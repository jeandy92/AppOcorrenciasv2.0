package appocorrencias.com.appocorrencias.Network;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import appocorrencias.com.appocorrencias.Activitys.Login;
import appocorrencias.com.appocorrencias.R;

/**
 * Created by Jeanderson on 07/05/2017.
 */

public class GCMPushReceiverService extends GcmListenerService {
    @Override
    public void onMessageReceived(String s, Bundle bundle) {
        String message = bundle.getString("message");
        sendNotification(message);

    }

    public void sendNotification(String message){
        Intent intent = new Intent(this,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this,requestCode,intent,PendingIntent.FLAG_ONE_SHOT);
        //Configura a notificação
        //Som da notificação
        Uri sound  = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_app)
                .setContentInfo("Novo Crime Registrado")
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,noBuilder.build());// 0 = ID PARA NOTIFICAÇÃO


    }

}

