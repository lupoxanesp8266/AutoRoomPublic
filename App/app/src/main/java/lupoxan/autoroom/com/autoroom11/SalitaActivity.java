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

/**
 * @since 11/05/2020
 * @author lupo.xan
 * @version 0.1
 */
public class SalitaActivity extends AppCompatActivity {

    private Switch living;
    private TextView state, rssi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salita);
        living = findViewById(R.id.livingSwitch);
        state = findViewById(R.id.state);
        rssi = findViewById(R.id.rssi);

        MainActivity.firebase.getDb().child("iluminacion").child("luces").child("salita").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                living.setChecked((Boolean) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/

        MainActivity.firebase.getDb().child("wifidevices").child("esp8266").child("salita").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = (String) dataSnapshot.child("state").getValue();
                if(status.equals("Connected")){
                    state.setTextColor(Color.GREEN);
                    living.setEnabled(true);
                }else{
                    state.setTextColor(Color.RED);
                    living.setEnabled(false);
                }

                state.setText((String) dataSnapshot.child("state").getValue());
                rssi.setText((String) dataSnapshot.child("rssi").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/

        living.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    MainActivity.firebase.getDb().child("iluminacion").child("luces").child("salita").setValue(true);
                } else {
                    MainActivity.firebase.getDb().child("iluminacion").child("luces").child("salita").setValue(false);
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
