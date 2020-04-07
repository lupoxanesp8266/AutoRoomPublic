package lupoxan.autoroom.com.autoroom11;

import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * @author lupo.xan
 * @since 06/02/2019
 * @version 0.2.2
 */
public class Leds extends AppCompatActivity {

    Switch coolMode, fadeMode, blinkMode;

    Button apagarLeds;

    TextView rojoPor, verdePor, blancoLPor, blancoRPor, blancoCPor, fadingPor, blinkerPor;

    SeekBar rojos, verdes, blancoL, blancoR, blancoC, fading, blinker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leds);

        ids();

        MainActivity.firebase.getDb().child("leds").child("autoLeds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                coolMode.setChecked((Boolean) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/
        MainActivity.firebase.getDb().child("leds").child("blinker").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blinkMode.setChecked((Boolean) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/
        MainActivity.firebase.getDb().child("leds").child("fading").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fadeMode.setChecked((Boolean) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/

        MainActivity.firebase.getDb().child("leds").child("verdes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                verdePor.setText(dataSnapshot.getValue().toString() + " %");
                verdes.setProgress(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast error = Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT);
                error.show();
            }
        });//*/
        MainActivity.firebase.getDb().child("leds").child("rojos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rojoPor.setText(dataSnapshot.getValue().toString() + " %");
                rojos.setProgress(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast error = Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT);
                error.show();
            }
        });//*/
        MainActivity.firebase.getDb().child("leds").child("blanco_l").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blancoLPor.setText(dataSnapshot.getValue().toString() + " %");
                blancoL.setProgress(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast error = Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT);
                error.show();
            }
        });//*/
        MainActivity.firebase.getDb().child("leds").child("blanco_r").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blancoRPor.setText(dataSnapshot.getValue().toString() + " %");
                blancoR.setProgress(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast error = Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT);
                error.show();
            }
        });//*/
        MainActivity.firebase.getDb().child("leds").child("blanco_cama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blancoCPor.setText(dataSnapshot.getValue().toString() + " %");
                blancoC.setProgress(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast error = Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT);
                error.show();
            }
        });//*/

        MainActivity.firebase.getDb().child("leds").child("timer_fade").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fadingPor.setText(dataSnapshot.getValue().toString() + " mS");
                fading.setProgress(Integer.parseInt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast error = Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT);
                error.show();
            }
        });//*/
        MainActivity.firebase.getDb().child("leds").child("timer_blink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blinkerPor.setText(dataSnapshot.getValue().toString() + " mS");
                blinker.setProgress(Integer.parseInt(dataSnapshot.getValue().toString())/1000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast error = Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT);
                error.show();
            }
        });//*/

        coolMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.firebase.getDb().child("leds").child("autoLeds").setValue(isChecked);

                fadeMode.setEnabled(!isChecked);//Ocultamos el switch fade
                blinkMode.setEnabled(!isChecked);//Ocultamos el switch blink

                fading.setEnabled(!isChecked);//Ocultamos el seekBar fading
                blinker.setEnabled(!isChecked);//Ocultamos el seekBar blink

            }
        });//*/

        fadeMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.firebase.getDb().child("leds").child("fading").setValue(isChecked);

                coolMode.setEnabled(!isChecked);//Ocultamos el switch coolMode
                blinkMode.setEnabled(!isChecked);//Ocultamos el switch blink

                blinker.setEnabled(!isChecked);//Ocultamos el seekBar blink
            }
        });//*/

        blinkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.firebase.getDb().child("leds").child("blinker").setValue(isChecked);

                fadeMode.setEnabled(!isChecked);//Ocultamos el switch fade
                coolMode.setEnabled(!isChecked);//Ocultamos el switch coolMode

                fading.setEnabled(!isChecked);//Ocultamos el seekBar fading
            }
        });//*/


        apagarLeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.firebase.getDb().child("leds").child("rojos").setValue(0);
                MainActivity.firebase.getDb().child("leds").child("verdes").setValue(0);
                MainActivity.firebase.getDb().child("leds").child("blanco_l").setValue(0);
                MainActivity.firebase.getDb().child("leds").child("blanco_r").setValue(0);
                MainActivity.firebase.getDb().child("leds").child("blanco_cama").setValue(0);
            }
        });//*/

        rojos.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.firebase.getDb().child("leds").child("rojos").setValue(seekBar.getProgress());
            }
        });//*/
        verdes.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.firebase.getDb().child("leds").child("verdes").setValue(seekBar.getProgress());
            }
        });//*/
        blancoL.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.firebase.getDb().child("leds").child("blanco_l").setValue(seekBar.getProgress());
            }
        });//*/
        blancoR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.firebase.getDb().child("leds").child("blanco_r").setValue(seekBar.getProgress());
            }
        });//*/
        blancoC.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.firebase.getDb().child("leds").child("blanco_cama").setValue(seekBar.getProgress());
            }
        });//*/

        fading.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.firebase.getDb().child("leds").child("timer_fade").setValue(seekBar.getProgress());
            }
        });//*/
        blinker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.firebase.getDb().child("leds").child("timer_blink").setValue(seekBar.getProgress()*1000);
            }
        });//*/
    }

    private void ids() {
        coolMode = findViewById(R.id.coolMode);
        fadeMode = findViewById(R.id.fadingMode);
        blinkMode = findViewById(R.id.blinkerMode);

        rojoPor = findViewById(R.id.rojosPor);
        verdePor = findViewById(R.id.verdesPor);
        blancoLPor = findViewById(R.id.blancoLPor);
        blancoRPor = findViewById(R.id.blancoRPor);
        blancoCPor = findViewById(R.id.blancoCPor);

        fadingPor = findViewById(R.id.fadingTimePor);
        blinkerPor = findViewById(R.id.blinkTimePor);

        rojos = findViewById(R.id.rojosSeek);
        verdes = findViewById(R.id.verdesSeek);
        blancoL = findViewById(R.id.blancoLSeek);
        blancoR = findViewById(R.id.blancoRSeek);
        blancoC = findViewById(R.id.blancoCSeek);

        fading = findViewById(R.id.fadingTimerSeek);
        blinker = findViewById(R.id.blinkTimerSeek);

        apagarLeds = findViewById(R.id.ofAll);
    }//*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
        }
        return true;
    }
}
