package appocorrencias.com.appocorrencias.Network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import appocorrencias.com.appocorrencias.R;

/**
 * Created by Jeanderson on 07/05/2017.
 */

public class GCMRegistrationIntentService extends IntentService {
    public static final String REGISTRATION_SUCESS ="RegistrationSucess";
    public static final String REGISTRATION_ERROR ="RegistrationError";

    public static final String LOG ="LOG";

    public GCMRegistrationIntentService(){
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
      registerGCM();
    }

    public void registerGCM() {
        Intent registrationComplete = null;
        String token = null;
        try {
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.w("GCMRegistraService : ", "token: "+token);

            //notifica se o token foi completado com sucesso
            registrationComplete = new Intent(REGISTRATION_SUCESS);
            registrationComplete.putExtra("token", token);

        } catch (IOException e) {
            // Se ocorrer uma exceção ao buscar o novo token ou atualizar nossos dados de registro
            // em um servidor de terceiros, isso garante que tentaremos a atualização mais tarde.
            Log.i("RegistrationService", "RegistrationError");
            registrationComplete = new Intent(REGISTRATION_ERROR);
        }
        // send Broadcast
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }

}
