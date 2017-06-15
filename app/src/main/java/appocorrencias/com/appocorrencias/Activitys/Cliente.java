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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Adapters.AdapterFeed;
import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.ListView.ArrayImagens;
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfil;
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfilComentarios;
import appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas;
import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
import appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias;
import appocorrencias.com.appocorrencias.R;
import me.drakeet.materialdialog.MaterialDialog;

import static appocorrencias.com.appocorrencias.Activitys.CadastrarOcorrencia.processaSocket;
import static appocorrencias.com.appocorrencias.ListView.ArrayComentariosRegistrados.deleteAllArrayComentarios;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.deleteAllArray;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getListaOcorrencia;
import static appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias.evBuscarComentario;
import static appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias.evBuscarImagens;


public class Cliente extends AppCompatActivity {


    //Variaveis para activitys
    private Toolbar toolbar;
    private ListView lvFeedOcorrencias;
    private TextView tvNomeCompleto;
    private FloatingActionButton btnOcorrenciasRegistradas, btnCadastrarOcorrencias, btnBuscarOcorrencias;

    //Variaveis Locais
    public static String clienteNome, clienteCpf, clienteBairro;

    //Obejtos instanciados na classe
    public ProcessaSocket processa = new ProcessaSocket();

    //Váriaveis para realizar o controle do ResultActivity
     public static final int IMAGEM_INTERNA = 12;
    private static final int REQUEST_PERMISSIONS_CODE = 128;
    private static final String TAG = "LOG";


    //Variaveis para criar a primeiraImagem
    private ImageView ivCliente;
    private byte[] byteImagem = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        //Pegando valores que vem do Login  - TEM Q MANTER DESSA FORMA SE NAO QUANDO LOGAR COM OUTRO USUARIO O cpf MANTEM O MESMO
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        clienteNome = bundle.getString("nome");
        clienteCpf = bundle.getString("cpf");
        clienteBairro = bundle.getString("bairro");

        btnOcorrenciasRegistradas = (FloatingActionButton) findViewById(R.id.btnOcorrenciasRegistradasPorUsuario);
        btnCadastrarOcorrencias   = (FloatingActionButton) findViewById(R.id.btnCadastrarOcorrencias);
        btnBuscarOcorrencias      = (FloatingActionButton) findViewById(R.id.btnBuscarOcorrencias);
        lvFeedOcorrencias         = (ListView)             findViewById(R.id.lv_feed_de_ocorrencias);
        tvNomeCompleto            = (TextView)             findViewById(R.id.tv_nome_completo);
        ivCliente                 = (ImageView)            findViewById(R.id.ivCliente);

        ArrayList<Bitmap> listaImagens = ArrayImagensPerfil.getImagens();
        Bitmap[] images = new Bitmap[listaImagens.size()];

        if (listaImagens.size() > 0) {
            images[0] = listaImagens.get(0);
            ivCliente.setImageBitmap(images[0]);
        }


        ArrayList<DadosOcorrencias> listaFeedOcorrencias = getListaOcorrencia();
        AdapterFeed adapter = new AdapterFeed(this, listaFeedOcorrencias);

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
                    i.putExtra("cpf", clienteCpf);
                    i.putExtra("nome", clienteNome);
                    i.putExtra("bairro", clienteBairro);
                    i.putExtra("id_ocorrencia", idocorrencia);
                    i.putExtra("desc_ocorrencia", descocorrencia);
                    i.putExtra("tipocrime", tipocrime);
                    i.putExtra("tela", tela);

                    deleteAllArrayComentarios();

                    String retornoImagem = null;
                    try {
                        retornoImagem = evBuscarImagens(idocorrencia, "ocorrencia");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (retornoImagem.equals("true") || retornoImagem.equals("false")) {
                        String retornoComent = null;
                        try {
                            retornoComent = evBuscarComentario(idocorrencia);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (retornoComent.equals("true") || retornoComent.equals("false")) {
                            startActivity(i);
                        }
                    }
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
        tvNomeCompleto.setText(clienteNome);

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


    public void evBuscarOcorrencias(View v) {

        Intent cliente = new Intent(this, BuscarOcorrencias.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome", clienteNome);
        bundle.putString("cpf", clienteCpf);
        bundle.putString("bairro", clienteBairro);
        bundle.putString("tela", "Cliente");
        cliente.putExtras(bundle);
        this.startActivity(cliente);
        this.finish();
    }

    // OBS FUNCAO ESTAVA FORA DO CODIGGO JEAN VERIFICAR
    public void evCadastrarOcorrencia(View view) {
        deleteAllArray();

        Intent cadastrarOcorrencia = new Intent(this, CadastrarOcorrencia.class);

        Bundle bundle = new Bundle();
        bundle.putString("nome", clienteNome);
        bundle.putString("cpf", clienteCpf);
        bundle.putString("bairro", clienteBairro);
        bundle.putString("tela", "Cliente");

        cadastrarOcorrencia.putExtras(bundle);
        this.startActivity(cadastrarOcorrencia);
        this.finish();
    }

    public void evOcorrenciasInformadas(View view) throws IOException {

        deleteAllArray();

        String buscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradas" + " " + clienteCpf;


        Toast.makeText(this, "Minhas Ocorrencias Registradas ", Toast.LENGTH_SHORT).show();

        ArrayImagensPerfilComentarios.deleteBitmap();

        String retorno = ProcessaSocket.buscarDadosImagensServer(buscarOcorrenciasRegistradas);

        if (retorno.equals("false")) {

            Toast.makeText(this, "Não há ocorrencias cadastradas", Toast.LENGTH_SHORT).show();

            Intent cliente = new Intent(this, ListarOcorrencias.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", clienteNome);
            bundle.putString("cpf", clienteCpf);
            bundle.putString("bairro", clienteBairro);

            cliente.putExtras(bundle);
            this.startActivity(cliente);
            this.finish();
        } else {
            // Pegando quantidade de Ocorrencias

            int qtdOcorrencia = ArrayOcorrenciasRegistradas.getQuantidadeOcorrencia(retorno);

            // Pegando dados e Adicioanando dados no Array

            for (int i = 0; i < qtdOcorrencia; i++) {

                String todasOcorrencias[] = retorno.split("///");
                String ocorrencia           = todasOcorrencias[i];
                String primeiraOcorrencia[] = ocorrencia.split("//");
                String numeroOcorrencia     = primeiraOcorrencia[1];
                String focoCpf              = primeiraOcorrencia[2];
                String ruaOcorrencia        = primeiraOcorrencia[3];
                String bairroOcorrencia     = primeiraOcorrencia[4];
                String cidadeOcorrencia     = primeiraOcorrencia[5];
                String ufOcorrencia         = primeiraOcorrencia[6];
                String descricaoOcorrencia  = primeiraOcorrencia[7];
                String dataOcorrencia       = primeiraOcorrencia[8];
                String tipoOcorrencia       = primeiraOcorrencia[9];
                String anonimoOcorrencia    = primeiraOcorrencia[10];
                String apelidoOcorrencia    = primeiraOcorrencia[11];

                DadosOcorrencias dado = new DadosOcorrencias(numeroOcorrencia, focoCpf, ruaOcorrencia, bairroOcorrencia, cidadeOcorrencia, ufOcorrencia, descricaoOcorrencia, dataOcorrencia, tipoOcorrencia, anonimoOcorrencia, apelidoOcorrencia);

                ArrayOcorrenciasRegistradas.adicionar(dado);
            }

            Toast.makeText(this, "Mostrando suas Ocorrencias ", Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_listar_ocorrencias);
            Intent cliente = new Intent(this, ListarOcorrencias.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", clienteNome);
            bundle.putString("cpf", clienteCpf);
            bundle.putString("bairro", clienteBairro);

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
        ArrayImagensPerfil.deleteBitmap();
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

    public static String getClienteNome() {
        return clienteNome;
    }

    public static String getClienteCpf() {
        return clienteCpf;
    }

    public static String getClienteBairro() {
        return clienteBairro;
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


    public void entrarGaleria(View v) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                dialogo("É preciso a permission READ_EXTERNAL_STORAGE para acessar sua Galeria.", new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_CODE);
            }
        } else {
            abrirGaleria();
        }

    }

    private void abrirGaleria() {

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
        if (retorno.equals("erro")) {
            Toast.makeText(getApplicationContext(), "Erro de Conexão", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "imagem do Perfil Atualizada", Toast.LENGTH_SHORT).show();
            ArrayImagensPerfil.deleteBitmap();
            ArrayImagensPerfil.adicionarImg(bitmap);
        }
    }

    public String enviarImgPerfil() throws IOException {

        String retornoImg = processaSocket.enviaImgPerfil(clienteCpf, "Img1", byteImagem);

        if (retornoImg.equals("erro")) {
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

