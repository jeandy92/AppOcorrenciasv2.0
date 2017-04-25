package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.Fragments.PerfilFragment;
import appocorrencias.com.appocorrencias.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class MainActivity extends AppCompatActivity {

    private byte[] imagem;
    private String nome, RESULTADO, APELIDO, NOME, SENHA, LoginServer, CPF, Nome;
    private String convCpf;
    private ProcessaSocket processa = new ProcessaSocket();
    private String retorno;
    private View view;
    private String nomecompleto = "Jeanderson de Almeeida Dyorgenes";
    private EditText txtUsuario, txtSenha;
    private CheckBox salvarlogin;
    private Button btnCadastrarCli;
    private PerfilFragment perfil = new PerfilFragment();
    private static final String PREF_NAME = "MainActivityPreferences";
    private int count1;
    private int count2;

//******Create by Jeanderson  22/04/2017*****//

    private SharedPreferences.OnSharedPreferenceChangeListener callback = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.i("Script", key + "update");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Thread para que o aplicativo possa se conectar com o servidor na rede
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //Cria shared preference para armazemar os dados de preferencid do usuário
        SharedPreferences sp1 = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        //Recupera as string armazenadas no shared Preference
        String cpf = sp1.getString("login", "");
        String senha = sp1.getString("senha", "");

        //Quando a atividade inicial for ativada ele veirifica se existe preferences salva e a compara com a ja armazenada no erlang
        //Caso encontre chama a tela do cliente
        if (cpf.equals("adm") && senha.equals("senha")) {

            //Chama Layout client
          // enviaDadosparafragment(nomecompleto,perfil);

            Intent intent = new Intent(this, Cliente.class);

            Bundle bundle  =  new Bundle();
            bundle.putString("nomecompleto", nomecompleto);
            intent.putExtras(bundle);

            startActivity(intent);


        }

    }

    private void enviaDadosparafragment(String nomecompleto, PerfilFragment fragment) {



        Bundle bundle  =  new Bundle();
        bundle.putString("nomecompleto", nomecompleto);
        fragment.setArguments(bundle);



    }


    public void evCadastrarSe(View view) {

        setContentView(R.layout.activity_cadastrar_usuario);
        this.startActivity(new Intent(this, Cadastrar_Usuario.class));


    }

    public void evEntrar(View view) throws IOException {

        //******Create by Jeanderson  22/04/2017*****//
        //Botãp enttar acionado ele instancia os campos da Activity
        txtUsuario = (EditText) findViewById(R.id.usuario);
        txtSenha = (EditText) findViewById(R.id.password);
        salvarlogin = (CheckBox) findViewById(R.id.ckSalvarLogin);
        btnCadastrarCli = (Button) findViewById(R.id.btnCadastrarCli);

        //Quando usuário clicar nos campos de login e senha ele apaga os dados default para o preenchimento
        txtUsuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtUsuario.setText("");
            }


        });
        txtSenha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtSenha.setText("");
            }


        });

        //Insere a mascara no cpf
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", txtUsuario);
        txtUsuario.addTextChangedListener(maskCPF);

        //Retira a mascara do CPF para que possamos trabalhar se os '.'
        convCpf = txtUsuario.getText().toString().replaceAll("[^0123456789]", "");

        //Retirando a referencia  nulla
        String senha = txtSenha.getText().toString();

        //Cria um log, para confirmar as informações.
        Log.i("Script", "Sem conversao:" + txtUsuario.getText().toString());
        Log.i("Script", senha);

        //Verifica se o usuario esta com o cpf e senha cadastrados.
        if (txtUsuario.getText().toString().equals("adm") && senha.equals("senha")) {

            //Verifica se o salvar login foi marcado

            if (salvarlogin.isChecked()) {

                //Aramazena os dados na shared preferences em modo privato, impossibilitando que outra atividade altere esta preference.
                SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                //Colocando os dados no shared prefence
                editor.putString("login", txtUsuario.getText().toString());
                editor.putString("senha", senha);

                //faz o commit das preferencias
                editor.commit();

                //Chama tela de Cliente
                Toast.makeText(getApplicationContext(), "Perfil de ADM", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_cliente);
                this.startActivity(new Intent(this, Cliente.class));
            }

        }

        //******Create by Jeanderson  22/04/2017*****//
        //Caso usuário e senha não encontrados, realizamos o tratamento de erros.
        else {

            LoginServer = "LoginServer" + " " + convCpf + " " + txtSenha.getText().toString();

            if (Cadastrar_Usuario.validarCPF(convCpf)) {
                txtUsuario.setError("CPF Inválido");
                txtUsuario.setFocusable(true);
                txtUsuario.requestFocus();
            } else {
                retorno = processa.cadastrar1_no_server(LoginServer);
                String retorno2[] = retorno.split("/");
                String Status = retorno2[0];

                if (Status.equals("erro")) {
                    Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();

                } else {
                    if (Status.equals("false")) {
                        txtUsuario.setError("Usuario ou senha Invalido");
                        txtUsuario.setFocusable(true);
                        txtUsuario.requestFocus();
                    } else {
                        CPF = retorno2[1];
                        Nome = retorno2[2];

                        Toast.makeText(getApplicationContext(), "Perfil Cliente", Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.activity_cliente);
                        Intent cliente = new Intent(this, Cliente.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("nome", Nome);
                        bundle.putString("cpf" , CPF);

                        cliente.putExtras(bundle);
                        this.startActivity(cliente);

                    }
                }
            }
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.activity_main);

        SharedPreferences sp1 = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        convCpf = sp1.getString("login", "");
        Log.i("Script", convCpf);


        SharedPreferences.Editor editor = sp1.edit();
        editor.putString("login", convCpf);
        editor.commit();


//        SharedPreferences sp2 = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
//        count2 =sp2.getInt("count_2",0);
//        editor =sp2.edit();
//        editor.putInt("count_2",count2+1);
//        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        setContentView(R.layout.activity_main);

        SharedPreferences sp1 = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

    }

}




