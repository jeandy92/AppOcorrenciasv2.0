package appocorrencias.com.appocorrencias;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class CadastrarUsuarioActivity extends AppCompatActivity {

    //Variaveis globais
    Button btnCadastarUsuario;

    EditText Nome,Apelido,Telefone,Email,Endereco,Senha;

    private String host = "192.168.0.17";
    private int porta = 2222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

//Cutton cadastrar
        btnCadastarUsuario = (Button) findViewById(R.id.CadastrarUsuário);

        //Váriaveis para o cadastro
        Nome     = (EditText) findViewById(R.id.edtNome);
        Apelido  = (EditText) findViewById(R.id.edtApelido);
        Senha = (EditText) findViewById(R.id.edtSenha);
        Email    = (EditText) findViewById(R.id.edtEmail);
        Endereco = (EditText) findViewById(R.id.edtEndereco);
        Telefone = (EditText) findViewById(R.id.edtTelefone);


    }


    //Cadastrar usuário no servidor
    public void cadastrarUsuario(View v) {

        Toast.makeText(getApplicationContext(), "Cadastrado feito com sucesso"+Nome.getText(), Toast.LENGTH_SHORT).show();

//        if (v == btnCadastarUsuario) {
//            cadastrarUsuario();
//        }
}



    public void cadastrarUsuario(){
        try {
            Socket cliente = new Socket(this.host, this.porta);

            PrintStream saida = new PrintStream(cliente.getOutputStream());
            Toast.makeText(getApplicationContext(), "Cadastrando", Toast.LENGTH_SHORT).show();

            String cadastro = "Cadastrar"+"Cadastro"+Apelido.getText()+Senha.getText();

            //String s = "Cadastrar Cadastro Elenaldo 1234";
//            String x= "Cadastrar";

            String texto = "Mensagem Para teste !!!";

            OutputStream outputStream = cliente.getOutputStream();
            InputStream inputStream = cliente.getInputStream();

            outputStream.write("Cadastrar".getBytes());
            outputStream.flush();

            int Rec = inputStream.read();
            System.out.println(Rec);
            outputStream.write(cadastro.getBytes());
            outputStream.flush();

        }catch (Exception e ){

        }
    }
}

