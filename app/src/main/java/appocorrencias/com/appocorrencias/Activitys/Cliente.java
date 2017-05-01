package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import appocorrencias.com.appocorrencias.ClassesSA.SlidingTabLayout;
import appocorrencias.com.appocorrencias.R;

public class Cliente extends AppCompatActivity  {

    private Toolbar mToolbar;
    private Toolbar mToolbarBottom;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ImageButton cadastrarocorrencia;
    private Toolbar toolbar;
    private FloatingActionButton msOcorrenciasRegistradas;
    static String Nome, CPF, Bairro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        msOcorrenciasRegistradas = (FloatingActionButton) findViewById(R.id.btnOcorrenciasRegistradasPorUsuario);


        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0);
        toolbar.setTitle("AppOcorrencias");
        setSupportActionBar(toolbar);




        //Pegando valores que vem do Login
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Nome = bundle.getString("nome");
        CPF = bundle.getString("cpf");
        Bairro = bundle.getString("bairro");




    }


    public void cadastrarroubo(View v) {


        setContentView(R.layout.activity_cadastrar_ocorrencia);
        Bundle b = new Bundle();
        b.putString("tipocrime", "roubo");

        this.startActivity(new Intent(this, Cadastrar_Ocorrencia.class));

    }


    protected void CriaNotificaçoes() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.fab_plus_icon)
                .setContentTitle("Um novo crime foi registrado próximo ao lugar onde mora")
                .setContentText("Um novo crime registrado");
    }

    public void onUpdateCliente() {

        setContentView(R.layout.activity_cadastrar_usuario);
        this.startActivity(new Intent(this, Cadastrar_Usuario.class));
    }


    // OBS FUNCAO ESTAVA FORA DO CODIGGO JEAN VERIFICAR
    public void evCadastrarOcorrencia(View view) {
        setContentView(R.layout.activity_cadastrar_ocorrencia);

        Intent cadastrarOcorrencia = new Intent(this, Cadastrar_Ocorrencia.class);

        Bundle bundle = new Bundle();
        bundle.putString("cpf", CPF);

        cadastrarOcorrencia.putExtras(bundle);
        this.startActivity(cadastrarOcorrencia);


    }

    public void evOcorrenciasInformadas (View view){
        setContentView(R.layout.activity_listar_ocorrencias);

        Intent ocorrenciasinformadas = new Intent(this, Listar_Ocorrencias.class);
        this.startActivity(ocorrenciasinformadas);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cliente, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_logout:
                deletarSharedPreferences();
                deslogarusuario();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deslogarusuario() {
        deletarSharedPreferences();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void deletarSharedPreferences() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        //SharedPreferences.Editor editor = sharedPreferences.edit();


        SharedPreferences sp1 = getSharedPreferences("MainActivityPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp1.edit();

        editor.putString("login", "");


        Log.i("LOGOUT", sharedPreferences.getString("login",""));
        Log.i("LOGOUT", sharedPreferences.getString("senha",""));

        editor.clear();
        editor.commit();

        Log.i("LOGOUT", sharedPreferences.getString("login",""));
        Log.i("LOGOUT", sharedPreferences.getString("senha",""));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //setContentView(R.layout.activity_login);
        //this.startActivity(new Intent(this,Login.class));
    }

    public void logout(Menu v) {
        deletarSharedPreferences();


        setContentView(R.layout.activity_login);
        this.startActivity(new Intent(this, Login.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }

    public static String getNome(){
        return Nome;
    }

    public static String getCPF(){
        return CPF;
    }

    public static String getBairro(){
        return Bairro;
    }


}

