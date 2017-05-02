package appocorrencias.com.appocorrencias.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import appocorrencias.com.appocorrencias.ClassesSA.Buscar_Cep;
import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import me.drakeet.materialdialog.MaterialDialog;

public class Cadastrar_Ocorrencia extends AppCompatActivity implements  LocationListener {

    private static final int REQUEST_PERMISSIONS_CODE = 128;

    private static final String TAG = "LOG";
    private static final android.util.Log LOG = null;

    //Váriaveis para realizar o controle do ResultActivity
    private static final int SELECIONA_IMAGEM = 1;
    private static final int IMAGEM_CAPTURADA = 1;


    private String convDataOcorrencia,convDescricao,convEndereco,convCidade,convBairro , tipo_crime, UF;;
    private EditText txRua,txCidade,txEstado,txDescricao,txData_Ocorrencia,txtBairro, txReferencia;
    private RadioButton BtnAnonimo;

    private Buscar_Cep buscauf = new Buscar_Cep();

    private Location location;
    private LocationManager locationmenager;
    private android.location.Address endereco;
    private Spinner spinner;

    private Date data;


    static String  Nome, CPFCliente, Bairro;

    public static ProcessaSocket processasocket  = new ProcessaSocket();
    private String Anonimo;

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


        txCidade = (EditText) findViewById(R.id.edtCidade);
        txRua = (EditText) findViewById(R.id.edtRua);
        txtBairro = (EditText) findViewById(R.id.edtBairro);
        txEstado = (EditText) findViewById(R.id.edtEstado);
        txReferencia = (EditText) findViewById(R.id.edtReferencia);
        txDescricao = (EditText) findViewById(R.id.edtDescricao);
        txData_Ocorrencia = (EditText) findViewById((R.id.edtData_Ocorrencia));
        spinner = (Spinner) findViewById(R.id.spinner);


        //ArrayList<TiposDeCrime> list = criarcrimes();
        //AdapterSpinner adaptero = new AdapterSpinner(this,list);
        //listadecrimes.setAdapter(adaptero);

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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecionar Imagem"), SELECIONA_IMAGEM);

    }

    private String getPath(Uri uri) {

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null
        );


        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;

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
            endereco = buscarEndereco(-23.540827   ,  -46.761993);


            Log.i(TAG, endereco.getLocality());
            //Log.i(TAG, endereco.getAdminArea());
            Log.i(TAG, endereco.getAddressLine(1));
            Log.i(TAG, endereco.getSubLocality());

            Buscar_Cep buscar_cep = new Buscar_Cep();

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

                ActivityCompat.requestPermissions(Cadastrar_Ocorrencia.this, permissions, REQUEST_PERMISSIONS_CODE);
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

    public void  evEscolher_Data(View v){
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setContentView(R.layout.activity_cliente);
        Intent intent = new Intent(this,Cliente.class);
        startActivity(intent);

    }


    public void salvar_ocorrencia(View v) throws IOException {

        tipo_crime =  spinner.getSelectedItem().toString();
        UF = txEstado.getText().toString();
        convDataOcorrencia = txData_Ocorrencia.getText().toString();
        convDescricao = txDescricao.getText().toString();
        convEndereco = txRua.getText().toString();
        convCidade = txCidade.getText().toString();
        convBairro = txtBairro.getText().toString();

        BtnAnonimo = (RadioButton) findViewById(R.id.rdBtnAnonimo);

        if(BtnAnonimo.isChecked()){
            Anonimo = "true";
        }

        String BuscaId = "IDocorrencia teste";
        String ID = processasocket.cadastrar1_no_server(BuscaId);

        String retorno = processasocket.cadastrar_Ocorrencia(ID, CPFCliente,tipo_crime,convDataOcorrencia,UF, convDescricao,
                convEndereco,convCidade,convBairro, Anonimo);

        if (retorno.equals("erro")) {
            Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();
        } else {
            if (retorno.equals("true")) {
                Toast.makeText(this, "Ocorrencia Salva com sucesso", Toast.LENGTH_SHORT).show();

                setContentView(R.layout.activity_cliente);
                Intent cliente = new Intent(this, Cliente.class);

                Bundle bundle = new Bundle();
                bundle.putString("nome", Nome);
                bundle.putString("cpf" , CPFCliente);
                bundle.putString("bairro" , Bairro);

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




