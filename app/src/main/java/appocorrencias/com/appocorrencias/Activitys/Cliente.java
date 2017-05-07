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
import appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas;
import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
import appocorrencias.com.appocorrencias.ListView.Feed_Ocorrencias;
import appocorrencias.com.appocorrencias.ListView.Item_Feed_Ocorrencias;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.deleteAllArray;
import static appocorrencias.com.appocorrencias.ListView.Feed_Ocorrencias.criarfeedocorrencias;

public class Cliente extends AppCompatActivity  {
    //Cloud Menssagem Cliente(GCM)


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





        //Pegando valores que vem do Login  - TEM Q MANTER DESSA FORMA SE NAO QUANDO LOGAR COM OUTRO USUARIO O CPF MANTEM O MESMO
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            Nome = bundle.getString("nome");
            CPF = bundle.getString("cpf");
            Bairro = bundle.getString("bairro");


        btnOcorrenciasRegistradas = (FloatingActionButton) findViewById(R.id.btnOcorrenciasRegistradasPorUsuario);
        btnCadastrarOcorrencias = (FloatingActionButton) findViewById(R.id.btnCadastrarOcorrencias);
        btnBuscarOcorrencias   = (FloatingActionButton) findViewById(R.id.btnBuscarOcorrencias);
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

        if (retorno.equals("false")) {

            Toast.makeText(this, "Não há ocorrencias cadastradas", Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_listar_ocorrencias);
            Intent cliente = new Intent(this, Listar_Ocorrencias.class);

            Bundle bundle = new Bundle();
            bundle.putString("numerosOcorrencias", "false");
            bundle.putString("qtdOcorrencias", "false");
            bundle.putString("nome", Nome);
            bundle.putString("cpf", CPF);
            bundle.putString("bairro", Bairro);

            cliente.putExtras(bundle);
            this.startActivity(cliente);


        } else {
            // Pegando quantidade de Ocorrencias

            int qtdOcorrencia = ArrayOcorrenciasRegistradas.getQuantidadeOcorrencia(retorno);

            // Pegando dados e Adicioanando dados no Array

            for (int i = 0; i < qtdOcorrencia; i++) {
                String TodasOcorrencias[] = retorno.split("///");

                String Ocorrencia = TodasOcorrencias[i];
                String OcorrenciaUm[] = Ocorrencia.split("//");
                String Nr = OcorrenciaUm[1];
                String CPFOco = OcorrenciaUm[2];
                String Rua = OcorrenciaUm[3];
                String Bairro = OcorrenciaUm[4];
                String Cidade = OcorrenciaUm[5];
                String UF = OcorrenciaUm[6];
                String Descricao = OcorrenciaUm[7];
                String Data = OcorrenciaUm[8];
                String Tipo = OcorrenciaUm[9];
                String Anonimo = OcorrenciaUm[10];

                DadosOcorrencias dado = new DadosOcorrencias(Nr, CPFOco, Rua, Bairro, Cidade, UF, Descricao, Data, Tipo, Anonimo);

                ArrayOcorrenciasRegistradas.adicionar(dado);
            }

            Toast.makeText(this, "Mostrando suas Ocorrencias ", Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_listar_ocorrencias);
            Intent cliente = new Intent(this, Listar_Ocorrencias.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", Nome);
            bundle.putString("cpf", CPF);
            bundle.putString("bairro", Bairro);

            cliente.putExtras(bundle);
            this.startActivity(cliente);
        }
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
        deleteAllArray();
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

    public void logout(Menu v) {
        deletarSharedPreferences();


        setContentView(R.layout.activity_login);
        this.startActivity(new Intent(this, Login.class));
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("Cliente", "onRestart");


    }
}

