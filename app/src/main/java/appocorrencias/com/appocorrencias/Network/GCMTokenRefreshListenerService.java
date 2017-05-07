package appocorrencias.com.appocorrencias.Network;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Jeanderson on 07/05/2017.
 */

public class GCMTokenRefreshListenerService extends InstanceIDListenerService {
    /**
     *Quando o  Token sofre alguma alteração um novo token é gerado.
     *
     */

    @Override
    public void onTokenRefresh() {
        Intent intent =  new Intent(this,GCMRegistrationIntentService.class);
        startService(intent);
    }
}
