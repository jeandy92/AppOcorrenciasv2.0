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

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import appocorrencias.com.appocorrencias.R;




public class Adm extends AppCompatActivity {

    private Button btnConexao,btnCadastrarOcorrencias;
    private TextView txvRetornoSocket;
    static String NomeCli, CPF, Bairro;
    private TextView txtTesteAdm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm);

        //Pegando valores que vem do Login
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        NomeCli = bundle.getString("nome");
        CPF = bundle.getString("cpf");
        Bairro = bundle.getString("bairro");

        btnConexao  = (Button) findViewById(R.id.btn_validar_conexao);
        txvRetornoSocket = (TextView) findViewById(R.id.txvRetornoSocket);
        btnCadastrarOcorrencias =  (Button) findViewById(R.id.btnCadastrarOcorrencias);

        txtTesteAdm = (TextView) findViewById(R.id.txtTesteAdm);
        txtTesteAdm.setText("Bem Vindo Adm " + NomeCli + " Cpf: " + CPF);
    }
    public void ev_valida_conexao(View view) {

    }

    public void ev_cadastrar_usuario(View view) {
        CriaNotificaçoes();


        setContentView(R.layout.activity_cadastrar_usuario);
        this.startActivity(new Intent(this,CadastrarUsuario.class));
    }
    protected void CriaNotificaçoes() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.fab_plus_icon)
                .setContentTitle("Um novo crime foi registrado próximo ao lugar onde mora")
                .setContentText("Um novo crime registrado");

        NotificationManager notificationmenager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        int mld = 1;
        notificationmenager.notify(mld,builder.build());
    }




    public void ev_cadastrar_ocorrencia(View v){

        setContentView(R.layout.activity_cadastrar_ocorrencia);

        Intent cadastrarOcorrencia = new Intent(this, CadastrarOcorrencia.class);

        Bundle bundle = new Bundle();
        bundle.putString("nome", NomeCli);
        bundle.putString("cpf" , CPF);
        bundle.putString("bairro" , Bairro);

        cadastrarOcorrencia.putExtras(bundle);
        this.startActivity(cadastrarOcorrencia);

        this.finish();
    }

   private void conectarSocket()  {
        try{
            Socket socket = null;

            ObjectOutputStream canalSaida = null;
            ObjectInputStream canalEntrada = null;
            Toast.makeText(getApplicationContext(), "Tentando conexao", Toast.LENGTH_SHORT).show();
            socket = new Socket("192.168.0.192", 5678);

            canalSaida = new ObjectOutputStream(socket.getOutputStream());
            canalSaida.writeObject("Teste");

            canalEntrada = new ObjectInputStream(socket.getInputStream());
            Object object = canalEntrada.readObject();
            if ((object != null) && (object instanceof String)) {
                txvRetornoSocket.setText(object.toString());

            }
        }  catch(Exception e) {
            //FIXME Tratar a Exception.
           e.printStackTrace();
       }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setContentView(R.layout.activity_login);
        this.startActivity(new Intent(this,Login.class));

    }
}