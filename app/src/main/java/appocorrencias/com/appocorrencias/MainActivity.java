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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Thread para que o aplicativo possa se conectar com o servidor na rede
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //

    }

public void evCadastrarSe (View view){

    Button btnCadastrarCli = (Button) findViewById(R.id.btnCadastrarCli);


    setContentView(R.layout.activity_cadastrar_usuario);
    this.startActivity(new Intent(this,CadastrarUsuarioActivity.class));

}

    public void evEntrar(View view) {

        EditText usuario = (EditText) findViewById(R.id.usuario);
        EditText senha  =  (EditText) findViewById(R.id.password);

        if(usuario.getText().toString().equals("adm")&& senha.getText().toString().equals("senha"))
        {
            Toast.makeText(getApplicationContext(), "Perfil de ADM", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_adm);
            this.startActivity(new Intent(this,AdmActivity.class));
        }


        else
        {
           if(usuario.getText().toString().equals("cliente")&& senha.getText().toString().equals("cliente")){}
           Toast.makeText(getApplicationContext(), "Perfil Cliente", Toast.LENGTH_SHORT).show();
           setContentView(R.layout.activity_cliente);
            this.startActivity(new Intent(this,ClienteActivity.class));
        }


    }



}


