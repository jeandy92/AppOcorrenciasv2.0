package appocorrencias.com.appocorrencias.Network;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Jeanderson on 07/05/2017.
 */

public class FCMFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "GCMRegistratService";


    @Override
    public void onTokenRefresh() {

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);


        //-- GCM /*Intent intent =  new Intent(this,FCMFirebaseInstanceIDService.class);
       /* startService(intent);*/
    }


    private void sendRegistrationToServer(String token) {

        // TODO: Implementar metodo para enviar token para o servidor de aplicativos
    }


}



























//    public static final String REGISTRATION_SUCESS ="RegistrationSucess";
//    public static final String REGISTRATION_ERROR ="RegistrationError";
//
//    public static final String LOG ="LOG";
//
//    public FCMFirebaseInstanceIDService(){
//        super("");
//    }
////
////    @Override
////    protected void onHandleIntent(@Nullable Intent intent) {
////      registerGCM();
////    }
////
//////    public void registerGCM() {
////        Intent registrationComplete = null;
////        String token = null;
////        try {
////            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
////            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
////            Log.w("GCMRegistraService : ", "token: "+token);
////
////            //notifica se o token foi completado com sucesso
////            registrationComplete = new Intent(REGISTRATION_SUCESS);
////            registrationComplete.putExtra("token", token);
////
////        } catch (IOException e) {
////            // Se ocorrer uma exceção ao buscar o novo token ou atualizar nossos dados de registro
////            // em um servidor de terceiros, isso garante que tentaremos a atualização mais tarde.
////            Log.i("RegistrationService", "RegistrationError");
////            registrationComplete = new Intent(REGISTRATION_ERROR);
////        }
////        // send Broadcast
////        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
////
////    }

