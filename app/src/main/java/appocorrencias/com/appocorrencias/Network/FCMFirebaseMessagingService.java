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

import java.util.Map;

import appocorrencias.com.appocorrencias.Activitys.Login;
import appocorrencias.com.appocorrencias.R;

/**
 * Created by Jeanderson on 07/05/2017.
 */

public class FCMFirebaseMessagingService extends FirebaseMessagingService {

    public int ID_NOTIFICACAO ;
    public NotificationCompat.Builder noBuilder;
    public NotificationManager notificationManager;


    public void onMessageReceived(RemoteMessage message){
        String from = message.getFrom();
        Map data = message.getData();
        sendNotification(message.getNotification().getBody());

    }

    public void sendNotification(String message){
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
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{100, 250, 100, 500})
                .setSound(sound);



       NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        String[] events = new String[6];

        for (int i=0; i < events.length; i++) {
            inboxStyle.addLine(events[i] = new String(message));
        }

        noBuilder.setStyle(inboxStyle);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_NOTIFICACAO, noBuilder.build());// 0 = ID PARA NOTIFICAÇÃO


    }

}

