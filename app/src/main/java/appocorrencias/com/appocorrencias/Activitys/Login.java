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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.IOException;

import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas;
import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
import appocorrencias.com.appocorrencias.Network.FCMFirebaseInstanceIDService;
import appocorrencias.com.appocorrencias.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

import static com.google.firebase.iid.FirebaseInstanceId.getInstance;

public class Login extends AppCompatActivity {

    private int PLAY_SERVICES_RESOLUTION_REQUEST = 9001;

    private byte[] imagem;
    private String nome, SENHA, LoginServer, CPF, Nome, Bairro;
    private String convCpf, Status;
    private static ProcessaSocket processa = new ProcessaSocket();
    private String retorno;
    private View view;
    private String nomecompleto = "Jeanderson de Almeeida Dyorgenes";
    private EditText txtUsuario, txtSenha;
    private CheckBox salvarlogin;
    private Button btnCadastrarCli;
    private static final String PREF_NAME = "MainActivityPreferences";
    private int count1;
    private int count2;
    private static final String TAG = "Login";

    //private DatabaseReference firebasereferencia = FirebaseDatabase.getInstance().getReference();

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
        setContentView(R.layout.activity_login);


        //Verifica o  status do Play Services no seu aplicativo
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int resultCode = googleAPI.isGooglePlayServicesAvailable(getApplicationContext());
        Log.i("RESULTCODE", "------------------------" + String.valueOf(resultCode));

        if (ConnectionResult.SUCCESS != resultCode) {
            if (googleAPI.isUserResolvableError(resultCode)) {
                Toast.makeText(this, "Google Play Service is not install/enable em seu dispositivo ", Toast.LENGTH_SHORT).show();
                googleAPI.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);

            } else {
                Toast.makeText(this, "Este Celular não suporta o google play services", Toast.LENGTH_SHORT).show();
            }
        } else {
            //Inicia o serviço
            Intent intent = new Intent(this, FCMFirebaseInstanceIDService.class);
            startService(intent);
        }


        //******Create by Jeanderson  22/04/2017*****//

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


        //Thread para que o aplicativo possa se conectar com o servidor na rede
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Cria shared preference para armazenar os dados de preferencid do usuário
        SharedPreferences sp1 = getSharedPreferences(PREF_NAME, MODE_APPEND);


        //Retirando a referencia  nulla
        SENHA = txtSenha.getText().toString();
        CPF = txtUsuario.getText().toString();


        //Recupera as string armazenadas no shared Preference
        CPF = sp1.getString("login", "");
        SENHA = sp1.getString("senha", "");

        //Cria um log, para confirmar as informações.
        Log.i("onCreate", CPF);
        Log.i("onCreate", SENHA);

        txtUsuario.setText(CPF);
        txtSenha.setText(SENHA);


    }


    public void evCadastrarSe(View view) {

        Intent cadastrar = new Intent(this, CadastrarUsuario.class);

        Bundle bundle = new Bundle();
        bundle.putString("tela" , "Cliente");

        cadastrar.putExtras(bundle);
        this.startActivity(cadastrar);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void adm(View view) {

        setContentView(R.layout.activity_adm);
        this.startActivity(new Intent(this, Adm.class));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    public void evEntrar(View view) throws IOException {

        String token = getInstance().getToken();


        Log.d(TAG, token);

        Toast.makeText(Login.this, token, Toast.LENGTH_SHORT).show();

        SENHA = txtSenha.getText().toString();
        CPF = txtUsuario.getText().toString();

        //ARMAZEANDO DADOS ESCRITOS NO CAMPOS USUÁRIO E SENHA E TIRANDO A  MASCARA DO CAMPO CPF
        CPF = CPF.replaceAll("[^0-9]", "");


        //ARMAZENANDO LOG DOS DADOS FORNECIDOS PELO USUÁRIO
        Log.i("evEntrar", CPF);
        Log.i("evEntrar", SENHA);

        if (salvarlogin.isChecked()) {

            Log.i("isCheck", CPF);
            Log.i("isCheck", SENHA);

            //Aramazena os dados na shared preferences em modo privato, impossibilitando que outra atividade altere esta preference.
            SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();


            //Colocando os dados no shared prefence
            editor.putString("login", CPF);
            editor.putString("senha", SENHA);

            editor.commit();

        }


        if (txtUsuario.getText().toString() != null && txtSenha.getText().toString() != null) {

            if (CPF.equals("33333333333") && SENHA.equals("1234")) {

                Intent adm = new Intent(this, Adm.class);

                Bundle bundle = new Bundle();
                bundle.putString("nome", "Administrador");
                bundle.putString("cpf", "33333333333");
                bundle.putString("bairro", "Adm");

                adm.putExtras(bundle);
                this.startActivity(adm);

            } else {


                if (CadastrarUsuario.validarCPF(CPF)) {
                    txtUsuario.setError("CPF Inválido");
                    txtUsuario.setFocusable(true);
                    txtUsuario.requestFocus();
                    Log.i("evEntrar(IF2)", CPF);
                    Log.i("evEntrar(IF2)", SENHA);
                } else {

                    LoginServer = "LoginServer" + " " + CPF + " " + SENHA;
                    Log.i("evEntrar(ELSE)", CPF);
                    Log.i("evEntrar(ELSE)", SENHA);

                    retorno = processa.cadastrar1_no_server(LoginServer);
                    String retorno2[] = retorno.split("/");
                    Status = retorno2[0];


                    if (Status.equals("erro")) {
                        Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();

                    } else {
                        if (Status.equals("false")) {
                            txtUsuario.setError("Usuario ou senha Invalido");
                            txtUsuario.setFocusable(true);
                            txtUsuario.requestFocus();
                        } else {
                            if (Status.equals("true")) {
                                CPF = retorno2[1];
                                Nome = retorno2[2];
                                String Bairro2 = retorno2[3];

                                evBuscarOcorrenciasBairro(Bairro2);

                                Intent cliente = new Intent(this, Cliente.class);

                                Bundle bundle = new Bundle();
                                bundle.putString("nome", Nome);
                                bundle.putString("cpf", CPF);
                                bundle.putString("bairro", Bairro2);

                                cliente.putExtras(bundle);
                                this.startActivity(cliente);
                            }
                        }
                    }
                }
            }
        } else {

            txtUsuario.setError("Usuario ou senha em branco");
            txtUsuario.setFocusable(true);
            txtUsuario.requestFocus();
        }
    }

    public static void evBuscarOcorrenciasBairro(String Bairro2) throws IOException {

        String BuscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradasBairro" + Bairro2;
        //Toast.makeText(this, "Ocorrencias Registradas no meu Bairro ", Toast.LENGTH_SHORT).show();
        String retorno = processa.cadastrar1_no_server(BuscarOcorrenciasRegistradas);

        if (retorno.equals("false")) {
            // Toast.makeText(this, "Não há ocorrencias cadastradas no seu bairro", Toast.LENGTH_SHORT).show();
        } else {
            // Pegando quantidade de Ocorrencias
            int qtdOcorrencia = ArrayOcorrenciasRegistradas.getQuantidadeOcorrencia(retorno);


            // Pegando dados e Adicioanando dados no Array
            for (int i = 0; i < qtdOcorrencia; i++) {
                String TodasOcorrencias[] = retorno.split("///");

                String Ocorrencia = TodasOcorrencias[i];
                String OcorrenciaUm[] = Ocorrencia.split("//");
                String Nr = OcorrenciaUm[1];
                String CPFOco = OcorrenciaUm[2];
                String Rua = OcorrenciaUm[3];
                String Bairro = OcorrenciaUm[4];
                String Cidade = OcorrenciaUm[5];
                String UF = OcorrenciaUm[6];
                String Descricao = OcorrenciaUm[7];
                String Data = OcorrenciaUm[8];
                String Tipo = OcorrenciaUm[9];
                String Anonimo = OcorrenciaUm[10];
                String Apelido = OcorrenciaUm[11];

                DadosOcorrencias dado = new DadosOcorrencias(Nr, CPFOco, Rua, Bairro, Cidade, UF, Descricao, Data, Tipo, Anonimo, Apelido);

                ArrayOcorrenciasRegistradas.adicionar(dado);
            }
            //Toast.makeText(this, "Mostrando Ocorrencias no seu Bairro ", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.activity_login);

        SharedPreferences sp1 = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        convCpf = sp1.getString("login", "");
        Log.i("INonDestroy", convCpf);


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

    }


    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}


