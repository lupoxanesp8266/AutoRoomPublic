package lupoxan.autoroom.com.autoroom11;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * @author lupo.xan
 * @version 0.2.3
 * @since 10/04/2019
 */
public class MessagingService extends FirebaseMessagingService {

    private NotificationChannel notificationChannel;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        try {
            showNotificacion(remoteMessage.getNotification().getChannelId(),remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void setNotificationChannel(String channel){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            notificationChannel = new NotificationChannel(channel, channel, NotificationManager.IMPORTANCE_HIGH);

            //Configure the channel´s initial settings
            notificationChannel.setShowBadge(true);
            notificationChannel.setSound(notificationChannel.getSound(), notificationChannel.getAudioAttributes());
            notificationChannel.setVibrationPattern(new long[]{100, 0, 0, 100, 10, 100});
            notificationChannel.enableVibration(true);
        }
    }

    public NotificationChannel getNotificationChannel(){
        return this.notificationChannel;
    }

    public void showNotificacion(String channel, String title, String body){
        //Create the channel TEMPS


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setNotificationChannel(channel);


            //Create a notification
            NotificationCompat.Builder notification = new NotificationCompat.Builder(MessagingService.this, channel)
                    .setSmallIcon(R.drawable.autoroom_not_black)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);


            //Submit the notification channel object to the notification manager
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MessagingService.this);
            notificationManager.createNotificationChannel(getNotificationChannel());

            //Notify
            notificationManager.notify(new Random().nextInt(), notification.build());

        }else{
            //Do nothing for now
            Log.d("Not_for_23","Message arrives");
        }




    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }


}
