package appocorrencias.com.appocorrencias;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class Cadastrar_Usuario extends AppCompatActivity {

    //Variavel para gerar log
    private static final String TAG = "LOG";

    //Variaveis globais
    private Button btnCadastarUsuario;
    private Button btnBuscar;
    private EditText Nome,CPF,Telefone,Email,Senha,Rua, Bairro, Cidade, Numero, CEP, UF;

    //Variaveis para conversão e  referencia nula
    private String  convCpf,convTelefone,convCep,email;

    //Váriaveis para serem utilizadas  no envio do cadastro
    protected String cadastro1,cadastroNome,cadastroRua,cadastroBairro,cadastroCidade;

    //Dados para o envio do socket.
    ProcessaSocket processa =  new ProcessaSocket();
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


        // Inserindo Mascaras.
        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", CPF);
        MaskEditTextChangedListener maskTelefone = new MaskEditTextChangedListener("(##)####-####", Telefone);
        MaskEditTextChangedListener maskCEP = new MaskEditTextChangedListener("#####-###", CEP);



        CPF.addTextChangedListener(maskCPF);
        Telefone.addTextChangedListener(maskTelefone);
        CEP.addTextChangedListener(maskCEP);





    }


    // Método para buscar Cep
    public void evBuscarCep(View v) throws IOException {
        Buscar_Cep busca = new Buscar_Cep();
        Rua.setText(busca.getEndereco(CEP.getText().toString()));
        Bairro.setText(busca.getBairro(CEP.getText().toString()));
        Cidade.setText(busca.getCidade(CEP.getText().toString()));
        UF.setText(busca.getUF(CEP.getText().toString()));
    }

    //Cadastrar usuário no servidor
    public void evCadastrarUsuario(View v){

        //Tirando a mascara dos campos
         convCpf = CPF.getText().toString().replaceAll("[^0123456789]", "");
         convTelefone = Telefone.getText().toString().replaceAll("[^0123456789]", "");
         convCep = CEP.getText().toString().replaceAll("[^0123456789]", "");

        //Retirando referencia null
        email = Email.getText().toString();


        Log.i(TAG,  "Cadastrar...."+convCpf);


        if (convCpf.isEmpty()) {
            CPF.setError("Faltou preencher CPF ");
            CPF.setFocusable(true);
            CPF.requestFocus();
        } else {
            if (validarCPF(convCpf)) {
                CPF.setError("CPF Inválido");
                CPF.setFocusable(true);
                CPF.requestFocus();
            } else {
                if (!validarEmail(email)) {
                    Email.setError("Faltou preencher E-mail");
                    Email.setFocusable(true);
                    Email.requestFocus();
                } else {
                    if (convCep.isEmpty()) {
                        CEP.setError("CPF Inválido");
                        CEP.setFocusable(true);
                        CEP.requestFocus();
                    } else {


                        cadastro1 = "Cadastrar1" + " " + CPF.getText().toString() + " " + Senha.getText().toString() +
                                " " + Email.getText().toString() + " " + Telefone.getText().toString() + " " + CEP.getText().toString() +
                                " " + UF.getText().toString() + " " + Numero.getText().toString();
                        cadastroNome = "CadastrarNome" + " " + CPF.getText().toString() + " " + Nome.getText().toString();
                        cadastroRua = "CadastrarRua" + " " + CPF.getText().toString() + " " + Rua.getText().toString();
                        cadastroBairro = "CadastrarBairro" + " " + CPF.getText().toString() + " " + Bairro.getText().toString();
                        cadastroCidade = "CadastrarCidade" + " " + CPF.getText().toString() + " " + Cidade.getText().toString();

                        processa.cadastrar_no_server(cadastro1);
                        //
//            //Creceber resposta do server sobre CPF
//
//            processa.cadastrar_no_server(cadastroNome);
//            processa.cadastrar_no_server(cadastroRua);
//            processa.cadastrar_no_server(cadastroBairro);
//            processa.cadastrar_no_server(cadastroCidade);


                        setContentView(R.layout.activity_main);
                        this.startActivity(new Intent(this, MainActivity.class));
                    }
                }
            }
        }
    }

    //Método para Validar CPF
    public static boolean validarCPF(String CPF) {
        if (CPF.equals("00000000000") || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")) {
            return true;
        }
        Log.i(TAG,  "Validando...."+CPF);
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
        setContentView(R.layout.activity_main);
        this.startActivity(new Intent(this,MainActivity.class));
    }
}


