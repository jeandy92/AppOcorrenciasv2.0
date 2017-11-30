package appocorrencias.com.appocorrencias.Activitys;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import appocorrencias.com.appocorrencias.R;




public class Adm extends AppCompatActivity {

    private Button btnSair,btnCadastrarOcorrencias;
    private TextView txvRetornoSocket;
    public static String nomeAdm, cpfAdm, bairro, Ip;
    public static int porta;
    private TextView txtTesteAdm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm);

        //Pegando valores que vem do Login
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nomeAdm = bundle.getString("nome");
        cpfAdm = bundle.getString("cpf");
        bairro = bundle.getString("bairro");

        btnSair  = (Button) findViewById(R.id.btnSair);
        txvRetornoSocket = (TextView) findViewById(R.id.txvRetornoSocket);
        btnCadastrarOcorrencias =  (Button) findViewById(R.id.btnCadastrarOcorrencias);

        txtTesteAdm = (TextView) findViewById(R.id.txtTesteAdm);
        txtTesteAdm.setText(nomeAdm);




            }



    public void evCadastrarUsuario(View view) {
        evCriarNotificacao();

        Intent cadastrar = new Intent(this, CadastraUsuario.class);

        Bundle bundle = new Bundle();
        bundle.putString("tela" , "Adm");

        cadastrar.putExtras(bundle);
        this.startActivity(cadastrar);
        this.finish();

    }

    public void evCriarNotificacao() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.fab_plus_icon)
                .setContentTitle("Um novo crime foi registrado próximo ao lugar onde mora")
                .setContentText("Um novo crime registrado");

        NotificationManager notificationmenager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        int mld = 1;
        notificationmenager.notify(mld,builder.build());
    }

    public void evCadastrarOcorrencia(View v){

        Intent cadastrarOcorrencia = new Intent(this, CadastraOcorrencia.class);

        Bundle bundle = new Bundle();
        bundle.putString("nome", nomeAdm);
        bundle.putString("cpf" , cpfAdm);
        bundle.putString("bairro" , bairro);
        bundle.putString("tela" , "Adm");
        bundle.putString("ip", Ip);
        bundle.putInt("porta", porta);

        cadastrarOcorrencia.putExtras(bundle);
        this.startActivity(cadastrarOcorrencia);

        this.finish();
    }

    public void evBuscarOcorrencia(View v){

        Intent buscarOcorrencia = new Intent(this, BuscaOcorrencias.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome", nomeAdm);
        bundle.putString("cpf" , cpfAdm);
        bundle.putString("bairro" , bairro);
        bundle.putString("tela" , "Adm");
        bundle.putString("telaBusca", "Busca");
        bundle.putString("ip", Ip);
        bundle.putInt("porta", porta);

        buscarOcorrencia.putExtras(bundle);
        this.startActivity(buscarOcorrencia);

        this.finish();
    }

    public void evBuscarUsuario(View v){
        Intent buscarUsuario = new Intent(this, BuscaUsuarios.class);

        Bundle bundle = new Bundle();
        bundle.putString("ip", Ip);
        bundle.putInt("porta", porta);

        buscarUsuario.putExtras(bundle);
        this.startActivity(buscarUsuario);

        this.finish();
    }

    public void evSair(View v){
        this.startActivity(new Intent(this,Login.class));
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.startActivity(new Intent(this,Login.class));
        this.finish();
    }
}