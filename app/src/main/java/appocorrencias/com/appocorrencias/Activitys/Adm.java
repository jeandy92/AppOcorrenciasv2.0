package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import appocorrencias.com.appocorrencias.R;

//Cadastrar usuario

  //      Enviar dados separados por /
//Nome Completo (Nome jeanderson ALmeida);
//Login (ID);(id jean)
  //      Erlang devolve Ok

    //    PEGAR O RETORNO DO SERVIDOR ERLANG ERRADO E VOLTA PARA O INICIA
   //     PEGAR O RETORNO EM CASO POSITIVO E MOSTRAR AO USUARIO
//SENHA
//E-mail
//Telefone
  //      Endereco


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

        btnConexao  = (Button) findViewById(R.id.teste);
        txvRetornoSocket = (TextView) findViewById(R.id.txvRetornoSocket);
        btnCadastrarOcorrencias =  (Button) findViewById(R.id.btnCadastrarOcorrencias);

        txtTesteAdm = (TextView) findViewById(R.id.txtTesteAdm);
        txtTesteAdm.setText("Bem Vindo Adm " + NomeCli + " CPF: " + CPF);
    }
    public void valida_conexao(View view) {

        setContentView(R.layout.activity_cliente_socket);
        this.startActivity(new Intent(this,Cliente_Socket.class));
    }

    public void cadastrar_usuario(View view) {
        setContentView(R.layout.activity_cadastrar_usuario);
        this.startActivity(new Intent(this,Cadastrar_Usuario.class));
    }

    public void cadastrar_ocorrencia(View v){

        setContentView(R.layout.activity_cadastrar_ocorrencia);

        Intent cadastrarOcorrencia = new Intent(this, Cadastrar_Ocorrencia.class);

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
        setContentView(R.layout.activity_main);
        this.startActivity(new Intent(this,MainActivity.class));

    }
}