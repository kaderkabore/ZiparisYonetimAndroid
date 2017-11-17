package oceannet.com.ziparisyonetim.UTILS.GCM;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import oceannet.com.ziparisyonetim.AppController;
import oceannet.com.ziparisyonetim.R;
import oceannet.com.ziparisyonetim.UI.MainMenu;


/**
 * Created by oceannet on 06/01/17.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {

        System.out.println("zassss yusufff"+data.toString());

      //  addNotification("Bitmiyo",data.toString()+"");
        addNotification("Zipariş Yönetim",data.getString("alert")+"");

           if (AppController.getInstance().getPushRefresher() != null){
               AppController.getInstance().getPushRefresher().refresh();
           }



     //   alert
       // type=5, alert=Sepetiniz süresinin dolmasına 0.010 dakika kalmıştır
//        System.out.println(data.getString("orderid"));
//        System.out.println(data.getString("type"));

        //type

//        String message = data.getString("data");
//        Log.d(TAG, "From: " + from);
//        Log.d(TAG, "Message: " + message);
//
//        String tittle = "";
//
//
//     //   addNotification("hello",data.toString());

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        //sendNotification(message);
        // [END_EXCLUDE]
    }

    private void addNotification(String title,String text) {


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.loading_logo_2x)
                        .setContentTitle(title)
                        .setContentText(text);



        Intent notificationIntent = new Intent(this, MainMenu.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        builder.mNotification.defaults |= Notification.DEFAULT_SOUND;

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());


        //type=5, alert=Sepetiniz süresinin dolmasına 0.010 dakika kalmıştır
    }

    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
//    private void sendNotification(String message) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.cafeslogo)
//                .setContentTitle("GCM Message")
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//
//
//    }
}