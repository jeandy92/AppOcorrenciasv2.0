package appocorrencias.com.appocorrencias;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class CadastrarUsuarioActivity extends AppCompatActivity {

    //Variaveis globais
    Button btnCadastarUsuario;
    Button btnBuscar;
    EditText Nome, CPF, Telefone, Email, Senha, Rua, Bairro, Cidade, Numero, CEP, UF;


    private String host = "127.0.0.1";
    private int porta = 2222;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        //Thread para que o aplicativo possa se conectar com o servidor na rede
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Button cadastrar
        btnCadastarUsuario = (Button) findViewById(R.id.CadastrarUsuario);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);

        //Váriaveis para o cadastro
        Nome = (EditText) findViewById(R.id.edtNome);
        CPF = (EditText) findViewById(R.id.edtCPF);
        Senha = (EditText) findViewById(R.id.edtSenha);
        Email = (EditText) findViewById(R.id.edtEmail);
        Rua = (EditText) findViewById(R.id.edtRua);
        Telefone = (EditText) findViewById(R.id.edtTelefone);
        CEP = (EditText) findViewById(R.id.edtCep);
        Bairro = (EditText) findViewById(R.id.edtBairro);
        Cidade = (EditText) findViewById(R.id.edtCidade);
        UF = (EditText) findViewById(R.id.edtUF);
        Numero = (EditText) findViewById(R.id.edtNumero);


    }


    // Método para buscar Cep
    public void evBuscarCep(View v) throws IOException {
        buscaCEP busca = new buscaCEP();
        Rua.setText(busca.getEndereco(CEP.getText().toString()));
        Bairro.setText(busca.getBairro(CEP.getText().toString()));
        Cidade.setText(busca.getCidade(CEP.getText().toString()));
        UF.setText(busca.getUF(CEP.getText().toString()));

    }





    //Cadastrar usuário no servidor
    public void evCadastrarUsuario(View v) throws IOException {
        String cadastro1 = "Cadastrar1" + " " + CPF.getText().toString() + " " + Senha.getText().toString() + " " + Email.getText().toString() + " " + Telefone.getText();
        String teste = "teste";

        envia_cadastro(teste);
        Toast.makeText(getApplicationContext(), "Cadastrado feito com sucesso" + Nome.getText(), Toast.LENGTH_SHORT).show();


    }

    public void envia_cadastro(String data) throws UnknownHostException, IOException {
        try {
            Socket cliente = new Socket(this.host, this.porta);

            OutputStream outputStream = cliente.getOutputStream();


            outputStream.write(data.getBytes());
            outputStream.flush();


        } catch (IOException e) {
            System.out.println(e);
        }
    }





}





