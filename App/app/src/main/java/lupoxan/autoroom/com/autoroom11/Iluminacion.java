package lupoxan.autoroom.com.autoroom11;

import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Iluminacion extends AppCompatActivity {

    Switch general, cama, mesa;
    CheckBox autoMesa, autoCama, autoGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iluminacion);

        general = findViewById(R.id.general_lamp);
        cama = findViewById(R.id.cama_lamp);
        mesa = findViewById(R.id.mesa_lamp);


        autoCama = findViewById(R.id.camaCheck);
        autoMesa = findViewById(R.id.mesaCheck);
        autoGeneral = findViewById(R.id.upCheck);//*/

        MainActivity.firebase.getDb().child("iluminacion").child("luces").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                general.setChecked((Boolean) dataSnapshot.child("general").getValue());
                mesa.setChecked((Boolean) dataSnapshot.child("mesa").getValue());
                cama.setChecked((Boolean) dataSnapshot.child("cama").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/
        MainActivity.firebase.getDb().child("iluminacion").child("auto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                autoMesa.setChecked((Boolean) dataSnapshot.child("mesa").getValue());
                autoGeneral.setChecked((Boolean) dataSnapshot.child("general").getValue());
                autoCama.setChecked((Boolean) dataSnapshot.child("cama").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/

        autoCama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.firebase.getDb().child("iluminacion").child("auto").child("cama").setValue(true);
                    cama.setEnabled(false);
                } else {
                    MainActivity.firebase.getDb().child("iluminacion").child("auto").child("cama").setValue(false);
                    cama.setEnabled(true);
                }
            }
        });//*/

        autoMesa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.firebase.getDb().child("iluminacion").child("auto").child("mesa").setValue(true);
                    mesa.setEnabled(false);
                } else {
                    MainActivity.firebase.getDb().child("iluminacion").child("auto").child("mesa").setValue(false);
                    mesa.setEnabled(true);
                }
            }
        });//*/

        autoGeneral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.firebase.getDb().child("iluminacion").child("auto").child("general").setValue(true);
                    general.setEnabled(false);
                } else {
                    MainActivity.firebase.getDb().child("iluminacion").child("auto").child("general").setValue(false);
                    general.setEnabled(true);
                }
            }
        });//*/

        general.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.firebase.getDb().child("iluminacion").child("luces").child("general").setValue(true);
                } else {
                    MainActivity.firebase.getDb().child("iluminacion").child("luces").child("general").setValue(false);
                }
            }
        });//*/

        cama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.firebase.getDb().child("iluminacion").child("luces").child("cama").setValue(isChecked);
            }
        });//*/

        mesa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.firebase.getDb().child("iluminacion").child("luces").child("mesa").setValue(isChecked);
            }
        });//*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
    }
}
