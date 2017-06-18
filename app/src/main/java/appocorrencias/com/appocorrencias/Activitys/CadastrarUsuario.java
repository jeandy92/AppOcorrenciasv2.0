package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.text.Normalizer;

import appocorrencias.com.appocorrencias.ClassesSA.BuscarCep;
import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastrarUsuario extends AppCompatActivity {

    //Variavel para gerar log
    private static final String TAG = "LOG";

    //Variaveis globais
    private Button btnCadastarUsuario;
    private Button btnBuscar;
    private EditText edtNome, edtCpf, edtTelefone, edtEmail, edtSenha, edtRua, edtBairro, edtCidade, edtNumero, edtCep, edtUf, edtDataNasc, edtConfirmarSenha, edtComplemento;

    //Variaveis para conversão e  referencia nula
    private String convCpf, convTelefone, convCep, convEmail, convSenha,
            convNumero, convRua, convBairro, convCidade, convUf, convNome, convDataNasc, convConfirmarSenha, convComplemento, Ip;
    private int Porta;

    //Váriaveis para serem utilizadas  no envio do cadastro
    protected String primeiroCadastro, cadastroNome, cadastroRua, cadastroBairro, cadastroCidade;


    //Dados para o envio do socket.
    private ProcessaSocket processa = new ProcessaSocket();
    private boolean retorno;
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
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtCpf = (EditText) findViewById(R.id.edtCPF);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtDataNasc = (EditText) findViewById(R.id.edtDataNasc);
        edtRua = (EditText) findViewById(R.id.edtRua);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);
        edtCep = (EditText) findViewById(R.id.edtCep);
        edtBairro = (EditText) findViewById(R.id.edtBairro);
        edtCidade = (EditText) findViewById(R.id.edtCidade);
        edtUf = (EditText) findViewById(R.id.edtUF);
        edtNumero = (EditText) findViewById(R.id.edtNumero);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtConfirmarSenha = (EditText) findViewById(R.id.edtConfirmarSenha);
        edtComplemento = (EditText) findViewById(R.id.edtComplemento);

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

        BuscarCep busca = new BuscarCep();

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

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    //Cadastrar usuário no servidor
    public void evCadastrarUsuario(View v) throws IOException {

        String DadosServidor = null;


        try {
            DadosServidor = ProcessaSocket.BuscarServidor();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (DadosServidor.equals("erro")) {
            Toast.makeText(this, "Erro na Conexão DNS", Toast.LENGTH_SHORT).show();
        } else {
            if (DadosServidor != null) {
                String retorno2[] = DadosServidor.split("//");
                Ip = retorno2[0];
                Porta = Integer.parseInt(retorno2[1]);
            }
        }


        String segundaRua = edtRua.getText().toString();
        String segundoBairro = edtBairro.getText().toString();
        String segundaCidade = edtCidade.getText().toString();
        convUf = edtUf.getText().toString();

        convRua = removerAcentos(segundaRua);
        convBairro = removerAcentos(segundoBairro);
        convCidade = removerAcentos(segundaCidade);

        //Tirando a mascara dos campos
        convCpf = edtCpf.getText().toString().replaceAll("[^0123456789]", "");
        convTelefone = edtTelefone.getText().toString().replaceAll("[^0123456789]", "");
        convCep = edtCep.getText().toString().replaceAll("[^0123456789]", "");

        //Retirando referencia null
        convEmail = edtEmail.getText().toString();
        convSenha = edtSenha.getText().toString();
        convNumero = edtNumero.getText().toString();
        convNome = edtNome.getText().toString();
        convConfirmarSenha = edtConfirmarSenha.getText().toString();
        convDataNasc = edtDataNasc.getText().toString();
        String segundoComplemento = edtComplemento.getText().toString();
        convComplemento = removerAcentos(segundoComplemento);

        Log.i(TAG, "Cadastrar...." + convCpf);

        if (convCpf.isEmpty()) {
            edtCpf.setError("Faltou preencher cpf ");
            edtCpf.setFocusable(true);
            edtCpf.requestFocus();
        } else {
            if (validarCPF(convCpf)) {
                edtCpf.setError("cpf Inválido");
                edtCpf.setFocusable(true);
                edtCpf.requestFocus();
            } else {
                if (!validarEmail(convEmail)) {
                    edtEmail.setError("Faltou preencher E-mail");
                    edtEmail.setFocusable(true);
                    edtEmail.requestFocus();
                } else {
                    if (convCep.isEmpty()) {
                        edtCep.setError("edtCep Inválido");
                        edtCep.setFocusable(true);
                        edtCep.requestFocus();
                    } else {
                        if (convSenha.isEmpty()) {
                            edtSenha.setError("Faltou preencher a edtSenha ");
                            edtSenha.setFocusable(true);
                            edtSenha.requestFocus();
                        } else {
                            if (convSenha.equals(convConfirmarSenha) == false) {
                                edtConfirmarSenha.setError("A senha digitada nao corresponde");
                                edtConfirmarSenha.setFocusable(true);
                                edtConfirmarSenha.requestFocus();
                            } else {
                                String retorno = processa.cadastrarUsuario(convCpf, convSenha, convEmail, convTelefone, convCep, convUf, convNumero, convRua, convBairro, convCidade, convNome, convDataNasc, convComplemento, Ip, Porta);
                                if (retorno.equals("erro")) {
                                    Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (retorno.equals("true")) {
                                        Toast.makeText(this, "Cadastro feito com sucesso", Toast.LENGTH_SHORT).show();

                                        setContentView(R.layout.activity_login);
                                        this.startActivity(new Intent(this, Login.class));
                                        this.finish();
                                    } else {
                                        edtCpf.setError("cpf Já Cadastrado");
                                        edtCpf.setFocusable(true);
                                        edtCpf.requestFocus();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

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


