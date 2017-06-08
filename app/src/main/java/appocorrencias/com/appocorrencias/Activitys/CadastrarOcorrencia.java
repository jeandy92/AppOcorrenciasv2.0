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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import appocorrencias.com.appocorrencias.ClassesSA.BuscarCep;
import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import me.drakeet.materialdialog.MaterialDialog;

import static appocorrencias.com.appocorrencias.Activitys.Login.evBuscarOcorrenciasBairro;

public class CadastrarOcorrencia extends AppCompatActivity implements LocationListener {

    private static final int REQUEST_PERMISSIONS_CODE = 128;

    private static final String TAG = "LOG";
    private static final android.util.Log LOG = null;

    //Váriaveis para realizar o controle do ResultActivity
    private static final int SELECIONA_IMAGEM = 1;
    private static final int IMAGEM_CAPTURADA = 1;

    public static final int IMAGEM_INTERNA = 12;
    private ImageButton imgBtnAdd;
    private ImageButton imgBtnDel;
    private ImageView iv1, iv2, iv3;
    private int cont = 1;


    private String convDataOcorrencia, convDescricao, convEndereco, convCidade, convBairro, tipo_crime, UF;
    ;
    private EditText txRua, txCidade, txEstado, txDescricao, txData_Ocorrencia, txtBairro, txReferencia;
    private CheckBox BtnAnonimo;

    private BuscarCep buscauf = new BuscarCep();

    private Location location;
    private LocationManager locationmenager;
    private android.location.Address endereco;
    private Spinner spinner;

    private Date data;


    static String Nome, CPFCliente, Bairro, Tela;

    public static ProcessaSocket processasocket = new ProcessaSocket();
    private String Anonimo;

    byte[] byteImagem = null, byteImagem2 = null, byteImagem3 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_ocorrencia);

        //Pegando valores que vem do Login
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Nome = bundle.getString("nome");
        CPFCliente = bundle.getString("cpf");
        Bairro = bundle.getString("bairro");
        Tela = bundle.getString("tela");


        imgBtnAdd = (ImageButton) findViewById(R.id.imgBtnAdd);
        imgBtnDel = (ImageButton) findViewById(R.id.imgBtnDel);

        iv1 = (ImageView) findViewById(R.id.imageView1);
        iv2 = (ImageView) findViewById(R.id.imageView2);
        iv3 = (ImageView) findViewById(R.id.ivCliente);


        txCidade = (EditText) findViewById(R.id.edtCidade);
        txRua = (EditText) findViewById(R.id.edtRua);
        txtBairro = (EditText) findViewById(R.id.edtBairro);
        txEstado = (EditText) findViewById(R.id.edtEstado);
        txReferencia = (EditText) findViewById(R.id.edtReferencia);
        txDescricao = (EditText) findViewById(R.id.edtDescricao);
        txData_Ocorrencia = (EditText) findViewById((R.id.edtData_Ocorrencia));
        spinner = (Spinner) findViewById(R.id.spinner);
        BtnAnonimo = (CheckBox) findViewById((R.id.rdBtnAnonimo));


        txDescricao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txDescricao.setText("");
            }


        });

        // Inserindo Mascaras.
        MaskEditTextChangedListener maskData = new MaskEditTextChangedListener("##/##/####", txData_Ocorrencia);
        txData_Ocorrencia.addTextChangedListener(maskData);


        //criando um ArrayAdapter para usar as strings do array como default
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.TIPOS_CRIME, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Colocando data defaul
        data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarData = new SimpleDateFormat("dd-MM-yyyy");
        txData_Ocorrencia.setText(formatarData.format(data).replaceAll("[^0123456789]", ""));


    }


    // Galeria
    public void limparImg(View view) {
        if (cont == 2) {
            iv1.setImageBitmap(null);
            iv1.setBackgroundResource(R.drawable.cam1);
            cont--;

        } else if (cont == 3) {
            iv2.setImageBitmap(null);
            iv2.setBackgroundResource(R.drawable.cam1);
            cont--;

        } else if (cont == 4) {
            iv3.setImageBitmap(null);
            iv3.setBackgroundResource(R.drawable.cam1);
            cont--;

        }
    }

    // Galeria
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


    public void localidade_atual(View v) {


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
        Toast.makeText(this, "Sua Localidade", Toast.LENGTH_SHORT).show();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        Location location = null;
        double latitude = 0;
        double longitude = 0;

        if (!isGPSEnabled || !isNetworkEnabled) {
            Log.i(TAG, "A geolocalização não pode ser usada.");
        } else {
            if (isNetworkEnabled) {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, this);
                Toast.makeText(this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Network");

                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


                if (location != null) {


                    latitude = location.getLatitude();
                    longitude = location.getLongitude();


                }
            }

            if (isGPSEnabled) {
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
        }
        Log.i(TAG, "Lat: " + latitude + " | Long: " + longitude);

        try {
            //endereco = buscarEndereco(latitude   ,  longitude);
            endereco = buscarEndereco(-23.540827, -46.761993);


            Log.i(TAG, endereco.getLocality());
            //Log.i(TAG, endereco.getAdminArea());
            Log.i(TAG, endereco.getAddressLine(1));
            Log.i(TAG, endereco.getSubLocality());

            BuscarCep buscar_cep = new BuscarCep();

            txCidade.setText(endereco.getLocality());
            txEstado.setText(endereco.getAdminArea());
            txRua.setText(endereco.getThoroughfare());
            txtBairro.setText(endereco.getSubLocality());
            txEstado.setText(buscar_cep.getUF(endereco.getPostalCode()));


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

                ActivityCompat.requestPermissions(CadastrarOcorrencia.this, permissions, REQUEST_PERMISSIONS_CODE);
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


    //Abrir Calendário

    public void evEscolher_Data(View v) {
    }



    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }


    public void salvar_ocorrencia(View v) throws IOException {

        String tipo_crime2 = spinner.getSelectedItem().toString();
        UF = txEstado.getText().toString();
        convDataOcorrencia = txData_Ocorrencia.getText().toString();
        String convDescricao2 = txDescricao.getText().toString();
        String convEndereco2 = txRua.getText().toString();
        String convCidade2 = txCidade.getText().toString();
        String convBairro2 = txtBairro.getText().toString();

        String ArrayNome[] = Nome.split(" ");
        String PriNome = ArrayNome[1];

        tipo_crime = removerAcentos(tipo_crime2);
        convDescricao = removerAcentos(convDescricao2);
        convEndereco = removerAcentos(convEndereco2);
        convCidade = removerAcentos(convCidade2);
        convBairro = removerAcentos(convBairro2);

        BtnAnonimo = (CheckBox) findViewById(R.id.rdBtnAnonimo);

        Anonimo = "false";

        if (BtnAnonimo.isChecked()) {
            Anonimo = "true";
        }

        if (convDescricao.isEmpty()) {
            txDescricao.setError("Faltou preencher Descrição ");
            txDescricao.setFocusable(true);
            txDescricao.requestFocus();
        } else {
            if (convEndereco.isEmpty()) {
                txRua.setError("Faltou preencher Rua ");
                txRua.setFocusable(true);
                txRua.requestFocus();
            } else {
                if (convCidade.isEmpty()) {
                    txCidade.setError("Faltou preencher Cidade ");
                    txCidade.setFocusable(true);
                    txCidade.requestFocus();
                } else {
                    if (convBairro.isEmpty()) {
                        txtBairro.setError("Faltou preencher Bairro ");
                        txtBairro.setFocusable(true);
                        txtBairro.requestFocus();
                    } else {
                        if (UF.isEmpty()) {
                            txEstado.setError("Faltou preencher UF ");
                            txEstado.setFocusable(true);
                            txEstado.requestFocus();
                        } else {

                            Random random = new Random();
                            int x = random.nextInt(101);
                            String NrAleatorio = Integer.toString(x);
                            String BuscaId = "IDocorrencia teste";
                            String IDserver = processasocket.cadastrar1_no_server(BuscaId);
                            //String ID = IDserver + NrAleatorio;



                            String retorno = processasocket.cadastrar_Ocorrencia(IDserver, CPFCliente, tipo_crime, convDataOcorrencia, UF, convDescricao,
                                    convEndereco, convCidade, convBairro, Anonimo, PriNome);

                            if (retorno.equals("erro")) {
                                Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();
                            } else {
                                if (retorno.equals("true")) {
                                    String retornoImg = null;

                                    // ENVIANDO IMAGEM
                                    if (byteImagem != null) {
                                        int x1 = random.nextInt(101);
                                        String IDImg = Integer.toString(x1);
                                        retornoImg = processasocket.envia_Img(IDImg, IDserver, CPFCliente, "Img1", byteImagem);
                                        if (retornoImg != null && byteImagem2 != null) {
                                            int x2 = random.nextInt(101);
                                            String IDImg2 = Integer.toString(x2);
                                            retornoImg = processasocket.envia_Img(IDImg2, IDserver, CPFCliente, "Img2", byteImagem2);
                                            if (retornoImg != null && byteImagem3 != null) {
                                                int x3 = random.nextInt(101);
                                                String IDImg3 = Integer.toString(x3);
                                                retornoImg = processasocket.envia_Img(IDImg3, IDserver, CPFCliente, "Img3", byteImagem3);
                                            } else {
                                                Toast.makeText(this, "Duas Img", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(this, "Uma Img", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(this, "Nao há imagem", Toast.LENGTH_SHORT).show();
                                    }

                                    Toast.makeText(this, "Ocorrencia Salva com sucesso", Toast.LENGTH_SHORT).show();

                                    evBuscarOcorrenciasBairro(Bairro);

                                    Intent cliente = new Intent(this, Cliente.class);

                                    Bundle bundle = new Bundle();
                                    bundle.putString("nome", Nome);
                                    bundle.putString("cpf", CPFCliente);
                                    bundle.putString("bairro", Bairro);

                                    cliente.putExtras(bundle);
                                    this.startActivity(cliente);


                                } else {
                                    txRua.setError("Erro Retorno do Server False");
                                    txRua.setFocusable(true);
                                    txRua.requestFocus();
                                }
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

        if (Tela.equals("Adm")) {
            Intent adm = new Intent(this, Adm.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", Nome);
            bundle.putString("cpf", CPFCliente);
            bundle.putString("bairro", Bairro);

            adm.putExtras(bundle);
            this.startActivity(adm);

        } else {

            try {
                evBuscarOcorrenciasBairro(Bairro);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent cliente = new Intent(this, Cliente.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", Nome);
            bundle.putString("cpf", CPFCliente);
            bundle.putString("bairro", Bairro);

            cliente.putExtras(bundle);
            this.startActivity(cliente);

        }
    }
}




