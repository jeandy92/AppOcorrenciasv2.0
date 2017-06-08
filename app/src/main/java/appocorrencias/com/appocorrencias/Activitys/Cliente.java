package appocorrencias.com.appocorrencias.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import appocorrencias.com.appocorrencias.Adapters.AdapterFeed;
import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.ListView.ArrayImagens;
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfil;
import appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas;
import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
import appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias;
import appocorrencias.com.appocorrencias.R;
import me.drakeet.materialdialog.MaterialDialog;

import static appocorrencias.com.appocorrencias.Activitys.CadastrarOcorrencia.processasocket;
import static appocorrencias.com.appocorrencias.ListView.ArrayComentariosRegistrados.deleteAllArrayComentarios;
import static appocorrencias.com.appocorrencias.ListView.ArrayImagens.getImagens;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.deleteAllArray;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getListaOcorrencia;
import static appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias.evBuscarComentario;
import static appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias.evBuscarImagens;


public class Cliente extends AppCompatActivity {
    //Cloud Menssagem Cliente(GCM)

    private Toolbar toolbar;
    private ListView lvFeedOcorrencias;
    private TextView tvnomecompleto;
    private FloatingActionButton btnOcorrenciasRegistradas, btnCadastrarOcorrencias, btnBuscarOcorrencias;
    public static String Nome, CPF, Bairro;

    ProcessaSocket processa = new ProcessaSocket();

    private static final int REQUEST_PERMISSIONS_CODE = 128;

    private static final String TAG = "LOG";
    //Váriaveis para realizar o controle do ResultActivity
    public static final int IMAGEM_INTERNA = 12;
    private ImageView ivCliente;

    byte[] byteImagem = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        //Pegando valores que vem do Login  - TEM Q MANTER DESSA FORMA SE NAO QUANDO LOGAR COM OUTRO USUARIO O Cpf MANTEM O MESMO
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Nome = bundle.getString("nome");
        CPF = bundle.getString("cpf");
        Bairro = bundle.getString("bairro");

        btnOcorrenciasRegistradas = (FloatingActionButton) findViewById(R.id.btnOcorrenciasRegistradasPorUsuario);
        btnCadastrarOcorrencias = (FloatingActionButton) findViewById(R.id.btnCadastrarOcorrencias);
        btnBuscarOcorrencias = (FloatingActionButton) findViewById(R.id.btnBuscarOcorrencias);
        lvFeedOcorrencias = (ListView) findViewById(R.id.lv_feed_de_ocorrencias);
        tvnomecompleto = (TextView) findViewById(R.id.tv_nome_completo);
        ivCliente = (ImageView) findViewById(R.id.ivCliente);

        ArrayList<Bitmap> listaImagens = ArrayImagensPerfil.getImagens();
        Bitmap[] images = new Bitmap[listaImagens.size()];

        if(listaImagens.size() > 0){
            images[0] = listaImagens.get(0);
            ivCliente.setImageBitmap(images[0]);
        }


        ArrayList<DadosOcorrencias> listafeedocorrencias = getListaOcorrencia();
        AdapterFeed adapter = new AdapterFeed(this, listafeedocorrencias);

        lvFeedOcorrencias.setAdapter(adapter);

        lvFeedOcorrencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {

                    Intent i = new Intent(view.getContext(), ItemFeedOcorrencias.class);
                    String idocorrencia = ((TextView) view.findViewById(R.id.txt_id_ocorrencia)).getText().toString();
                    String descocorrencia = ((TextView) view.findViewById(R.id.txt_desc_comentario)).getText().toString();
                    String tipocrime = ((TextView) view.findViewById(R.id.tv_bairro)).getText().toString();

                    String tela = "Cliente";
                    i.putExtra("cpf", CPF);
                    i.putExtra("nome", Nome);
                    i.putExtra("bairro", Bairro);
                    i.putExtra("id_ocorrencia", idocorrencia);
                    i.putExtra("desc_ocorrencia", descocorrencia);
                    i.putExtra("tipocrime", tipocrime);
                    i.putExtra("tela", tela);

                    deleteAllArrayComentarios();

                    try {
                        evBuscarImagens(idocorrencia,"ocorrencia");
                        evBuscarComentario(idocorrencia);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    startActivity(i);

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


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0);
        toolbar.setTitle("AppOcorrencias");
        setSupportActionBar(toolbar);


        ///Setta o nome no BEM VINDO
        tvnomecompleto.setText(Nome);

        lvFeedOcorrencias.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

    }


    public void cadastrarroubo(View v) {


        setContentView(R.layout.activity_cadastrar_ocorrencia);
        Bundle b = new Bundle();
        b.putString("tipocrime", "roubo");

        this.startActivity(new Intent(this, CadastrarOcorrencia.class));

    }


    protected void CriaNotificaçoes() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.fab_plus_icon)
                .setContentTitle("Um novo crime foi registrado próximo ao lugar onde mora")
                .setContentText("Um novo crime registrado");
    }

    public void onUpdateCliente() {

        setContentView(R.layout.activity_cadastrar_usuario);
        this.startActivity(new Intent(this, CadastrarUsuario.class));
    }


    public void evBuscarOcorrencias(View v) {

        Intent cliente = new Intent(this, BuscarOcorrencias.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome", Nome);
        bundle.putString("cpf", CPF);
        bundle.putString("bairro", Bairro);
        bundle.putString("tela", "Cliente");
        cliente.putExtras(bundle);
        this.startActivity(cliente);

    }

    // OBS FUNCAO ESTAVA FORA DO CODIGGO JEAN VERIFICAR
    public void evCadastrarOcorrencia(View view) {
        deleteAllArray();

        Intent cadastrarOcorrencia = new Intent(this, CadastrarOcorrencia.class);

        Bundle bundle = new Bundle();
        bundle.putString("nome", Nome);
        bundle.putString("cpf", CPF);
        bundle.putString("bairro", Bairro);
        bundle.putString("tela", "Cliente");

        cadastrarOcorrencia.putExtras(bundle);
        this.startActivity(cadastrarOcorrencia);

    }

    public void evOcorrenciasInformadas(View view) throws IOException {

        deleteAllArray();

        String BuscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradas" + " " + CPF;


        Toast.makeText(this, "Minhas Ocorrencias Registradas ", Toast.LENGTH_SHORT).show();

        String retorno = processa.cadastrar1_no_server(BuscarOcorrenciasRegistradas);

        if (retorno.equals("false")) {

            Toast.makeText(this, "Não há ocorrencias cadastradas", Toast.LENGTH_SHORT).show();

            Intent cliente = new Intent(this, ListarOcorrencias.class);

            Bundle bundle = new Bundle();
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
                String Apelido = OcorrenciaUm[11];

                DadosOcorrencias dado = new DadosOcorrencias(Nr, CPFOco, Rua, Bairro, Cidade, UF, Descricao, Data, Tipo, Anonimo, Apelido);

                ArrayOcorrenciasRegistradas.adicionar(dado);
            }

            Toast.makeText(this, "Mostrando suas Ocorrencias ", Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_listar_ocorrencias);
            Intent cliente = new Intent(this, ListarOcorrencias.class);

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


        Log.i("LOGOUT", sharedPreferences.getString("login", ""));
        Log.i("LOGOUT", sharedPreferences.getString("senha", ""));

        editor.clear();
        editor.commit();

        Log.i("LOGOUT", sharedPreferences.getString("login", ""));
        Log.i("LOGOUT", sharedPreferences.getString("senha", ""));
    }

    public void logout(Menu v) {
        deletarSharedPreferences();

        this.startActivity(new Intent(this, Login.class));
    }

    public static String getNome() {
        return Nome;
    }

    public static String getCPF() {
        return CPF;
    }

    public static String getBairro() {
        return Bairro;
    }


    // FOTO DO PERFIL/////////

    private void dialogo(String message, final String[] permissions) {

        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle("Permission");
        mMaterialDialog.setMessage(message);
        mMaterialDialog.setPositiveButton("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(Cliente.this, permissions, REQUEST_PERMISSIONS_CODE);
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.setNegativeButton("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });
        mMaterialDialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i(TAG, "Permissão");
        switch (requestCode) {
            case REQUEST_PERMISSIONS_CODE:
                for (int i = 0; i < permissions.length; i++) {

                    if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                    } else if (permissions[i].equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                    } else if (permissions[i].equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                    } else if (permissions[i].equalsIgnoreCase(Manifest.permission.CAMERA)
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                    }
                }
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public void entrar_galeria(View v) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                dialogo("É preciso a permission READ_EXTERNAL_STORAGE para acessar sua Galeria.", new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_CODE);
            }
        } else {
            abrir_galeria();
        }

    }

    private void abrir_galeria() {

        Toast.makeText(getApplicationContext(), "Abrindo galeria", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGEM_INTERNA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == IMAGEM_INTERNA && resultCode == RESULT_OK) {
            Uri imagemSelecionada = intent.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imagemSelecionada));
                ivCliente.setImageBitmap(bitmap);
                ivCliente.setBackground(null);
                toByte1(bitmap);
            } catch (Exception err) {
                Log.d("Imag", err.getMessage());
            }
        }
    }


    //// Bitmap em bytes

    public void toByte1(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        byteImagem = byteArray;
        String retorno = enviarImgPerfil();
        if(retorno.equals("erro")){
            Toast.makeText(getApplicationContext(), "Erro de Conexão", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Imagem do Perfil Atualizada", Toast.LENGTH_SHORT).show();
        }
    }

    public String enviarImgPerfil() throws IOException {

        String retornoImg = processasocket.envia_Img_Perfil(CPF, "Img1", byteImagem);

        if (retornoImg.equals("erro")){
            return "erro";
        }
        return "true";
    }



        @Override
    public void onBackPressed() {
        super.onBackPressed();
        ArrayImagens.deleteBitmap();
        deleteAllArray();
        finish();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("Cliente", "onRestart");


    }
}

