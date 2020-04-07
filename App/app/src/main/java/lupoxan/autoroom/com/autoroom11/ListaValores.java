package lupoxan.autoroom.com.autoroom11;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaValores extends AppCompatActivity {

    ListView tempsss;
    String fecha;
    ArrayList<Valores> datos = new ArrayList<>();
    ValueEventListener valueEventListener;

    private MyFirebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_valores);

        tempsss = findViewById(R.id.listView);

        firebase = new MyFirebase();

        fecha = getIntent().getExtras().getString("fecha");

        setTitle(fecha);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datos.clear();
                ArrayList<String> datoss = new ArrayList<>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    datoss.add(postSnapshot.getKey());
                    datoss.add((String)postSnapshot.child("Movimiento").getValue());
                    datoss.add((String)postSnapshot.child("Temp_Ext").getValue());
                    datoss.add((String)postSnapshot.child("Temp_Int").getValue());
                }

                for(int i = 0; i < datoss.size() - 3; i += 4){
                    datos.add(new Valores(datoss.get(i),datoss.get(i+1),datoss.get(i+2),datoss.get(i+3)));
                }

                MyCustomAdapter myCustomAdapter = new MyCustomAdapter(ListaValores.this,datos);
                tempsss.setAdapter(myCustomAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        firebase.getDb().child("sensores").child(fecha).addValueEventListener(valueEventListener);//*/

    }

    @Override
    public void onBackPressed() {
        firebase.getDb().child("sensores").child(fecha).removeEventListener(valueEventListener);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            firebase.getDb().child("sensores").child(fecha).removeEventListener(valueEventListener);
            finish();
        }
        return true;
    }
}
