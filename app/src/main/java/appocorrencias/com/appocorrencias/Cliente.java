package appocorrencias.com.appocorrencias;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

    public class Cliente extends AppCompatActivity {

        private Toolbar mToolbar;
        private Toolbar mToolbarBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setContentView(R.layout.activity_main);
        this.startActivity(new Intent(this,MainActivity.class));
    }

    protected  void CriaNotificaçoes(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.fab_plus_icon)
                .setContentTitle("@string/TituloNotificao")
                .setContentText("@string/TextoNotificação");


    }



}



