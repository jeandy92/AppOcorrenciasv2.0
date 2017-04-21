package appocorrencias.com.appocorrencias.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class MainActivity extends AppCompatActivity {

private byte[] imagem ;
private String nome,RESULTADO,APELIDO,NOME,SENHA;

    String LoginServer;

    String CPF;
    String Nome;

    ProcessaSocket processa =  new ProcessaSocket();
    String retorno;


    private   EditText usuario;
    private   EditText senha;
    private   Button btnCadastrarCli;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        usuario = (EditText) findViewById(R.id.usuario);
        senha = (EditText) findViewById(R.id.password);
        btnCadastrarCli = (Button) findViewById(R.id.btnCadastrarCli);

        usuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                usuario.setText("");
            }


        });
        senha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                senha.setText("");
            }


        });

        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", usuario);
        usuario.addTextChangedListener(maskCPF);



//        Intent intent = getIntent();
//        String name = intent.getStringExtra("my_name");
//        int age = intent.getIntExtra("my_age", 0);
//        byte[] random = intent.getByteArrayExtra("random");



        //Thread para que o aplicativo possa se conectar com o servidor na rede
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public void evCadastrarSe(View view) {

        setContentView(R.layout.activity_cadastrar_usuario);
        this.startActivity(new Intent(this, Cadastrar_Usuario.class));

    }

    public void evEntrar(View view) throws IOException {


        if (usuario.getText().toString().equals("adm") && senha.getText().toString().equals("senha")) {
            Toast.makeText(getApplicationContext(), "Perfil de ADM", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_adm);
            this.startActivity(new Intent(this, Adm.class));

        } else {
            String convCpf = usuario.getText().toString().replaceAll("[^0123456789]", "");
            LoginServer = "LoginServer" + " " + convCpf + " " + senha.getText().toString();

            if (Cadastrar_Usuario.validarCPF(convCpf)) {
                usuario.setError("CPF Inválido");
                usuario.setFocusable(true);
                usuario.requestFocus();
            } else {
                retorno = processa.cadastrar1_no_server(LoginServer);
                String retorno2[] = retorno.split("/");
                String Status = retorno2[0];

                if (Status.equals("erro")) {
                    usuario.setError("Erro de Conexao");
                    usuario.setFocusable(true);
                    usuario.requestFocus();
                    //erro_de_conexao();
                } else {
                    if (Status.equals("false")) {
                        usuario.setError("Usuario ou senha Invalido");
                        usuario.setFocusable(true);
                        usuario.requestFocus();
                    } else {
                        CPF = retorno2[1];
                        Nome = retorno2[2];

                        Toast.makeText(getApplicationContext(), "Perfil Cliente", Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.activity_cliente);
                        Intent cliente = new Intent(this, Cliente.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("nome", Nome);

                        cliente.putExtras(bundle);
                        this.startActivity(cliente);

                    }
                }
            }
        }
    }

        //atributo da classe.
        private AlertDialog alerta;

    public void erro_de_conexao() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Erro de Conexao");
        //define a mensagem
        builder.setMessage("Servidor não encontrado");
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(MainActivity.this, "ok=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}



