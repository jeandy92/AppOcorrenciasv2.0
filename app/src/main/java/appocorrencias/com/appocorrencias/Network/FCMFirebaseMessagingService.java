package appocorrencias.com.appocorrencias.Network;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import appocorrencias.com.appocorrencias.Activitys.Login;
import appocorrencias.com.appocorrencias.R;

/**
 * Created by Jeanderson on 07/05/2017.
 */

public class FCMFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "onMessageReceived"  ;
    public int ID_NOTIFICACAO ;
    public NotificationCompat.Builder noBuilder;
    public NotificationManager notificationManager;

//
//   public void onMessageReceived(RemoteMessage message){
//       String from = message.getFrom();
//       Map data = message.getData();
//       sendNotification(message.getNotification().getBody());
//
//   }


//    public void onMessageReceived(String s, Bundle bundle) {
//        String message = bundle.getString("message");
//        sendNotification(message);
//
//    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            //sendNotification(remoteMessage.getData().toString());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        // ...
//
//    }


    public void sendNotification(String message,String titulo){
        Intent intent = new Intent(this,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;

        Log.i("Mensagem: ",message);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        //Configura a notificação


        //Som da notificação
        Uri sound = RingtoneManager.getActualDefaultRingtoneUri(this.getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
        Ringtone currentRingtone = RingtoneManager.getRingtone(this, sound);

        //

        noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_app)
                .setContentInfo("Novo Crime Registrado")
                .setContentText(message)
                .setContentTitle(titulo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{100, 250, 100, 500})
                .setSound(sound);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_NOTIFICACAO, noBuilder.build());// 0 = ID PARA NOTIFICAÇÃO


    }

}

