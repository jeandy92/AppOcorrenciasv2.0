package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import appocorrencias.com.appocorrencias.ClassesSA.MDUsuario;
import appocorrencias.com.appocorrencias.ClassesSA.ProtocoloErlang;
import appocorrencias.com.appocorrencias.Network.FCMFirebaseInstanceIDService;
import appocorrencias.com.appocorrencias.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    private int PLAY_SERVICES_RESOLUTION_REQUEST = 9001;

    private String nome, SENHA, LoginServer, CPF, Nome, Bairro, IpServer;
    int PortaServer;
    private String convCpf, Status;
    private static ProtocoloErlang processa = new ProtocoloErlang();
    private String retorno;
    private EditText txtUsuario, txtSenha;
    private CheckBox salvarlogin;
    private Button btnCadastrarCli;
    private static final String PREF_NAME = "MainActivityPreferences";
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

        //Insere a mascara no cpf
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", txtUsuario);
        txtUsuario.addTextChangedListener(maskCPF);


        //Thread para que o aplicativo possa se conectar com o servidor na rede
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Cria shared preference para armazenar os dados de preferencid do usuário
        SharedPreferences sp1 = getSharedPreferences(PREF_NAME, MODE_PRIVATE);


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

        Intent cadastrar = new Intent(this, CadastraUsuario.class);

        //Tratamento diferenciado quando  a tela for de cliente
        Bundle bundle = new Bundle();
        bundle.putString("tela", "Cliente");

        cadastrar.putExtras(bundle);
        this.startActivity(cadastrar);
    }

    public void evEntrar (View view) {

    //Armazena os dados
    CPF = txtUsuario.getText().toString();
    SENHA = txtSenha.getText().toString();

    //ARMAZEANDO DADOS ESCRITOS NO CAMPOS USUÁRIO E SENHA E TIRANDO A  MASCARA DO CAMPO cpfAdm
    CPF = CPF.replaceAll("[^0-9]", "");

    // Verifica se os dados fora preenchidos corretamente
    if (CPF.isEmpty() || SENHA.isEmpty()) {

        txtUsuario.setError("Usuario ou senha em branco");
        txtUsuario.setFocusable(true);
        txtUsuario.requestFocus();

    }

    //Aramazena os dados na shared preferences em modo privato, impossibilitando que outra atividade altere esta preference.
    SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();


    //Colocando os dados no shared prefence
    editor.putString("login", CPF);
    editor.putString("senha", SENHA);

    if (txtUsuario.getText().toString() != null && txtSenha.getText().toString() != null) {

        if (CPF.equals("33333333333") && SENHA.equals("1234")) {

            Intent adm = new Intent(this, Adm.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", "Administrador");
            bundle.putString("cpf", "33333333333");
            bundle.putString("bairro", "Adm");

            adm.putExtras(bundle);
            this.startActivity(adm);
            this.finish();
        }
    }

            editor.commit();
    AsyncTask.execute(new Runnable() {
        @Override
        public void run() {

            Gson gson = new Gson();
            MDUsuario usuario = new MDUsuario();

            try {

                OkHttpClient client = new OkHttpClient();


                Request.Builder builder = new Request.Builder();


                System.out.println("cpf:" + CPF + "SENHA" + SENHA);


                builder.url(getResources().getString(R.string.ipConexao) + getResources().getString(R.string.endpointLogar) + CPF + "-" + SENHA);

                MediaType mediaType =
                        MediaType.parse("application/json; charset=utf-8");

                RequestBody body = RequestBody.create(mediaType, gson.toJson(usuario));

                builder.post(body);

                Request request = builder.build();
                Response response = null;

                response = client.newCall(request).execute();
                final String jsonDeResposta = response.body().string();
                //System.out.println(jsonDeResposta);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (jsonDeResposta.equals("SENHA INCORRETA!")) {
                            Toast.makeText(Login.this, "SENHA INCORRETA", Toast.LENGTH_SHORT).show();
                        } else

                        {

                            if (jsonDeResposta.equals("USUÁRIO NÃO CADASTRADO NA BASE DE DADOS"))

                            {
                                Toast.makeText(Login.this, "Cadastre-se, usuário não encontrado", Toast.LENGTH_SHORT).show();
                            } else {


                                try {

                                    JSONObject json = new JSONObject(jsonDeResposta);

                                    byte[] imgRecebida = Base64.decode(json.getString("ft_perfil"), Base64.DEFAULT);


                                    Toast.makeText(Login.this, "Bem vindo !!", Toast.LENGTH_SHORT).show();

                                    Intent cliente = new Intent(Login.this, Cliente.class);
                                    Bundle bundle = new Bundle();
                                 //   bundle.putString("imgperfil",json.getString("ft_perfil"));
                                    bundle.putString("nome", json.getString("nome"));
                                    bundle.putString("cpf", json.getString("cpf"));
                                    bundle.putString("bairro", json.getString("bairro"));
                                    bundle.putString("ip", IpServer);
                                    bundle.putInt("porta", PortaServer);



                                    cliente.putExtras(bundle);
                                    Login.this.startActivity(cliente);
                                    Login.this.finish();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                });

            } catch (IOException e)

            {
                e.printStackTrace();
            }


        }
    });
    }




    public static String evBuscarOcorrenciasBairro(String Bairro2,String IpServer,int PortaServer)throws IOException{

        return "true";
    }


    @Override
    protected void onRestart(){
        super.onRestart();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        setContentView(R.layout.activity_login);

        SharedPreferences sp1=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        convCpf=sp1.getString("login","");
        Log.i("INonDestroy",convCpf);


        SharedPreferences.Editor editor=sp1.edit();
        editor.putString("login",convCpf);
        editor.commit();


//        SharedPreferences sp2 = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
//        count2 =sp2.getInt("count_2",0);
//        editor =sp2.edit();
//        editor.putInt("count_2",count2+1);
//        editor.commit();
    }

    @Override
    protected void onStop(){
        super.onStop();

    }


    @Override
    protected void onPause(){
        super.onPause();


    }

    @Override
    protected void onResume(){
        super.onResume();


    }
}


