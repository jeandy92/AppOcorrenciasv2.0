package appocorrencias.com.appocorrencias.Activitys;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.R;




public class Adm extends AppCompatActivity {

    private Button btnSair,btnCadastrarOcorrencias;
    private TextView txvRetornoSocket;
    private String nomeAdm, cpfAdm, bairro, Ip;
    private int Porta;
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

        String DadosServidor = null;


        try {
            DadosServidor = ProcessaSocket.BuscarServidor();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (DadosServidor.equals("erro")) {
            Toast.makeText(this, "Erro na Conexão DNS", Toast.LENGTH_SHORT).show();
        } else {
            if (DadosServidor != null) {
                String retorno2[] = DadosServidor.split("//");
                Ip = retorno2[0];
                Porta = Integer.parseInt(retorno2[1]);
            }
        }

    }

    public void evCadastrarUsuario(View view) {
        evCriarNotificacao();

        Intent cadastrar = new Intent(this, CadastrarUsuario.class);

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

        Intent cadastrarOcorrencia = new Intent(this, CadastrarOcorrencia.class);

        Bundle bundle = new Bundle();
        bundle.putString("nome", nomeAdm);
        bundle.putString("cpf" , cpfAdm);
        bundle.putString("bairro" , bairro);
        bundle.putString("tela" , "Adm");
        bundle.putString("ip", Ip);
        bundle.putInt("porta", Porta);

        cadastrarOcorrencia.putExtras(bundle);
        this.startActivity(cadastrarOcorrencia);

        this.finish();
    }

    public void evBuscarOcorrencia(View v){

        Intent buscarOcorrencia = new Intent(this, BuscarOcorrencias.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome", nomeAdm);
        bundle.putString("cpf" , cpfAdm);
        bundle.putString("bairro" , bairro);
        bundle.putString("tela" , "Adm");
        bundle.putString("telaBusca", "Busca");
        bundle.putString("ip", Ip);
        bundle.putInt("porta", Porta);

        buscarOcorrencia.putExtras(bundle);
        this.startActivity(buscarOcorrencia);

        this.finish();
    }

    public void evBuscarUsuario(View v){
        Intent buscarUsuario = new Intent(this, BuscarUsuarios.class);

        Bundle bundle = new Bundle();
        bundle.putString("ip", Ip);
        bundle.putInt("porta", Porta);

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