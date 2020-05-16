package lupoxan.autoroom.com.autoroom11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class EntradaActivity extends AppCompatActivity {

    private Switch exterior;
    private TextView state, rssi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);
        exterior = findViewById(R.id.otherLamp);
        state = findViewById(R.id.stateExt);
        rssi = findViewById(R.id.rssiExt);

        MainActivity.firebase.getDb().child("iluminacion").child("luces").child("exterior").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exterior.setChecked((Boolean) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/

        MainActivity.firebase.getDb().child("wifidevices").child("esp32").child("exterior").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = (String) dataSnapshot.child("state").getValue();
                if(status.equals("Connected")){
                    state.setTextColor(Color.GREEN);
                    exterior.setEnabled(true);
                }else{
                    state.setTextColor(Color.RED);
                    exterior.setEnabled(false);
                }

                state.setText((String) dataSnapshot.child("state").getValue());
                rssi.setText((String) dataSnapshot.child("rssi").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/

        exterior.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    MainActivity.firebase.getDb().child("iluminacion").child("luces").child("exterior").setValue(true);
                } else {
                    MainActivity.firebase.getDb().child("iluminacion").child("luces").child("exterior").setValue(false);
                }
            }
        });
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
