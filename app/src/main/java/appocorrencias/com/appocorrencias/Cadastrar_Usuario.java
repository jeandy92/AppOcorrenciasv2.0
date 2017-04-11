package appocorrencias.com.appocorrencias;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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
    public void evBuscarCep(View v) throws IOException {
        buscarCEP busca = new buscarCEP();
        Rua.setText(busca.getEndereco(CEP.getText().toString()));
        Bairro.setText(busca.getBairro(CEP.getText().toString()));
        Cidade.setText(busca.getCidade(CEP.getText().toString()));
        UF.setText(busca.getUF(CEP.getText().toString()));
    }

    //Cadastrar usuário no servidor
    public void evCadastrarUsuario(View v) throws IOException {
        String cadastro1 = "Cadastrar1" + " " + CPF.getText().toString() + " " + Senha.getText().toString() +
                " " + Email.getText().toString() + " " + Telefone.getText().toString()+ " " + CEP.getText().toString()+
                " " + UF.getText().toString()+ " " + Numero.getText().toString();
        String cadastroNome = "CadastrarNome" + " " + CPF.getText().toString()+ " " + Nome.getText().toString();
        String cadastroRua = "CadastrarRua" + " " + CPF.getText().toString()+ " " + Rua.getText().toString();
        String cadastroBairro = "CadastrarBairro" + " " + CPF.getText().toString()+ " " + Bairro.getText().toString();
        String cadastroCidade = "CadastrarCidade" + " " + CPF.getText().toString()+ " " + Cidade.getText().toString();

        cadastrar_no_server(cadastro1);

        //Creceber resposta do server sobre CPF

        cadastrar_no_server(cadastroNome);
        cadastrar_no_server(cadastroRua);
        cadastrar_no_server(cadastroBairro);
        cadastrar_no_server(cadastroCidade);



        Toast.makeText(getApplicationContext(), "Cadastrado feito com sucesso " + Nome.getText(), Toast.LENGTH_SHORT).show();
    }

    private void cadastrar_no_server(String dados) {
        try {
            Socket socket = null;

            OutputStream canalSaida = null;
            ObjectInputStream canalEntrada = null;

            socket = new Socket("172.20.10.3",2222);

            canalSaida = socket.getOutputStream();
            canalSaida.write(dados.getBytes());


            // canalEntrada = new ObjectInputStream(socket.getInputStream());
            // Object object = canalEntrada.readObject();
            // if ((object != null) && (object instanceof String)) {
            //   txvRetornoSocket.setText(object.toString());
            // }


        } catch (Exception e) {
            //FIXME Tratar a Exception.
            e.printStackTrace();
        }
    }
}



