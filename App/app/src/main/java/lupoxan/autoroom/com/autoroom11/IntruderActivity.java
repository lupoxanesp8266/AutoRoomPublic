package lupoxan.autoroom.com.autoroom11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IntruderActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ArrayList<String> horas = new ArrayList<>();
    ListView intruder;
    String fecha;

    private MyFirebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intruder);

        intruder = findViewById(R.id.intruso);

        firebase = new MyFirebase();

        fecha = getIntent().getExtras().getString("fecha");

        adapter = new ArrayAdapter<>(IntruderActivity.this, R.layout.datolayout, R.id.datosSensors, horas);
        intruder.setAdapter(adapter);

        firebase.getDb().child("sensores").child("PIR").child(fecha).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                horas.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    horas.add(ds.getKey());
                }
                adapter.notifyDataSetChanged();

                if(dataSnapshot.getChildrenCount() == 0){
                    horas.add(getResources().getString(R.string.noData));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
        }
        return true;
    }
}
