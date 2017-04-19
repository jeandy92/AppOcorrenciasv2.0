package appocorrencias.com.appocorrencias;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

private byte[] imagem ;
private String nome,RESULTADO,APELIDO,NOME,SENHA;
    private   EditText usuario;
    private   EditText senha;
    private   Button btnCadastrarCli;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        usuario = (EditText) findViewById(R.id.usuario);
        senha = (EditText) findViewById(R.id.password);
        btnCadastrarCli = (Button) findViewById(R.id.btnCadastrarCli);

        usuario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                usuario.setText("");
            }


        });
        senha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                senha.setText("");
            }


        });

//        Intent intent = getIntent();
//        String name = intent.getStringExtra("my_name");
//        int age = intent.getIntExtra("my_age", 0);
//        byte[] random = intent.getByteArrayExtra("random");



        //Thread para que o aplicativo possa se conectar com o servidor na rede
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public void evCadastrarSe(View view) {

        setContentView(R.layout.activity_cadastrar_usuario);
        this.startActivity(new Intent(this, Cadastrar_Usuario.class));

    }

    public void evEntrar(View view) {

        //Inserindo dados no banco local
        APELIDO = usuario.getText().toString();
        NOME = usuario.getText().toString();
        SENHA = senha.getText().toString();
        ControleDoBanco crud = new ControleDoBanco(getBaseContext());
        RESULTADO = crud.insereDado(APELIDO,NOME,SENHA);

        Toast.makeText(this, RESULTADO, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_adm);
        this.startActivity(new Intent(this, Adm.class));

//        if (usuario.getText().toString().equals("adm") && senha.getText().toString().equals("senha")) {
//            Toast.makeText(getApplicationContext(), "Perfil de ADM", Toast.LENGTH_SHORT).show();
//            setContentView(R.layout.activity_adm);
//            this.startActivity(new Intent(this, Adm.class));
//
//        } else {
//            if (usuario.getText().toString().equals("cliente") && senha.getText().toString().equals("cliente")) {
//            }
//            Toast.makeText(getApplicationContext(), "Perfil Cliente", Toast.LENGTH_SHORT).show();
//            nome = usuario.getText().toString();
//            setContentView(R.layout.activity_cliente);
//            Intent cliente = new Intent(this, Cliente.class);
//
//            Bundle bundle = new Bundle();
//            bundle.putString("nome", nome);
//
//            cliente.putExtras(bundle);
//
//            this.startActivity(cliente);
//
//
//        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}


