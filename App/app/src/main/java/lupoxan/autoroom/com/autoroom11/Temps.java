package lupoxan.autoroom.com.autoroom11;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.widget.RemoteViews;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link TempsConfigureActivity TempsConfigureActivity}
 */
public class Temps extends AppWidgetProvider {

    private static String tempI;
    private static String tempE;
    protected static DatabaseReference db;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.temps);

        db = FirebaseDatabase.getInstance().getReference();

        getData();

        views.setTextViewText(R.id.temp_ext, tempE);
        views.setTextViewText(R.id.tempIntLabel, tempI);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void getData() {
        db.child("sensores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    tempI = (String) dataSnapshot.child("temp_int").getValue();
                    tempE = (String) dataSnapshot.child("temp_ext").getValue();
                } catch (NullPointerException e) {
                    tempI = "NaN ºC";
                    tempE = "NaN ºC";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.temps);
        Intent intentSync = new Intent(context, Temps.class);
        intentSync.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE); //You need to specify the action for the intent. Right now that intent is doing nothing for there is no action to be broadcasted.
        PendingIntent pendingSync = PendingIntent.getBroadcast(context,0, intentSync, PendingIntent.FLAG_UPDATE_CURRENT); //You need to specify a proper flag for the intent. Or else the intent will become deleted.
        views.setOnClickPendingIntent(R.id.title,pendingSync);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            TempsConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

