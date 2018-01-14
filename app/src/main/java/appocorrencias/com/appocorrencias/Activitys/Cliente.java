package appocorrencias.com.appocorrencias.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Adapters.AdapterFeed;
import appocorrencias.com.appocorrencias.ClassesSA.MDUsuario;
import appocorrencias.com.appocorrencias.ClassesSA.ProtocoloErlang;
import appocorrencias.com.appocorrencias.ListView.ArrayImagens;
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfil;
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfilComentarios;
import appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas;
import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
import appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias;
import appocorrencias.com.appocorrencias.R;
import me.drakeet.materialdialog.MaterialDialog;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static appocorrencias.com.appocorrencias.Activitys.CadastraOcorrencia.protocoloErlang;
import static appocorrencias.com.appocorrencias.ListView.ArrayComentariosRegistrados.deleteAllArrayComentarios;
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
    public static String Nome, CPF, Bairro, Ip, Imagemperfil;
    public static int Porta;

    Bitmap bitmap;
    private static final int REQUEST_PERMISSIONS_CODE = 128;

    private static final String TAG = "LOG";
    //Váriaveis para realizar o controle do ResultActivity
    public static final int IMAGEM_INTERNA = 12;
    private ImageView ivCliente;

    byte[] byteImagem = null;
    byte[] imagemPerfil;
    String imgArray;

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

        // Método para buscar a imagem de perfil do usuário
        buscarImagemPerfil();


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
                    i.putExtra("ip", Ip);
                    i.putExtra("porta", Porta);
                    i.putExtra("id_ocorrencia", idocorrencia);
                    i.putExtra("desc_ocorrencia", descocorrencia);
                    i.putExtra("tipocrime", tipocrime);
                    i.putExtra("tela", tela);
                    i.putExtra("telaBusca", "Cliente");


                    deleteAllArrayComentarios();

                    String retornoImagem = null;
                    try {
                        retornoImagem = evBuscarImagens(idocorrencia, "ocorrencia", Ip, Porta);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (retornoImagem.equals("true") || retornoImagem.equals("false")) {
                        String retornoComent = null;
                        try {
                            retornoComent = evBuscarComentario(idocorrencia, Ip, Porta);

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

    private Bitmap buscarImagemPerfil() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                MDUsuario usu = new MDUsuario();
                Gson gson = new Gson();

                try {

                    String url = getResources().getString(R.string.ipConexao) + getResources().getString(R.string.endpointBuscarUsuarioOcorrencia) + CPF;

                    OkHttpClient clientbusca = new OkHttpClient();

                    Request requestbusca = new Request.Builder().url(url).build();

                    Response responsebusca = null;

                    responsebusca = clientbusca.newCall(requestbusca).execute();

                    String jsonDeRespostaBusca = responsebusca.body().string();

                    System.out.println(jsonDeRespostaBusca);

                    JSONObject json = new JSONObject(jsonDeRespostaBusca);

                    usu = gson.fromJson(jsonDeRespostaBusca, MDUsuario.class);

                    ImageView imgView = new ImageView(Cliente.this);
                    byte[] decodedBytes = Base64.decode(usu.getFt_perfil().getBytes(), Base64.DEFAULT);//Campo do tipo String recuperado do banco
                    BitmapFactory factory = new BitmapFactory();
                    bitmap = factory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                    ivCliente.setImageBitmap(bitmap);
                    Log.e("Imagem Perfi", usu.getFt_perfil());


                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    Log.e("Teste", "Erro: " + e.getMessage());
                }


            }
        });

        return bitmap;
    }


    public void cadastrarroubo(View v) {


        Bundle b = new Bundle();
        b.putString("tipocrime", "roubo");

        this.startActivity(new Intent(this, CadastraOcorrencia.class));
        this.finish();
    }


    protected void CriaNotificaçoes() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.fab_plus_icon)
                .setContentTitle("Um novo crime foi registrado próximo ao lugar onde mora")
                .setContentText("Um novo crime registrado");
    }

    public void onUpdateCliente() {

        this.startActivity(new Intent(this, CadastraUsuario.class));
    }


    public void evBuscarOcorrencias(View v) {

        Intent cliente = new Intent(this, BuscaOcorrencias.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome", Nome);
        bundle.putString("cpf", CPF);
        bundle.putString("bairro", Bairro);
        bundle.putString("tela", "Cliente");
        bundle.putString("ip", Ip);
        bundle.putInt("porta", Porta);

        cliente.putExtras(bundle);
        this.startActivity(cliente);
        this.finish();
    }

    // OBS FUNCAO ESTAVA FORA DO CODIGGO JEAN VERIFICAR
    public void evCadastrarOcorrencia(View view) {
        deleteAllArray();

        Intent cadastrarOcorrencia = new Intent(this, CadastraOcorrencia.class);

        Bundle bundle = new Bundle();
        bundle.putString("nome", Nome);
        bundle.putString("cpf", CPF);
        bundle.putString("bairro", Bairro);
        bundle.putString("tela", "Cliente");
        bundle.putString("ip", Ip);
        bundle.putInt("porta", Porta);

        cadastrarOcorrencia.putExtras(bundle);
        this.startActivity(cadastrarOcorrencia);
        this.finish();
    }

    public void evOcorrenciasInformadas(View view) throws IOException {

        ArrayOcorrenciasRegistradas.deleteAllArray();

        String BuscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradas" + " " + CPF;


        Toast.makeText(this, "Minhas Ocorrencias Registradas ", Toast.LENGTH_SHORT).show();

        ArrayImagensPerfilComentarios.deleteBitmap();
        String retorno = ProtocoloErlang.buscarDadosImagensServer(BuscarOcorrenciasRegistradas, Ip, Porta);

        if (retorno.equals("false")) {

            Toast.makeText(this, "Não há ocorrencias cadastradas", Toast.LENGTH_SHORT).show();

            Intent cliente = new Intent(this, ListaOcorrencias.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", Nome);
            bundle.putString("cpf", CPF);
            bundle.putString("bairro", Bairro);
            bundle.putString("ip", Ip);
            bundle.putInt("porta", Porta);

            cliente.putExtras(bundle);
            this.startActivity(cliente);
            this.finish();
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

            Intent cliente = new Intent(this, ListaOcorrencias.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", Nome);
            bundle.putString("cpf", CPF);
            bundle.putString("bairro", Bairro);
            bundle.putString("ip", Ip);
            bundle.putInt("porta", Porta);

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


    public void entrarGaleria(View v) {

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
                Log.d("Imag", err + "o");
            }
        }
    }


    //// Bitmap em bytes

    public void toByte1(Bitmap bitmap) throws IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();


        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);


        byte[] byteArray = stream.toByteArray();

        imgArray = Base64.encodeToString(byteArray, Base64.DEFAULT);


        String retorno = enviarImgPerfil();

        if (retorno.equals("erro")) {
            Toast.makeText(getApplicationContext(), "Erro de Conexão", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Imagem do Perfil Atualizada", Toast.LENGTH_SHORT).show();
            ArrayImagensPerfil.deleteBitmap();
            ArrayImagensPerfil.adicionarImg(bitmap);
        }
    }

    public String enviarImgPerfil() throws IOException {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                MDUsuario usu = new MDUsuario();
                Gson gson = new Gson();

                try {
                    usu.setCpf(CPF);
                    usu.setFt_perfil(imgArray);

                    OkHttpClient client = new OkHttpClient();


                    Request.Builder builder = new Request.Builder();

                    builder.url(getResources().getString(R.string.ipConexao) + getResources().getString(R.string.endpointCadastrarImagemPerfil));

                    MediaType mediaType =
                            MediaType.parse("application/json; charset=utf-8");


                    RequestBody body = RequestBody.create(mediaType, gson.toJson(usu));

                    builder.post(body);

                    Request request = builder.build();
                    Response response = null;

                    response = client.newCall(request).execute();
                    final String jsonDeResposta = response.body().string();


                    Cliente.this.runOnUiThread(new Runnable() {
                        public void run() {

                            if (jsonDeResposta.equals("USUARIO ALTERADO  COM SUCESSO !!")) {
                                Toast.makeText(Cliente.this, "FOTO ALTERADA", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Cliente.this, "FOTO NÃO ALTERADA", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        String retornoImg = protocoloErlang.envia_Img_Perfil(CPF, "Img1", byteImagem, Ip, Porta);

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

