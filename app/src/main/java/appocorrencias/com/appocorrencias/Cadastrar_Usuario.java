package appocorrencias.com.appocorrencias;

import android.content.Intent;
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

public class Cadastrar_Usuario extends AppCompatActivity {

    //Variaveis globais
    Button btnCadastarUsuario;
    Button btnBuscar;
    EditText Nome,CPF,Telefone,Email,Senha,Rua, Bairro, Cidade, Numero, CEP, UF;


    private String host = "192.168.0.17";
    private int porta = 2222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        //Button cadastrar
        btnCadastarUsuario = (Button) findViewById(R.id.CadastrarUsuário);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);

        //Váriaveis para o cadastro
        Nome     = (EditText) findViewById(R.id.edtNome);
        CPF      = (EditText) findViewById(R.id.edtCPF);
        Senha    = (EditText) findViewById(R.id.edtSenha);
        Email    = (EditText) findViewById(R.id.edtEmail);
        Rua      = (EditText) findViewById(R.id.edtRua);
        Telefone = (EditText) findViewById(R.id.edtTelefone);
        CEP      = (EditText) findViewById(R.id.edtCep);
        Bairro   = (EditText) findViewById(R.id.edtBairro);
        Cidade   = (EditText) findViewById(R.id.edtCidade);
        UF       = (EditText) findViewById(R.id.edtUF);
        Numero   = (EditText) findViewById(R.id.edtNumero);



    }


    // Método para buscar Cep
    public void evBuscarCep(View v){



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

            String cadastro = "Cadastrar"+"Cadastro"+CPF.getText()+Senha.getText();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setContentView(R.layout.activity_adm);
        this.startActivity(new Intent(this,Adm.class));
    }
}



