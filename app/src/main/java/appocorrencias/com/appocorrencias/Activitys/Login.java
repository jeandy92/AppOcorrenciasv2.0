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
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfilComentarios;
import appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas;
import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
import appocorrencias.com.appocorrencias.Network.FCMFirebaseInstanceIDService;
import appocorrencias.com.appocorrencias.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

import static appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias.evBuscarImagens;
import static com.google.firebase.iid.FirebaseInstanceId.getInstance;

//******Create by Jeanderson  22/04/2017*****//

public class Login extends AppCompatActivity {

    //Variaveis para Request
    private int PLAY_SERVICES_RESOLUTION_REQUEST = 9001;
    private static final String PREF_NAME = "MainActivityPreferences";
    private static final String TAG = "Login";

    //Váriaveis para usar no Edit Text
    private EditText edtUsuario, edtSenha;
    private CheckBox cbxSalvarlogin;
    private Button btnCadastrarCli;
    private View view;

    //Objetos para usar no processa socket
    private static ProcessaSocket processa = new ProcessaSocket();

    //Váriaveis Locais
    private String nome, senha, loginServer, cpf, vNome;
    private String convCpf, Status;
    private String retorno;

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

        edtUsuario = (EditText) findViewById(R.id.usuario);
        edtSenha = (EditText) findViewById(R.id.password);
        cbxSalvarlogin = (CheckBox) findViewById(R.id.ckSalvarLogin);
        btnCadastrarCli = (Button) findViewById(R.id.btnCadastrarCli);

        //Quando usuário clicar nos campos de login e senha ele apaga os dados default para o preenchimento
        edtUsuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                edtUsuario.setText("");
            }


        });
        edtSenha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                edtSenha.setText("");
            }


        });

        //Insere a mascara no cpf
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", edtUsuario);
        edtUsuario.addTextChangedListener(maskCPF);


        //Thread para que o aplicativo possa se conectar com o servidor na rede
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Cria shared preference para armazenar os dados de preferencid do usuário
        SharedPreferences sp1 = getSharedPreferences(PREF_NAME, MODE_APPEND);


        //Retirando a referencia  nulla
        senha = edtSenha.getText().toString();
        cpf = edtUsuario.getText().toString();


        //Recupera as string armazenadas no shared Preference
        cpf = sp1.getString("login", "");
        senha = sp1.getString("senha", "");

        //Cria um log, para confirmar as informações.
        Log.i("onCreate", cpf);
        Log.i("onCreate", senha);

        edtUsuario.setText(cpf);
        edtSenha.setText(senha);


    }


    public void evCadastrarSe(View view) {

        Intent cadastrar = new Intent(this, CadastrarUsuario.class);

        Bundle bundle = new Bundle();
        bundle.putString("tela", "Cliente");

        cadastrar.putExtras(bundle);
        this.startActivity(cadastrar);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    public void evEntrar(View view) throws IOException {

        String token = getInstance().getToken();


        Log.d(TAG, token);

        //Toast.makeText(Login.this, token, Toast.LENGTH_SHORT).show();
        ProcessaSocket.enviandoNotificacaoGrupo(token,"Jardim lok");



        senha = edtSenha.getText().toString();
        cpf = edtUsuario.getText().toString();

        //ARMAZEANDO DADOS ESCRITOS NO CAMPOS USUÁRIO E senha E TIRANDO A  MASCARA DO CAMPO cpfAdm
        cpf = cpf.replaceAll("[^0-9]", "");


        //ARMAZENANDO LOG DOS DADOS FORNECIDOS PELO USUÁRIO
        Log.i("evEntrar", cpf);
        Log.i("evEntrar", senha);

        if (cbxSalvarlogin.isChecked()) {

            Log.i("isCheck", cpf);
            Log.i("isCheck", senha);

            //Aramazena os dados na shared preferences em modo privato, impossibilitando que outra atividade altere esta preference.
            SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();


            //Colocando os dados no shared prefence
            editor.putString("login", cpf);
            editor.putString("senha", senha);

            editor.commit();

        }


        if (edtUsuario.getText().toString() != null && edtSenha.getText().toString() != null) {

            if (cpf.equals("33333333333") && senha.equals("1234")) {

                Intent adm = new Intent(this, Adm.class);

                Bundle bundle = new Bundle();
                bundle.putString("nome", "Administrador");
                bundle.putString("cpf", "33333333333");
                bundle.putString("bairro", "Adm");

                adm.putExtras(bundle);
                this.startActivity(adm);
                this.finish();

            } else {

                if (CadastrarUsuario.validarCPF(cpf)) {
                    edtUsuario.setError("cpfAdm Inválido");
                    edtUsuario.setFocusable(true);
                    edtUsuario.requestFocus();
                    Log.i("evEntrar(IF2)", cpf);
                    Log.i("evEntrar(IF2)", senha);
                } else {

                    loginServer = "loginServer" + " " + cpf + " " + senha;
                    Log.i("evEntrar(ELSE)", cpf);
                    Log.i("evEntrar(ELSE)", senha);

                    retorno = processa.primeiroCadastroNoServidor(loginServer);
                    String retorno2[] = retorno.split("/");
                    Status = retorno2[0];

                    if (Status.equals("erro")) {
                        Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();

                    } else {
                        if (Status.equals("false")) {
                            edtUsuario.setError("Usuario ou senha Invalido");
                            edtUsuario.setFocusable(true);
                            edtUsuario.requestFocus();
                        } else {
                            if (Status.equals("true")) {
                                cpf = retorno2[1];
                                vNome = retorno2[2];
                                String Bairro2 = retorno2[3];

                                String retornoBairro = evBuscarOcorrenciasBairro(Bairro2);
                                if (retornoBairro.equals("erro")) {
                                    Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (retornoBairro.equals("true") || retornoBairro.equals("false")) {
                                        String retornoImagem = evBuscarImagens(cpf, "cpf");
                                        if (retornoImagem.equals("erro")) {
                                            Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (retornoImagem.equals("true") || retornoImagem.equals("false")) {
                                                Intent cliente = new Intent(this, Cliente.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("nome", vNome);
                                                bundle.putString("cpf", cpf);
                                                bundle.putString("bairro", Bairro2);

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
        } else {

            edtUsuario.setError("Usuario ou senha em branco");
            edtUsuario.setFocusable(true);
            edtUsuario.requestFocus();
        }
    }


    public static String evBuscarOcorrenciasBairro(String Bairro2) throws IOException {

        String BuscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradasBairro" + Bairro2;
        //Toast.makeText(this, "Ocorrencias Registradas no meu bairro ", Toast.LENGTH_SHORT).show();
        ArrayImagensPerfilComentarios.deleteBitmap();
        String retorno = ProcessaSocket.buscarDadosImagensServer(BuscarOcorrenciasRegistradas);

        if (retorno.equals("false")) {
            // Toast.makeText(this, "Não há ocorrencias cadastradas no seu bairro", Toast.LENGTH_SHORT).show();
            return "false";
        } else {
            if (retorno.equals("erro")) {
                // Toast.makeText(this, "Não há ocorrencias cadastradas no seu bairro", Toast.LENGTH_SHORT).show();
                return "erro";
            } else {
                // Pegando quantidade de Ocorrencias
                int qtdOcorrencia = ArrayOcorrenciasRegistradas.getQuantidadeOcorrencia(retorno);

                // Pegando dados e Adicioanando dados no Array
                for (int i = 0; i < qtdOcorrencia; i++) {
                    String todasOcorrencias[] = retorno.split("///");

                    String ocorrencia           = todasOcorrencias[i];
                    String primeiraOcorrencia[] = ocorrencia.split("//");
                    String numeroOcorrencia     = primeiraOcorrencia[1];
                    String focoCpf              = primeiraOcorrencia[2];
                    String ruaOcorrencia        = primeiraOcorrencia[3];
                    String bairroOcorrencia     = primeiraOcorrencia[4];
                    String cidadeOcorrencia     = primeiraOcorrencia[5];
                    String ufOcorrencia         = primeiraOcorrencia[6];
                    String descricaoOcorrencia  = primeiraOcorrencia[7];
                    String dataOcorrencia       = primeiraOcorrencia[8];
                    String tipoOcorrencia       = primeiraOcorrencia[9];
                    String anonimoOcorrencia    = primeiraOcorrencia[10];
                    String apelidoOcorrencia    = primeiraOcorrencia[11];

                    DadosOcorrencias dado = new DadosOcorrencias(numeroOcorrencia, focoCpf, ruaOcorrencia, bairroOcorrencia, cidadeOcorrencia, ufOcorrencia, descricaoOcorrencia, dataOcorrencia, tipoOcorrencia, anonimoOcorrencia, apelidoOcorrencia);

                    ArrayOcorrenciasRegistradas.adicionar(dado);
                }

            }
        }
        return "true";
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

    }

    private SharedPreferences.OnSharedPreferenceChangeListener callback = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.i("Script", key + "update");
        }
    };


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


