package appocorrencias.com.appocorrencias;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class Cadastrar_Usuario extends AppCompatActivity {

    //Variaveis globais
    Button btnCadastarUsuario;
    Button btnBuscar;
    EditText Nome,CPF,Telefone,Email,Senha,Rua, Bairro, Cidade, Numero, CEP, UF;


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
        MaskEditTextChangedListener maskTelefone = new MaskEditTextChangedListener("##)####-####", Telefone);
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

        if (CPF.getText().toString().isEmpty()) {
            CPF.setError("Faltou preencher CPF ");
            CPF.setFocusable(true);
            CPF.requestFocus();
        } else {
            if (validateCPF(CPF.getText().toString()) == false) {
                CPF.setError("CPF INVÁLIDO ");
                CPF.setFocusable(true);
                CPF.requestFocus();
            } else {
                if (!validateEmail(Email.getText().toString())) {
                    Email.setError("Email inválido");
                    Email.setFocusable(true);
                    Email.requestFocus();
                } else {
                    if (Email.getText().toString().isEmpty()) {
                        Email.setError("Falta preencher campo e-mail");
                        Email.setFocusable(true);
                        Email.requestFocus();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Cadastrado feito com sucesso " + Nome.getText(), Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.activity_main);
                        this.startActivity(new Intent(this, MainActivity.class));

                    }


                }
            }
        }






                String cadastro1 = "Cadastrar1" + " " + CPF.getText().toString() + " " + Senha.getText().toString() +
                        " " + Email.getText().toString() + " " + Telefone.getText().toString()+ " " + CEP.getText().toString()+
                        " " + UF.getText().toString()+ " " + Numero.getText().toString();
                String cadastroNome = "CadastrarNome" + " " + CPF.getText().toString()+ " " + Nome.getText().toString();
                String cadastroRua = "CadastrarRua" + " " + CPF.getText().toString()+ " " + Rua.getText().toString();
                String cadastroBairro = "CadastrarBairro" + " " + CPF.getText().toString()+ " " + Bairro.getText().toString();
                String cadastroCidade = "CadastrarCidade" + " " + CPF.getText().toString()+ " " + Cidade.getText().toString();






//            cadastrar_no_server(cadastro1);
//
//            //Creceber resposta do server sobre CPF
//
//            cadastrar_no_server(cadastroNome);
//            cadastrar_no_server(cadastroRua);
//            cadastrar_no_server(cadastroBairro);
//            cadastrar_no_server(cadastroCidade);



            }



    private void cadastrar_no_server(String dados) {
        try {
            Socket socket = null;

            OutputStream canalSaida = null;
            ObjectInputStream canalEntrada = null;

            socket = new Socket("172.20.10.3",2222);

            canalSaida = socket.getOutputStream();
            canalSaida.write(dados.getBytes());


            // canalEntrada = new ObjectInputStream(socket.getInputStream());
            // Object object = canalEntrada.readObject();
            // if ((object != null) && (object instanceof String)) {
            //   txvRetornoSocket.setText(object.toString());
            // }


        } catch (Exception e) {
            //FIXME Tratar a Exception.
            e.printStackTrace();
        }
    }


    //Método para Validar CPF
    public static boolean validateCPF(String CPF) {
    if (CPF.equals("00000000000") || CPF.equals("11111111111")
            || CPF.equals("22222222222") || CPF.equals("33333333333")
            || CPF.equals("44444444444") || CPF.equals("55555555555")
            || CPF.equals("66666666666") || CPF.equals("77777777777")
            || CPF.equals("88888888888") || CPF.equals("99999999999")) {
        return false;
    }
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
            return (true);
        else
            return (false);
    } catch (Exception erro) {
        return (false);
    }
}

    public final static boolean validateEmail(String txtEmail) {
        if (TextUtils.isEmpty(txtEmail)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches();
        }
    }
}


