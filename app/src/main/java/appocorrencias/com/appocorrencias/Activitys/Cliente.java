package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Adapters.FeedAdapter;
import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.ListView.Feed_Ocorrencias;
import appocorrencias.com.appocorrencias.ListView.Item_Feed_Ocorrencias;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.ListView.Feed_Ocorrencias.criarfeedocorrencias;

public class Cliente extends AppCompatActivity  {

    private Toolbar mToolbar;
    private Toolbar mToolbarBottom;
    private ViewPager viewPager;
    private ImageButton cadastrarocorrencia;
    private Toolbar toolbar;
    private ListView lvFeedOcorrencias;
    private TextView tvnomecompleto;
    private RecyclerView rvfeedocorrencias;
    private FloatingActionButton btnOcorrenciasRegistradas,btnCadastrarOcorrencias,btnBuscarOcorrencias;
    static String Nome, CPF, Bairro;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ProcessaSocket processa = new ProcessaSocket();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        btnOcorrenciasRegistradas = (FloatingActionButton) findViewById(R.id.btnOcorrenciasRegistradasPorUsuario);
        btnCadastrarOcorrencias = (FloatingActionButton) findViewById(R.id.btnCadastrarOcorrencias);
        btnBuscarOcorrencias   = (FloatingActionButton) findViewById(R.id.btnCadastrarOcorrencias);
        lvFeedOcorrencias =  (ListView) findViewById(R.id.lv_feed_de_ocorrencias);
        tvnomecompleto =  (TextView) findViewById(R.id.tv_nome_completo);

        ArrayList<Feed_Ocorrencias> listafeedocorrencias = criarfeedocorrencias();

        FeedAdapter adapter = new FeedAdapter(this, listafeedocorrencias);

        lvFeedOcorrencias.setAdapter(adapter);

        lvFeedOcorrencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0){

                    Intent i = new Intent(view.getContext(), Item_Feed_Ocorrencias.class);
                    String idocorrencia = ((TextView) view.findViewById(R.id.txt_id_ocorrencia)).getText().toString();
                    String descocorrencia = ((TextView) view.findViewById(R.id.txt_desc_ocorrencia)).getText().toString();
                    String tipocrime = ((TextView) view.findViewById(R.id.txt_tipo_crime)).getText().toString();


                    i.putExtra("id_ocorrencia", idocorrencia);
                    i.putExtra("desc_ocorrencia", descocorrencia);
                    i.putExtra("tipocrime", tipocrime);

                    startActivity(i);

                    Toast.makeText(view.getContext(), " case 1 Exibir Sobre", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnBuscarOcorrencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evBuscarOcorrencias(v);

            }
        });
        btnOcorrenciasRegistradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    evOcorrenciasInformadas(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        btnCadastrarOcorrencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evCadastrarOcorrencia(v);
            }
        });


        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0);
        toolbar.setTitle("AppOcorrencias");
        setSupportActionBar(toolbar);


        if (Nome == null && CPF == null && Bairro == null) {
            //Pegando valores que vem do Login
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            Nome = bundle.getString("nome");
            CPF = bundle.getString("cpf");
            Bairro = bundle.getString("bairro");
        }
        ///Setta o nome no BEM VINDO
        tvnomecompleto.setText(Nome);




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


    public void evBuscarOcorrencias(View v){



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

    public void evOcorrenciasInformadas (View view) throws IOException {

        String BuscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradas" + " " + CPF;
        Toast.makeText(this, "Minhas Ocorrencias Registradas ", Toast.LENGTH_SHORT).show();
        String retorno = processa.cadastrar1_no_server(BuscarOcorrenciasRegistradas);




        setContentView(R.layout.activity_listar_ocorrencias);
        Intent cliente = new Intent(this, Cliente.class);



        Bundle bundle = new Bundle();
        bundle.putString("cpf", CPF);

        cliente.putExtras(bundle);
        this.startActivity(cliente);


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

