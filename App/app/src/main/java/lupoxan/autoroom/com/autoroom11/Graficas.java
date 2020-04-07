package lupoxan.autoroom.com.autoroom11;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author lupo.xan
 * @version 0.2.0
 * @since 02/06/2019
 */
public class Graficas extends AppCompatActivity {

    LineChart chart;

    String date = "";

    ArrayList<String> xAxes = new ArrayList<>();

    ArrayList<Entry> yAxesExt = new ArrayList<>();
    ArrayList<Entry> yAxesInt = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();

    LineDataSet dataSet;
    LineDataSet dataSetInt;

    Button datePickerButton;
    ImageButton next, prev;

    private MyFirebase firebase;

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
            date = String.format("%02d-%02d-%04d",datePicker.getDayOfMonth(),datePicker.getMonth()+1,datePicker.getYear());
            datePickerButton.setText(date);
            generarGrafica(date);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);

        chart = findViewById(R.id.graph);
        datePickerButton = findViewById(R.id.datePicker);
        next = findViewById(R.id.nextDate);
        prev = findViewById(R.id.prevDate);

        firebase = new MyFirebase();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date fecha = new Date();
        date = format.format(fecha);
        datePickerButton.setText(date);
        generarGrafica(date);

        firebase.getDb().child("sensores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dates.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    dates.add(snap.getKey());
                }
                dates.remove("PIR");
                dates.remove("movimiento");
                dates.remove("temp_ext");
                dates.remove("temp_int");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dates.indexOf(date) == dates.size() - 1){
                    date = dates.get(dates.size() - 1);
                    datePickerButton.setText(date);
                    generarGrafica(date);
                }else{
                    date = dates.get(dates.indexOf(date) + 1);
                    datePickerButton.setText(date);
                    generarGrafica(date);
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dates.indexOf(date) == 0){
                    date = dates.get(0);
                    datePickerButton.setText(date);
                    generarGrafica(date);
                }else{
                    date = dates.get(dates.indexOf(date) - 1);
                    datePickerButton.setText(date);
                    generarGrafica(date);
                }

            }
        });
    }

    private void generarGrafica(String fecha) {
        firebase.getDb().child("sensores").child(fecha).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Limpiamos los dataSets para no repetir los valores
                xAxes.clear();
                yAxesExt.clear();
                yAxesInt.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String tempe = (String) dataSnapshot.child(ds.getKey()).child("Temp_Ext").getValue();
                    String tempi = (String) dataSnapshot.child(ds.getKey()).child("Temp_Int").getValue();

                    if (tempe == null || tempe.equals("NaN ºC") || tempe.equals("")) {
                        tempe = "0,0 ºC";
                    }

                    if (tempi == null || tempi.equals("NaN ºC") || tempi.equals("")) {
                        tempi = "0,0 ºC";
                    }

                    //Cortar la cadena para quitar ºC
                    String[] tempis = tempi.replaceAll(",",".").trim().split("ºC");
                    String[] tempes = tempe.replaceAll(",",".").trim().split("ºC");
                    String[] hora = ds.getKey().split(":");
                    //Añadir los valores a los datasets
                    xAxes.add(hora[0] + ":" + hora[1]);//Añadiendo las horas
                    yAxesExt.add(new Entry(Float.parseFloat(tempes[0]), xAxes.size()));
                    yAxesInt.add(new Entry(Float.parseFloat(tempis[0]), xAxes.size()));
                }//*/

                dataSet = new LineDataSet(yAxesExt, getResources().getString(R.string.tempExt));
                dataSetInt = new LineDataSet(yAxesInt, getResources().getString(R.string.tempInt));

                dataSet.setDrawCircles(true);
                dataSet.setDrawFilled(true);
                dataSet.setColor(Color.BLUE);
                dataSet.setCircleColor(Color.BLUE);
                dataSet.setFillColor(Color.BLUE);

                dataSetInt.setDrawCircles(true);
                dataSetInt.setDrawFilled(true);
                dataSetInt.setCircleColor(Color.RED);
                dataSetInt.setColor(Color.RED);
                dataSetInt.setFillColor(Color.RED);

                ArrayList<ILineDataSet> lineDataSet = new ArrayList<>();//Crear uno nuevo para no repetir las leyendas

                lineDataSet.add(dataSet);//Añadimos los valores de la temperatura exterior
                lineDataSet.add(dataSetInt);//Añadimos los valores de la temperatura interios

                chart.setData(new LineData(xAxes, lineDataSet));//Añadimos esos valores al gráfico

                chart.setVisibleXRangeMaximum(49);

                chart.setDescription(getResources().getString(R.string.temperatures));
                chart.setDescriptionColor(Color.BLACK);

                chart.setTouchEnabled(true);
                chart.setDragEnabled(true);
                chart.setEnabled(true);
                chart.postInvalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graphmenu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.zoom_fade_in, R.anim.zoom_fade_out);
                return true;
            case R.id.registros:
                intent = new Intent(getApplicationContext(), IntruderActivity.class);
                intent.putExtra("fecha", date);
                startActivity(intent);
                overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
                return true;
            case R.id.cambio:
                intent = new Intent(getApplicationContext(), ListaValores.class);
                intent.putExtra("fecha", date);
                startActivity(intent);
                overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
                return true;
            case R.id.today:
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
                Date fecha = new Date();
                date = format.format(fecha);
                datePickerButton.setText(date);
                generarGrafica(date);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case 0:
                Calendar cal = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.calendar,onDateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH) ,cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.updateDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));

                return datePickerDialog;
        }
        return null;
    }
}
