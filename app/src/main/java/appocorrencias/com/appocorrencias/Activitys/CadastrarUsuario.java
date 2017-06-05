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
    private EditText Nome, CPF, Telefone, Email, Senha, Rua, Bairro, Cidade, Numero, CEP, UF, DataNasc, ConfirmarSenha, Complemento;

    //Variaveis para conversão e  referencia nula
    private String Tela, convCpf, convTelefone, convCep, email, senha, numero, rua, bairro, cidade, uf, nome, dataNasc, confirmarSenha, complemento;

    //Váriaveis para serem utilizadas  no envio do cadastro
    protected String cadastro1, cadastroNome, cadastroRua, cadastroBairro, cadastroCidade;

    //Dados para o envio do socket.
    ProcessaSocket processa = new ProcessaSocket();
    boolean retorno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Tela = bundle.getString("tela");


        //Button cadastrar
        btnCadastarUsuario = (Button) findViewById(R.id.CadastrarUsuario);

        //Váriaveis para o cadastro
        Nome = (EditText) findViewById(R.id.edtNome);
        CPF = (EditText) findViewById(R.id.edtCPF);
        Senha = (EditText) findViewById(R.id.edtSenha);
        DataNasc = (EditText) findViewById(R.id.edtDataNasc);
        Rua = (EditText) findViewById(R.id.edtRua);
        Telefone = (EditText) findViewById(R.id.edtTelefone);
        CEP = (EditText) findViewById(R.id.edtCep);
        Bairro = (EditText) findViewById(R.id.edtBairro);
        Cidade = (EditText) findViewById(R.id.edtCidade);
        UF = (EditText) findViewById(R.id.edtUF);
        Numero = (EditText) findViewById(R.id.edtNumero);
        Email = (EditText) findViewById(R.id.edtEmail);
        ConfirmarSenha = (EditText) findViewById(R.id.edtConfirmarSenha);
        Complemento = (EditText) findViewById(R.id.edtComplemento);

        // Inserindo Mascaras.
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", CPF);
        MaskEditTextChangedListener maskTelefone = new MaskEditTextChangedListener("(##)#####-####", Telefone);
        MaskEditTextChangedListener maskCEP = new MaskEditTextChangedListener("#####-###", CEP);
        MaskEditTextChangedListener maskData = new MaskEditTextChangedListener("##/##/####", DataNasc);

        DataNasc.addTextChangedListener(maskData);
        CPF.addTextChangedListener(maskCPF);
        Telefone.addTextChangedListener(maskTelefone);
        CEP.addTextChangedListener(maskCEP);
    }


    // Método para buscar Cep
    public void evBuscarCep(View v) throws IOException {

        convCep = CEP.getText().toString().replaceAll("[^0123456789]", "");

        BuscarCep busca = new BuscarCep();

        String Status = busca.getEndereco(convCep);
        if (Status.equals("erro")) {
            Toast.makeText(this, "Erro na Conexão", Toast.LENGTH_SHORT).show();
        } else {
            Rua.setText(busca.getEndereco(convCep));
            Bairro.setText(busca.getBairro(convCep));
            Cidade.setText(busca.getCidade(convCep));
            UF.setText(busca.getUF(convCep));


            String rua2 = Rua.getText().toString();
            String bairro2 = Bairro.getText().toString();
            String cidade2 = Cidade.getText().toString();
            uf = UF.getText().toString();

            rua = removerAcentos(rua2);
            bairro = removerAcentos(bairro2);
            cidade = removerAcentos(cidade2);


        }
    }

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    //Cadastrar usuário no servidor
    public void evCadastrarUsuario(View v) throws IOException {

        //Tirando a mascara dos campos
        convCpf = CPF.getText().toString().replaceAll("[^0123456789]", "");
        convTelefone = Telefone.getText().toString().replaceAll("[^0123456789]", "");
        convCep = CEP.getText().toString().replaceAll("[^0123456789]", "");

        //Retirando referencia null
        email = Email.getText().toString();
        senha = Senha.getText().toString();
        numero = Numero.getText().toString();
        nome = Nome.getText().toString();
        confirmarSenha = ConfirmarSenha.getText().toString();
        dataNasc = DataNasc.getText().toString();
        String complemento2 = Complemento.getText().toString();
        complemento = removerAcentos(complemento2);

        Log.i(TAG, "Cadastrar...." + convCpf);

        if (convCpf.isEmpty()) {
            CPF.setError("Faltou preencher Cpf ");
            CPF.setFocusable(true);
            CPF.requestFocus();
        } else {
            if (validarCPF(convCpf)) {
                CPF.setError("Cpf Inválido");
                CPF.setFocusable(true);
                CPF.requestFocus();
            } else {
                if (!validarEmail(email)) {
                    Email.setError("Faltou preencher E-mail");
                    Email.setFocusable(true);
                    Email.requestFocus();
                } else {
                    if (convCep.isEmpty()) {
                        CEP.setError("CEP Inválido");
                        CEP.setFocusable(true);
                        CEP.requestFocus();
                    } else {
                        if (senha.isEmpty()) {
                            Senha.setError("Faltou preencher a Senha ");
                            Senha.setFocusable(true);
                            Senha.requestFocus();
                        } else {
                            if (senha.equals(confirmarSenha) == false) {
                                ConfirmarSenha.setError("A senha digitada nao corresponde");
                                ConfirmarSenha.setFocusable(true);
                                ConfirmarSenha.requestFocus();
                            } else {
                                String retorno = processa.cadastrarUsuario(convCpf, senha, email, convTelefone, convCep, uf, numero, rua, bairro, cidade, nome, dataNasc, complemento);
                                if (retorno.equals("erro")) {
                                    Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (retorno.equals("true")) {
                                        Toast.makeText(this, "Cadastro feito com sucesso", Toast.LENGTH_SHORT).show();

                                        setContentView(R.layout.activity_login);
                                        this.startActivity(new Intent(this, Login.class));
                                    } else {
                                        CPF.setError("Cpf Já Cadastrado");
                                        CPF.setFocusable(true);
                                        CPF.requestFocus();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //Método para Validar Cpf

    public static boolean validarCPF(String CPF) {
        if (CPF.equals("00000000000") || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")) {
            return true;
        }
        Log.i(TAG, "Validando...." + CPF);
        char dig10, dig11;
        int sm, i, r, num, peso;
        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (CPF.charAt(i) - 48);
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
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (false);
            else
                return (true);
        } catch (Exception erro) {
            return (true);
        }
    }

    //Método para validar Email
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


        if (Tela.equals("Adm")) {
            Intent adm = new Intent(this, Adm.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", "Administrador");
            bundle.putString("cpf", "33333333333");
            bundle.putString("bairro", "Adm");

            adm.putExtras(bundle);
            this.startActivity(adm);
        } else {

            this.startActivity(new Intent(this, Login.class));
        }
    }
}


