package appocorrencias.com.appocorrencias.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import appocorrencias.com.appocorrencias.ClassesSA.BuscaCep;
import appocorrencias.com.appocorrencias.ClassesSA.MDOcorrencia;
import appocorrencias.com.appocorrencias.ClassesSA.MDUsuario;
import appocorrencias.com.appocorrencias.ClassesSA.ProtocoloErlang;
import appocorrencias.com.appocorrencias.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import me.drakeet.materialdialog.MaterialDialog;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static appocorrencias.com.appocorrencias.Activitys.Login.evBuscarOcorrenciasBairro;
import static com.google.firebase.iid.FirebaseInstanceId.getInstance;

public class CadastraOcorrencia extends AppCompatActivity implements LocationListener {

    //CONSTANTES
    //Váriaveis para realizar o controle do ResultActivity
    private static final int REQUEST_PERMISSIONS_CODE = 128;
    private static final int SELECIONA_IMAGEM = 1;
    private static final int IMAGEM_CAPTURADA = 1;
    private static final String TAG = "LOG";
    private static final int IMAGEM_INTERNA = 12;

    //VARIÁVEIS UTILIZADAS EXTRAIDAS DE INTENTS
    private static final android.util.Log LOG = null;
    private Location location;
    private LocationManager locationmenager;
    private android.location.Address endereco;


    //VARIAVEIS DE ACTIVITYS
    private ImageButton imgBtnAdd, imgBtnDel;
    private ImageView iv1, iv2, iv3;
    private Button btnSalvaOcorrencia;
    private EditText edtRua, edtCidade, edtEstado, edtDescricao, edtDataOcorrencia, edtBairro, edtReferencia;
    private CheckBox btnAnonimo;
    private Spinner spinner;

    //VARIAVEIS LOCAIS
    private int cont = 1;






    public static String Anonimo, Ip;

    public static int Porta;
    private byte[] byteImagem = null, byteImagem2 = null, byteImagem3 = null;


    //VARIAVEIS STATICAS
    public static String nomeCliente, cpfCliente, bairroCliente, telaCliente, tokenUsuario;

    //OBEJTOS STATICOS
    public static ProtocoloErlang protocoloErlang = new ProtocoloErlang();

    //OBJETOS
    private BuscaCep buscauf = new BuscaCep();
    private Date data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_ocorrencia);

        //Pegando valores que vem do Login
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nomeCliente = bundle.getString("nome");
        cpfCliente = bundle.getString("cpf");
        bairroCliente = bundle.getString("bairro");
        telaCliente = bundle.getString("tela");
        Ip = bundle.getString("ip");
        Porta = bundle.getInt("porta");


        imgBtnAdd = (ImageButton) findViewById(R.id.imgBtnAdd);
        imgBtnDel = (ImageButton) findViewById(R.id.imgBtnDel);

        iv1 = (ImageView) findViewById(R.id.imageView1);
        iv2 = (ImageView) findViewById(R.id.imageView2);
        iv3 = (ImageView) findViewById(R.id.imageView3);


        edtCidade = (EditText) findViewById(R.id.edtCidade);
        edtRua = (EditText) findViewById(R.id.edtRua);
        edtBairro = (EditText) findViewById(R.id.edtBairro);
        edtEstado = (EditText) findViewById(R.id.edtEstado);
        edtReferencia = (EditText) findViewById(R.id.edtReferencia);
        edtDescricao = (EditText) findViewById(R.id.edtDescricao);
        edtDataOcorrencia = (EditText) findViewById(R.id.edtData_Ocorrencia);

        //preenchimento automático
        edtDescricao.setText("Assalto a mão armada");
        edtRua.setText("Rua Joaquim de Abreu ");
        edtBairro.setText("Jardim Silveira");
        edtCidade.setText("Barueri");
        edtReferencia.setText("Nenhuma ReferÊncia");
        edtEstado.setText("SP");

        //Spinner
        spinner = (Spinner) findViewById(R.id.spinner);

        //Botões
        btnAnonimo = (CheckBox) findViewById(R.id.rdBtnAnonimo);
        btnSalvaOcorrencia = (Button) findViewById(R.id.btnSalvaOcorrencia);


        // Inserindo Mascaras.
        MaskEditTextChangedListener maskData = new MaskEditTextChangedListener("##/##/####", edtDataOcorrencia);
        edtDataOcorrencia.addTextChangedListener(maskData);


        //criando um ArrayAdapter para usar as strings do array como default
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.TIPOS_CRIME, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Colocando data defaul
        data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarData = new SimpleDateFormat("dd-MM-yyyy");
        edtDataOcorrencia.setText(formatarData.format(data).replaceAll("[^0123456789]", ""));

    }

    // Galeria
    public void limparImg(View view) {
        if (cont == 2) {
            iv1.setImageBitmap(null);
            iv1.setBackgroundResource(R.drawable.cam1);
            cont--;
            byteImagem = null;

        } else if (cont == 3) {
            iv2.setImageBitmap(null);
            iv2.setBackgroundResource(R.drawable.cam1);
            cont--;
            byteImagem2 = null;

        } else if (cont == 4) {
            iv3.setImageBitmap(null);
            iv3.setBackgroundResource(R.drawable.cam1);
            cont--;
            byteImagem3 = null;
        }
    }

    // Galeria
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
                if (cont > 0 && cont < 4) {
                    if (cont == 1) {
                        iv1.setImageBitmap(bitmap);
                        toByte1(bitmap);
                        iv1.setBackground(null);
                        cont++;

                    } else if (cont == 2) {
                        iv2.setImageBitmap(bitmap);
                        toByte2(bitmap);
                        iv2.setBackground(null);
                        cont++;

                    } else if (cont == 3) {
                        iv3.setImageBitmap(bitmap);
                        toByte3(bitmap);
                        iv3.setBackground(null);
                        cont++;
                    }
                }
            } catch (Exception err) {
                Log.d("Imag", err.getMessage());
            }
        }
    }


    //// Bitmap em bytes
    public void toByte1(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        byteImagem = byteArray;
    }

    public void toByte2(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        byteImagem2 = byteArray;
    }

    public void toByte3(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        byteImagem3 = byteArray;
    }


    //Abrir Camera
    public void tirarFoto(View v) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                dialogo("É preciso a permission READ_EXTERNAL_STORAGE para acessar sua Galeria.", new String[]{Manifest.permission.CAMERA});
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS_CODE);
            }
        } else {
            abrirCamera();
        }

    }

    private void abrirCamera() {

        Intent selecionacameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (selecionacameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(selecionacameraIntent, IMAGEM_CAPTURADA);
        }

    }


    //Localidade
    public void localidadeAtual(View v) {


        LOG.i(TAG, "localidade_atual");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                dialogo("É preciso a permission ACCESS_FINE_LOCATION para acessar sua localização.", new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
            }
        } else {
            selecionandoLocalidadeAtual();
        }

    }

    private void selecionandoLocalidadeAtual() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        Location location = null;
        double latitude = 0;
        double longitude = 0;


        if (!isGPSEnabled) {
            if (location == null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, (LocationListener) this);
                Toast.makeText(this, "Localização desligada", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "GPS Enabled");
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        }

        if (!isGPSEnabled || !isNetworkEnabled) {
            Log.i(TAG, "A geolocalização não pode ser usada.");
            Toast.makeText(this, " A geolocalização não pode ser usada localização desligada ", Toast.LENGTH_SHORT).show();
        } else {
            if (isNetworkEnabled) {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, this);
                //Toast.makeText(this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Network");

                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


                if (location != null) {


                    latitude = location.getLatitude();
                    longitude = location.getLongitude();


                }
            }


        }
        Log.i(TAG, "Lat: " + latitude + " | Long: " + longitude);

        try {
            if (latitude != 0 || longitude != 0) {

                endereco = buscarEndereco(latitude, longitude);
                //endereco = buscarEndereco(-23.540827, -46.761993);


//            Log.i(TAG, endereco.getLocality());
                //Log.i(TAG, endereco.getAdminArea());
                // Log.i(TAG, endereco.getAddressLine(1));
                // Log.i(TAG, endereco.getSubLocality());

                BuscaCep buscar_cep = new BuscaCep();

                edtCidade.setText(endereco.getLocality());
                edtEstado.setText(endereco.getAdminArea());
                edtRua.setText(endereco.getThoroughfare());
                edtBairro.setText(endereco.getSubLocality());
                edtEstado.setText(buscar_cep.getUF(endereco.getPostalCode()));

            } else {
                Toast.makeText(this, "Ligar localização", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public android.location.Address buscarEndereco(double latitude, double longitude) throws IOException {

        Geocoder geocorder;
        android.location.Address address = null;
        List<android.location.Address> addresses;

        geocorder = new Geocoder(getApplicationContext());
        addresses = geocorder.getFromLocation(latitude, longitude, 1);
        if (addresses.size() > 0) {
            address = addresses.get(0);

        }


        return address;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "Lat: " + location.getLatitude() + "| Long:" + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void dialogo(String message, final String[] permissions) {

        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle("Permission");
        mMaterialDialog.setMessage(message);
        mMaterialDialog.setPositiveButton("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(CadastraOcorrencia.this, permissions, REQUEST_PERMISSIONS_CODE);
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

                        selecionandoLocalidadeAtual();
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

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public void evCadastrarOcorrencia(View v) throws IOException {


        btnAnonimo = (CheckBox) findViewById(R.id.rdBtnAnonimo);

        Anonimo = "false";

        if (btnAnonimo.isChecked()) {
            Anonimo = "true";
        }
        if (spinner.getSelectedItem().toString().isEmpty()) {
            edtEstado.setError("Faltou preencher tipo crime ");
            edtEstado.setFocusable(true);
            edtEstado.requestFocus();
        } else {
            if (edtDescricao.getText().toString().isEmpty()) {
                edtDescricao.setError("Faltou preencher Descrição ");
                edtDescricao.setFocusable(true);
                edtDescricao.requestFocus();
            } else {
                if (edtRua.getText().toString().isEmpty()) {
                    edtRua.setError("Faltou preencher Rua ");
                    edtRua.setFocusable(true);
                    edtRua.requestFocus();
                } else {
                    if (edtCidade.getText().toString().isEmpty()) {
                        edtCidade.setError("Faltou preencher Cidade ");
                        edtCidade.setFocusable(true);
                        edtCidade.requestFocus();
                    } else {
                        if (edtBairro.getText().toString().isEmpty()) {
                            edtBairro.setError("Faltou preencher bairro ");
                            edtBairro.setFocusable(true);
                            edtBairro.requestFocus();
                        } else {
                            if (edtEstado.getText().toString().isEmpty()) {
                                edtEstado.setError("Faltou preencher UF ");
                                edtEstado.setFocusable(true);
                                edtEstado.requestFocus();
                            } else {


                            //Cadastrando ocorrencia pela API
                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            String url = getResources().getString(R.string.ipConexao) + getResources().getString(R.string.endpointBuscarUsuarioOcorrencia) + cpfCliente;

                                            MDOcorrencia ocor = new MDOcorrencia();
                                            MDUsuario     usu ;

                                            Gson gson = new Gson();

                                            OkHttpClient clientbusca = new OkHttpClient();

                                            Request requestbusca = new Request.Builder().url(url).build();

                                            Response responsebusca = clientbusca.newCall(requestbusca).execute();

                                            String jsonDeRespostaBusca = responsebusca.body().string();

                                            System.out.println(jsonDeRespostaBusca);

                                            JSONObject json = new JSONObject(jsonDeRespostaBusca);

                                            usu = gson.fromJson(jsonDeRespostaBusca, MDUsuario.class);

                                            System.out.println("----------------" +usu.getCpf());


                                        String referencia;
                                        if (edtReferencia.getText().toString().isEmpty()){
                                             referencia = "Nenhuma Referência";
                                        } else {
                                            referencia = edtReferencia.getText().toString();
                                        }


                                            ocor.setTipo      ( spinner           .getSelectedItem().toString() );
                                            ocor.setDescricao ( edtDescricao      .getText()        .toString() );
                                            ocor.setRua       ( edtRua            .getText()        .toString() );
                                            ocor.setBairro    ( edtBairro         .getText()        .toString() );
                                            ocor.setCidade    ( edtCidade         .getText()        .toString() );
                                            ocor.setUf        ( edtEstado         .getText()        .toString() );
                                            ocor.setData      ( edtDataOcorrencia .getText()        .toString() );
                                            ocor.setUsuario   (  usu );
                                            ocor.setReferencia( referencia );


                                        OkHttpClient client = new OkHttpClient();


                                        Request.Builder builder = new Request.Builder();

                                        builder.url(getResources().getString(R.string.ipConexao) + getResources().getString(R.string.endpointCadastrarOcorrencia));


                                        MediaType mediaType =
                                                MediaType.parse("application/json; charset=utf-8");


                                        RequestBody body = RequestBody.create(mediaType, gson.toJson(ocor));

                                        builder.post(body);

                                        Request request = builder.build();
                                        Response response = null;

                                        response = client.newCall(request).execute();
                                        final String jsonDeResposta = response.body().string();
                                            System.out.println("Usuario id "+ usu.getId());

                                        CadastraOcorrencia.this.runOnUiThread(new Runnable() {
                                            public void run() {

                                                if(jsonDeResposta.equals("OCORRÊNCIA CADASTRADO COM SUCESSO !!")){
                                                    Toast.makeText(CadastraOcorrencia.this, "ocorrencia cadstrada com sucesso", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    if (jsonDeResposta.equals("ERRO AO CADASTRAR OCORRENCIA")){
                                                        Toast.makeText(CadastraOcorrencia.this, "Erro ao cadastrar ocorrencia, entre em contato com adimistrador", Toast.LENGTH_SHORT).show();
                                                    }
                                                }


                                                if (telaCliente.equals("Cliente")) {


                                                    Intent cliente = new Intent(CadastraOcorrencia.this, Cliente.class);

                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("nome", nomeCliente);
                                                    bundle.putString("cpf", cpfCliente);
                                                    bundle.putString("bairro", bairroCliente);
                                                    bundle.putString("ip", Ip);
                                                    bundle.putInt("porta", Porta);

                                                    cliente.putExtras(bundle);


                                                    protocoloErlang.enviandoNotificacaoGrupo(getInstance().getToken(), edtBairro.getText().toString());
                                                    Toast.makeText(CadastraOcorrencia.this, "Ocorrência salva com sucesso", Toast.LENGTH_SHORT).show();


                                                    CadastraOcorrencia.this.startActivity(cliente);
                                                    CadastraOcorrencia.this.finish();
                                                } else

                                                {

                                                    Intent adm = new Intent(CadastraOcorrencia.this, Adm.class);

                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("nome", "Administrador");
                                                    bundle.putString("cpf", "33333333333");
                                                    bundle.putString("bairro", "Adm");

                                                    Toast.makeText(CadastraOcorrencia.this, "ADM - Ocorrência Salva com sucesso", Toast.LENGTH_SHORT).show();
                                                    adm.putExtras(bundle);
                                                    CadastraOcorrencia.this.startActivity(adm);
                                                    CadastraOcorrencia.this.finish();
                                                }
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                                }
                            }
                        }
                    }
                }
            }
        }





    @Override
    public void onBackPressed() {

        super.onBackPressed();
        if (telaCliente.equals("Adm")) {
            Intent adm = new Intent(this, Adm.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", nomeCliente);
            bundle.putString("cpf", cpfCliente);
            bundle.putString("bairro", bairroCliente);
            bundle.putString("tela","adm");

            adm.putExtras(bundle);
            this.startActivity(adm);
            this.finish();
        } else {

            try {
                evBuscarOcorrenciasBairro(bairroCliente, Ip, Porta);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent cliente = new Intent(this, Cliente.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", nomeCliente);
            bundle.putString("cpf", cpfCliente);
            bundle.putString("bairro", bairroCliente);
            bundle.putString("tela","CadstrarOcorrencia");


            cliente.putExtras(bundle);
            this.startActivity(cliente);
            this.finish();
        }
    }
}




