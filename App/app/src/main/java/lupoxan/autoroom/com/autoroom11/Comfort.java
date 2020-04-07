package lupoxan.autoroom.com.autoroom11;

import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * @author lupo.xan
 * @since 01/02/2019
 * @version 0.1.8
 */
public class Comfort extends AppCompatActivity {

    TextView tempInt, tempExt, modoAire, consignaL;
    Switch frio, calor, fan;
    SeekBar valorConsigna;
    CheckBox autofrio, autocalor;

    private static final int MIN = 10;
    private MyFirebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfort);

        tempInt = findViewById(R.id.temp_int);
        tempExt = findViewById(R.id.temp_ext);
        modoAire = findViewById(R.id.modoAireMode);
        consignaL = findViewById(R.id.consignaLabel);


        valorConsigna = findViewById(R.id.valorConsigna);

        frio = findViewById(R.id.frioMode);
        calor = findViewById(R.id.calorMode);
        fan = findViewById(R.id.fanMode);

        autocalor = findViewById(R.id.autoCalor);
        autofrio = findViewById(R.id.autoFrio);

        firebase = new MyFirebase();

        String tempI = getIntent().getExtras().getString("tempInt");
        String tempE = getIntent().getExtras().getString("tempExt");

        tempInt.setText(tempI);
        tempExt.setText(tempE);

        firebase.getDb().child("comfort").child("auto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                switch (dataSnapshot.getValue().toString()){
                    case "Frio":
                        autofrio.setChecked(true);
                        autocalor.setChecked(false);
                        break;
                    case "Calor":
                        autofrio.setChecked(false);
                        autocalor.setChecked(true);
                        break;
                    case "Off":
                        autocalor.setChecked(false);
                        autofrio.setChecked(false);
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        firebase.getDb().child("comfort").child("modo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                switch (dataSnapshot.getValue().toString()){
                    case "Frio":
                        frio.setChecked(true);
                        calor.setChecked(false);
                        modoAire.setText(dataSnapshot.getValue().toString());
                        break;
                    case "Calor":
                        calor.setChecked(true);
                        frio.setChecked(false);
                        modoAire.setText(dataSnapshot.getValue().toString());
                        break;
                    case "Apagado":
                        frio.setChecked(false);
                        calor.setChecked(false);
                        modoAire.setText(dataSnapshot.getValue().toString());
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        firebase.getDb().child("comfort").child("consigna").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consignaL.setText(getResources().getString(R.string.celsius,Integer.parseInt(dataSnapshot.getValue().toString())));
                valorConsigna.setProgress(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        firebase.getDb().child("comfort").child("fan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fan.setChecked((boolean)dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        valorConsigna.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress() <= MIN){
                    seekBar.setProgress(MIN);
                }
                firebase.getDb().child("comfort").child("consigna").setValue(seekBar.getProgress());
            }
        });

        frio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    firebase.getDb().child("comfort").child("modo").setValue("Frio");
                    calor.setVisibility(View.INVISIBLE);
                }else{
                    firebase.getDb().child("comfort").child("modo").setValue("Apagado");
                    calor.setVisibility(View.VISIBLE);
                }
            }
        });

        calor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    firebase.getDb().child("comfort").child("modo").setValue("Calor");
                    frio.setVisibility(View.INVISIBLE);
                }else{
                    firebase.getDb().child("comfort").child("modo").setValue("Apagado");
                    frio.setVisibility(View.VISIBLE);
                }
            }
        });

        autofrio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    firebase.getDb().child("comfort").child("auto").setValue("Frio");
                    autocalor.setVisibility(View.INVISIBLE);
                }else{
                    firebase.getDb().child("comfort").child("auto").setValue("Off");
                    autocalor.setVisibility(View.VISIBLE);
                }
            }
        });

        autocalor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    firebase.getDb().child("comfort").child("auto").setValue("Calor");
                    autofrio.setVisibility(View.INVISIBLE);
                }else{
                    firebase.getDb().child("comfort").child("auto").setValue("Off");
                    autofrio.setVisibility(View.VISIBLE);
                }
            }
        });

        fan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    firebase.getDb().child("comfort").child("fan").setValue(true);
                }else{
                    firebase.getDb().child("comfort").child("fan").setValue(false);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }
}
