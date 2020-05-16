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

public class StandActivity extends AppCompatActivity {

    private Switch stand;
    private TextView state, rssi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stand);

        stand = findViewById(R.id.standSwitch);
        state = findViewById(R.id.stateStand);
        rssi = findViewById(R.id.rssiStand);

        MainActivity.firebase.getDb().child("iluminacion").child("luces").child("caseta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stand.setChecked((Boolean) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/

        MainActivity.firebase.getDb().child("wifidevices").child("esp8266").child("caseta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = (String) dataSnapshot.child("state").getValue();
                if(status.equals("Connected")){
                    state.setTextColor(Color.GREEN);
                    stand.setEnabled(true);
                }else{
                    state.setTextColor(Color.RED);
                    stand.setEnabled(false);
                }

                state.setText((String) dataSnapshot.child("state").getValue());
                rssi.setText((String) dataSnapshot.child("rssi").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });//*/

        stand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    MainActivity.firebase.getDb().child("iluminacion").child("luces").child("caseta").setValue(true);
                } else {
                    MainActivity.firebase.getDb().child("iluminacion").child("luces").child("caseta").setValue(false);
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
