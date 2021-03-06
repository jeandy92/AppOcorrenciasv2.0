package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import appocorrencias.com.appocorrencias.ClassesSA.BuscaCep;
import appocorrencias.com.appocorrencias.ClassesSA.MDUsuario;
import appocorrencias.com.appocorrencias.ClassesSA.ProtocoloErlang;
import appocorrencias.com.appocorrencias.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.google.firebase.iid.FirebaseInstanceId.getInstance;

public class CadastraUsuario extends AppCompatActivity {

    //Variavel para gerar log
    private static final String TAG = "LOG";

    //Variaveis globais
    private Button btnCadastarUsuario;
    private Button btnBuscar;
    private EditText edtNome, edtCpf, edtTelefone, edtEmail, edtSenha, edtRua, edtBairro, edtCidade, edtNumero, edtCep, edtUf, edtDataNasc, edtConfirmarSenha, edtComplemento;

    //Variaveis para conversão e  referencia nula
    private String convCpf, convTelefone, convCep;

    //Dados para o envio do socket.
    private ProtocoloErlang processa = new ProtocoloErlang();

    private String telaCadUsuario;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        telaCadUsuario = bundle.getString("tela");


        //Button cadastrar
        btnCadastarUsuario = (Button) findViewById(R.id.CadastrarUsuario);

        //Váriaveis para o cadastro
        edtNome           = (EditText) findViewById(R.id.edtNome);
        edtCpf            = (EditText) findViewById(R.id.edtCPF);
        edtSenha          = (EditText) findViewById(R.id.edtSenha);
        edtDataNasc       = (EditText) findViewById(R.id.edtDataNasc);
        edtRua            = (EditText) findViewById(R.id.edtRua);
        edtTelefone       = (EditText) findViewById(R.id.edtTelefone);
        edtCep            = (EditText) findViewById(R.id.edtCep);
        edtBairro         = (EditText) findViewById(R.id.edtBairro);
        edtCidade         = (EditText) findViewById(R.id.edtCidade);
        edtUf             = (EditText) findViewById(R.id.edtUF);
        edtNumero         = (EditText) findViewById(R.id.edtNumero);
        edtEmail          = (EditText) findViewById(R.id.edtEmail);
        edtConfirmarSenha = (EditText) findViewById(R.id.edtConfirmarSenha);
        edtComplemento    = (EditText) findViewById(R.id.edtComplemento);

        //Setar default
        edtNome 	      .setText("Vinícius Mendes");
        edtCpf 	 		  .setText("34103982870");
        edtSenha 	      .setText("123456");
        edtConfirmarSenha .setText("123456");
        edtDataNasc 	  .setText("01/06/1981");
        edtRua 			  .setText("Rua Professora Nina Stocco - de 561/562 ao fim");
        edtTelefone 	  .setText("1141622246");
        edtCep 			  .setText("05767001");
        edtBairro 		  .setText("Jardim Catanduva");
        edtCidade	      .setText("Barueri");
        edtUf 			  .setText("SP");
        edtNumero 		  .setText("596");
        edtEmail 		  .setText("vinicius.mendes@benner.com.br");
        edtComplemento 	  .setText("casa1");

        // Inserindo Mascaras.
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", edtCpf);
        MaskEditTextChangedListener maskTelefone = new MaskEditTextChangedListener("(##)#####-####", edtTelefone);
        MaskEditTextChangedListener maskCEP = new MaskEditTextChangedListener("#####-###", edtCep);
        MaskEditTextChangedListener maskData = new MaskEditTextChangedListener("##/##/####", edtDataNasc);

        edtDataNasc.addTextChangedListener(maskData);
        edtCpf.addTextChangedListener(maskCPF);
        edtTelefone.addTextChangedListener(maskTelefone);
        edtCep.addTextChangedListener(maskCEP);
    }


    // Método para buscar Cep
    public void evBuscarCep(View v) throws IOException {

        convCep = edtCep.getText().toString().replaceAll("[^0123456789]", "");

        BuscaCep busca = new BuscaCep();

        String Status = busca.getEndereco(convCep);
        if (Status.equals("erro")) {
            Toast.makeText(this, "Erro na Conexão", Toast.LENGTH_SHORT).show();
        } else {
            edtRua.setText(Status);
            edtBairro.setText(busca.getBairro(convCep));
            edtCidade.setText(busca.getCidade(convCep));
            edtUf.setText(busca.getUF(convCep));
        }
    }



    //Método para cadastrar usuario
    public void evCadastrarUsuario(View v) {

        if (edtCpf.getText().toString().isEmpty()) {
            edtCpf.setError("Faltou Preencher");
            edtCpf.setFocusable(true);
            edtCpf.requestFocus();

        } else {

            if (edtNome.getText().toString().isEmpty()) {
                edtNome.setError("Faltou Preencher");
                edtNome.setFocusable(true);
                edtNome.requestFocus();
            } else {
                if (edtSenha.getText().toString().isEmpty()) {
                    edtSenha.setError("Faltou Preencher");
                    edtSenha.setFocusable(true);
                    edtSenha.requestFocus();
                } else {

                    if (edtConfirmarSenha.getText().toString().isEmpty()) {

                        edtConfirmarSenha.setError("Faltou Preencher");
                        edtConfirmarSenha.setFocusable(true);
                        edtConfirmarSenha.requestFocus();

                    } else {
                        if (!edtSenha.getText().toString().equals(edtConfirmarSenha.getText().toString())) {
                            edtConfirmarSenha.setError("Senhas não conferem");
                            edtConfirmarSenha.setFocusable(true);
                            edtConfirmarSenha.requestFocus();

                        }


                    }
                    if (edtDataNasc.getText().toString().isEmpty()) {
                        edtDataNasc.setError("Faltou Preencher");
                        edtDataNasc.setFocusable(true);
                        edtDataNasc.requestFocus();
                    } else {
                        if (edtRua.getText().toString().isEmpty()) {
                            edtRua.setError("Faltou Preencher");
                            edtRua.setFocusable(true);
                            edtRua.requestFocus();
                        } else {

                            if (edtTelefone.getText().toString().isEmpty()) {
                                edtTelefone.setError("Faltou Preencher");
                                edtTelefone.setFocusable(true);
                                edtTelefone.requestFocus();
                            } else {
                                if (edtCep.getText().toString().isEmpty()) {
                                    edtCep.setError("Faltou Preencher");
                                    edtCep.setFocusable(true);
                                    edtCep.requestFocus();
                                } else {
                                    if (edtBairro.getText().toString().isEmpty()) {
                                        edtBairro.setError("Faltou Preencher");
                                        edtBairro.setFocusable(true);
                                        edtBairro.requestFocus();
                                    } else {
                                        if (edtCidade.getText().toString().isEmpty()) {
                                            edtCidade.setError("Faltou Preencher");
                                            edtCidade.setFocusable(true);
                                            edtCidade.requestFocus();
                                        } else {
                                            if (edtUf.getText().toString().isEmpty()) {
                                                edtUf.setError("Faltou Preencher");
                                                edtUf.setFocusable(true);
                                                edtUf.requestFocus();
                                            } else {
                                                if (edtNumero.getText().toString().isEmpty()) {
                                                    edtNumero.setError("Faltou Preencher");
                                                    edtNumero.setFocusable(true);
                                                    edtNumero.requestFocus();
                                                } else {
                                                    if (edtEmail.getText().toString().isEmpty()) {
                                                        edtEmail.setError("Faltou Preencher");
                                                        edtEmail.setFocusable(true);
                                                        edtEmail.requestFocus();
                                                    } else {

                                                        if (edtComplemento.getText().toString().isEmpty()) {
                                                            edtComplemento.setError("Faltou Preencher");
                                                            edtComplemento.setFocusable(true);
                                                            edtComplemento.requestFocus();
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
            }
        }

            if(edtCpf.getText().toString().isEmpty()&&validarCPF(edtCpf.getText().toString())){
                System.out.println("Inválidooooooooooooo");
                edtCpf.setError("cpf inválido");
                edtCpf.setFocusable(true);
                edtCpf.requestFocus();
            } else {


                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            MDUsuario usu = new MDUsuario();
                                                     Gson gson = new Gson();


                        //Tirando a mascara dos campos
                        convCpf      = edtCpf.getText().toString().replaceAll("[^0123456789]", "");
                        convTelefone = edtTelefone.getText().toString().replaceAll("[^0123456789]", "");
                        convCep      = edtCep.getText().toString().replaceAll("[^0123456789]", "");


                        try {
                            usu.setCpf(convCpf);
                            usu.setNome(edtNome.getText().toString());
                            usu.setTelefone(convTelefone);
                            usu.setDataDeNascimento(edtDataNasc.getText().toString());
                            usu.setCep(convCep);
                            usu.setRua(edtRua.getText().toString());
                            usu.setNumero(edtNumero.getText().toString());
                            usu.setComplemento(edtComplemento.getText().toString());
                            usu.setBairro(edtBairro.getText().toString());
                            usu.setCidade(edtCidade.getText().toString());
                            usu.setEmail(edtEmail.getText().toString());
                            usu.setSenha(edtSenha.getText().toString());
                            usu.setConfirmarSenha(edtConfirmarSenha.getText().toString());
                            usu.setUf(edtUf.getText().toString());

                            OkHttpClient client = new OkHttpClient();


                            Request.Builder builder = new Request.Builder();

                            builder.url(getResources().getString(R.string.ipConexao)  + getResources().getString(R.string.endpointCadastrarUsuario));

                            MediaType mediaType =
                                    MediaType.parse("application/json; charset=utf-8");


                            RequestBody body = RequestBody.create(mediaType, gson.toJson(usu));

                            builder.post(body);

                            Request request = builder.build();
                            Response response = null;

                            response = client.newCall(request).execute();
                            final String jsonDeResposta = response.body().string();


                            CadastraUsuario.this.runOnUiThread(new Runnable() {
                                public void run() {


                                    if (jsonDeResposta.equals("USUARIO CADASTRADO COM SUCESSO !!")) {


                                        processa.criandoGrupoNotificacao(getInstance().getToken(), edtBairro.getText().toString(), edtBairro.getText().toString());
                                        processa.adicionandoUsuarioNotificacao(getInstance().getToken(), edtBairro.getText().toString());
                                        Toast.makeText(CadastraUsuario.this, "USUARIO CADASTRADO COM SUCESSO !!", Toast.LENGTH_SHORT).show();

                                        setContentView(R.layout.activity_login);
                                        CadastraUsuario.this.startActivity(new Intent(CadastraUsuario.this, Login.class));
                                        CadastraUsuario.this.finish();

                                    } else {
                                        if (jsonDeResposta.equals("CPF JÁ CADASTRADO")) {
                                            Toast.makeText(CadastraUsuario.this, "CPF JÁ CADASTRADO !!", Toast.LENGTH_SHORT).show();
                                            edtCpf.setError("Cpf já cadastrado");
                                            edtCpf.setFocusable(true);
                                            edtCpf.requestFocus();
                                        } else {
                                            if (jsonDeResposta.isEmpty()) {
                                                Toast.makeText(CadastraUsuario.this, "ERRO DE CONEXÃO COM O SERVIDOR !", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }


                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }}


    //Método para Validar cpf
    public static boolean validarCPF(String validaCpf) {
        if (validaCpf.equals("00000000000") || validaCpf.equals("11111111111")
                || validaCpf.equals("22222222222") || validaCpf.equals("33333333333")
                || validaCpf.equals("44444444444") || validaCpf.equals("55555555555")
                || validaCpf.equals("66666666666") || validaCpf.equals("77777777777")
                || validaCpf.equals("88888888888") || validaCpf.equals("99999999999")) {
            return true;
        }
        Log.i(TAG, "Validando...." + validaCpf);
        char dig10, dig11;
        int sm, i, r, num, peso;
        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (validaCpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48);
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (validaCpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);
            if ((dig10 == validaCpf.charAt(9)) && (dig11 == validaCpf.charAt(10)))
                return (false);
            else
                return (true);
        } catch (Exception erro) {
            return (true);
        }
    }

    //Método para validar edtEmail
    public final static boolean validarEmail(String txtEmail) {
        if (TextUtils.isEmpty(txtEmail)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        if (telaCadUsuario.equals("Adm")) {
            Intent adm = new Intent(this, Adm.class);

            Bundle bundle = new Bundle();
            bundle.putString("convNome", "Administrador");
            bundle.putString("cpf", "33333333333");
            bundle.putString("convBairro", "Adm");

            adm.putExtras(bundle);
            this.startActivity(adm);
            this.finish();
        } else {

            this.startActivity(new Intent(this, Login.class));
            this.finish();
        }
    }
}


