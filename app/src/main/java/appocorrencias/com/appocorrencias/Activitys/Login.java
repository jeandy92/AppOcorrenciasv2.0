package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import appocorrencias.com.appocorrencias.ClassesSA.MDUsuario;
import appocorrencias.com.appocorrencias.ClassesSA.ProtocoloErlang;
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfilComentarios;
import appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas;
import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
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
    private final String ipConexao = "http://192.168.53.86:62001";
    private final String endpointLogar = "/RestWO/services/WebserviceOcorrencia/logarUsuario/";


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
    if(CPF.isEmpty()||SENHA.isEmpty()) {

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

    editor.commit();
    AsyncTask.execute(new Runnable() {
        @Override
        public void run() {

            Gson gson = new Gson();
            MDUsuario usuario = new MDUsuario();

            try {

                OkHttpClient client = new OkHttpClient();


                //String url = "http://192.168.0.16:62001/RestWO/services/WebserviceOcorrencia/logarUsuario/"+"{"+login.getText()+"}"+"-"+"{"+senha.getText()+"}";
            /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            desc.setText(jsonDeResposta);

                        }
                    });*/

                Request.Builder builder = new Request.Builder();


                System.out.println("cpf:"+CPF+"SENHA"+SENHA);


                builder.url(ipConexao + endpointLogar  + CPF  + "-" + SENHA );

                MediaType mediaType =
                        MediaType.parse("application/json; charset=utf-8");

                RequestBody body = RequestBody.create(mediaType, gson.toJson(usuario));

                builder.post(body);

                Request request = builder.build();
                Response response = null;

                response = client.newCall(request).execute();
                final String jsonDeResposta = response.body().string();
                System.out.println(jsonDeResposta);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (jsonDeResposta.equals("SENHA INCORRETA!"))
                        {
                            Toast.makeText(Login.this, "SENHA INCORRETA", Toast.LENGTH_SHORT).show();
                        }
                        else

                        {

                            if (jsonDeResposta.equals("USUÁRIO NÃO CADASTRADO NA BASE DE DADOS"))

                            {
                                Toast.makeText(Login.this, "Cadastre-se, usuário não encontrado", Toast.LENGTH_SHORT).show();
                            }

                            else

                            {

                                try {

                                    JSONObject json = new JSONObject(jsonDeResposta);



                                Toast.makeText(Login.this, "Bem vindo !!", Toast.LENGTH_SHORT).show();

                                Intent cliente = new Intent(Login.this, Cliente.class);
                                Bundle bundle = new Bundle();
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
                    }});

            }
            catch(IOException e)

            {
                e.printStackTrace();
            }


        }
    });
}

    ///////////////////////////////////////////////////////////////////////////////////////////
 /*   public void evEntrar(View view) throws IOException {

        String token = getInstance().getToken();


        Log.d(TAG, token);

        //Toast.makeText(Login.this, token, Toast.LENGTH_SHORT).show();
        //ProtocoloErlang.enviandoNotificacaoGrupo(token, "Jardim lok");


        SENHA = txtSenha.getText().toString();
        CPF = txtUsuario.getText().toString();

        //ARMAZEANDO DADOS ESCRITOS NO CAMPOS USUÁRIO E SENHA E TIRANDO A  MASCARA DO CAMPO cpfAdm
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
                this.finish();

            } else {

                if (CadastraUsuario.validarCPF(CPF)) {
                    txtUsuario.setError("CPF Inválido");
                    txtUsuario.setFocusable(true);
                    txtUsuario.requestFocus();
                    Log.i("evEntrar(IF2)", CPF);
                    Log.i("evEntrar(IF2)", SENHA);
                } else {

                    String DadosServidor = null;

                    try {
                        DadosServidor = ProtocoloErlang.BuscarServidor();
                        Log.i("evEntrar(Servidor)", DadosServidor);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (DadosServidor.equals("erro")) {
                        Toast.makeText(this, "Erro na Conexão DNS", Toast.LENGTH_SHORT).show();
                    } else {
                        if (DadosServidor != null) {
                            String retorno2[] = DadosServidor.split("//");
                            IpServer = retorno2[0];
                            PortaServer = Integer.parseInt(retorno2[1]);

                            LoginServer = "LoginServer" + " " + CPF + " " + SENHA;
                            Log.i("evEntrar(ELSE)", CPF);
                            Log.i("evEntrar(ELSE)", SENHA);

                            retorno = processa.primeiroCadastroNoServidor(LoginServer, IpServer, PortaServer);
                            String retornoCadastro[] = retorno.split("/");
                            Status = retornoCadastro[0];

                            if (Status.equals("erro")) {
                                Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();

                            } else {
                                if (Status.equals("false")) {
                                    txtUsuario.setError("Usuario ou senha Invalido");
                                    txtUsuario.setFocusable(true);
                                    txtUsuario.requestFocus();
                                } else {
                                    if (Status.equals("true")) {
                                        CPF = retornoCadastro[1];
                                        Nome = retornoCadastro[2];
                                        String Bairro2 = retornoCadastro[3];

                                        String retornoBairro = evBuscarOcorrenciasBairro(Bairro2, IpServer, PortaServer);
                                        if (retornoBairro.equals("erro")) {
                                            Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (retornoBairro.equals("true") || retornoBairro.equals("false")) {
                                                String retornoImagem = evBuscarImagens(CPF, "cpf", IpServer, PortaServer);
                                                if (retornoImagem.equals("erro")) {
                                                    Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    if (retornoImagem.equals("true") || retornoImagem.equals("false")) {
                                                        Intent cliente = new Intent(this, Cliente.class);
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("nome", Nome);
                                                        bundle.putString("cpf", CPF);
                                                        bundle.putString("bairro", Bairro2);
                                                        bundle.putString("ip", IpServer);
                                                        bundle.putInt("porta", PortaServer);

                                                        cliente.putExtras(bundle);
                                                        this.startActivity(cliente);
                                                        this.finish();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
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

*/



    public static String evBuscarOcorrenciasBairro(String Bairro2,String IpServer,int PortaServer)throws IOException{

        String BuscarOcorrenciasRegistradas="BuscarOcorrenciasRegistradasBairro "+Bairro2;
        //Toast.makeText(this, "Ocorrencias Registradas no meu bairro ", Toast.LENGTH_SHORT).show();
        ArrayImagensPerfilComentarios.deleteBitmap();
        String retorno= ProtocoloErlang.buscarDadosImagensServer(BuscarOcorrenciasRegistradas,IpServer,PortaServer);

        if(retorno.equals("false")){
            // Toast.makeText(this, "Não há ocorrencias cadastradas no seu bairro", Toast.LENGTH_SHORT).show();
            return"false";
        }else{
            if(retorno.equals("erro")){
                // Toast.makeText(this, "Não há ocorrencias cadastradas no seu bairro", Toast.LENGTH_SHORT).show();
                return"erro";
            }else{
                // Pegando quantidade de Ocorrencias
                int qtdOcorrencia=ArrayOcorrenciasRegistradas.getQuantidadeOcorrencia(retorno);

                // Pegando dados e Adicioanando dados no Array
                for(int i=0;i<qtdOcorrencia; i++){
                    String TodasOcorrencias[]=retorno.split("///");

                    String Ocorrencia=TodasOcorrencias[i];
                    String OcorrenciaUm[]=Ocorrencia.split("//");
                    String Nr=OcorrenciaUm[1];
                    String CPFOco=OcorrenciaUm[2];
                    String Rua=OcorrenciaUm[3];
                    String Bairro=OcorrenciaUm[4];
                    String Cidade=OcorrenciaUm[5];
                    String UF=OcorrenciaUm[6];
                    String Descricao=OcorrenciaUm[7];
                    String Data=OcorrenciaUm[8];
                    String Tipo=OcorrenciaUm[9];
                    String Anonimo=OcorrenciaUm[10];
                    String Apelido=OcorrenciaUm[11];

                    DadosOcorrencias dado=new DadosOcorrencias(Nr,CPFOco,Rua,Bairro,Cidade,UF,Descricao,Data,Tipo,Anonimo,Apelido);

                    ArrayOcorrenciasRegistradas.adicionar(dado);
                }
                //Toast.makeText(this, "Mostrando Ocorrencias no seu bairro ", Toast.LENGTH_SHORT).show();
            }
        }
        return"true";
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


