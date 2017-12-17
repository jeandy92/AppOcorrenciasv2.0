package appocorrencias.com.appocorrencias.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Adapters.AdapterBuscaUsuario;
import appocorrencias.com.appocorrencias.ClassesSA.MDUsuario;
import appocorrencias.com.appocorrencias.ListView.ItemBuscaUsuario;
import appocorrencias.com.appocorrencias.R;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static appocorrencias.com.appocorrencias.ListView.ArrayUsuariosEncontrados.deleteAllArrayUsuarios;
import static appocorrencias.com.appocorrencias.ListView.ArrayUsuariosEncontrados.getListaUsuarios;

public class BuscaUsuarios extends AppCompatActivity {

    private RadioButton rbtCPF, rbtNome;
    private TextInputLayout tinpCPF, tinpNome;
    private EditText edtFoco, edtCPF, edtNome;
    private ListView lvUsuariosEncontrados;
    public static String Ip;
    public static int Porta;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_de_usuarios);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Ip = bundle.getString("ip");
        Porta = bundle.getInt("porta");

        //List View para mostrar os usuários
        lvUsuariosEncontrados = (ListView) findViewById(R.id.lv_usuarios_encontrados);

        ArrayList<MDUsuario> listaUsuariosEncontrados = getListaUsuarios(null);

        AdapterBuscaUsuario adapter = new AdapterBuscaUsuario(this, listaUsuariosEncontrados);

        lvUsuariosEncontrados.setAdapter(adapter);


        lvUsuariosEncontrados.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(), ItemBuscaUsuario.class);
                String cpfUsuario = ((TextView) view.findViewById(R.id.txt_CPF)).getText().toString();
                //deleteAllArrayUsuarios();
                intent.putExtra("cpfUsuario", cpfUsuario);
                intent.putExtra("ip", Ip);
                intent.putExtra("porta", Porta);


            }
        });


        edtFoco = (EditText) findViewById(R.id.edtFoco);

        rbtCPF = (RadioButton) findViewById(R.id.rbtCPF);
        rbtNome = (RadioButton) findViewById(R.id.rbtNome);

        tinpNome = (TextInputLayout) findViewById(R.id.input_layout_Nome);
        tinpCPF = (TextInputLayout) findViewById(R.id.input_layout_CPF);

        edtCPF = (EditText) findViewById(R.id.edtCPF);
        edtNome = (EditText) findViewById(R.id.edtNome);

        startActivity(intent);
        tinpCPF.setVisibility(View.INVISIBLE);
        tinpCPF.setEnabled(false);

        tinpNome.setVisibility(View.INVISIBLE);
        tinpNome.setEnabled(false);

        rbtCPF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtFoco.requestFocus();

                rbtNome.setChecked(false);

                tinpCPF.setVisibility(View.VISIBLE);
                tinpCPF.setEnabled(true);
                edtCPF.clearFocus();

                edtNome.setText("");
                tinpNome.setVisibility(View.INVISIBLE);
                tinpNome.setEnabled(false);
            }
        });

        rbtNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtFoco.requestFocus();

                rbtCPF.setChecked(false);

                tinpNome.setVisibility(View.VISIBLE);
                tinpNome.setEnabled(true);
                tinpNome.clearFocus();
                edtNome.clearFocus();


                edtCPF.setText("");
                tinpCPF.setVisibility(View.INVISIBLE);
                tinpCPF.setEnabled(false);
            }
        });

        edtCPF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edtNome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        lvUsuariosEncontrados.setOnTouchListener(new ListView.OnTouchListener() {
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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void evBuscarUsuario(View view) throws IOException {

        deleteAllArrayUsuarios();

        if (rbtCPF.isChecked()) {
            String usuarioBusca = edtCPF.getText().toString();
            evBuscarUsuarioCpf(usuarioBusca);

            ArrayList<MDUsuario> listaUsuarios = getListaUsuarios(usuarioBusca);

            AdapterBuscaUsuario adapter = new AdapterBuscaUsuario(this, listaUsuarios);

            lvUsuariosEncontrados.setAdapter(adapter);

        } else {

            if (rbtNome.isChecked()) {

                String nome = edtNome.getText().toString();

                evBuscarUsuarioNome(nome);

                ArrayList<MDUsuario> listaUsuarios = getListaUsuarios(nome);

                AdapterBuscaUsuario adapter = new AdapterBuscaUsuario(this, listaUsuarios);

                lvUsuariosEncontrados.setAdapter(adapter);

            } else {

                Toast.makeText(this, "Escolha um tipo de Busca ", Toast.LENGTH_SHORT).show();
            }
        }


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public void evBuscarUsuarioCpf(final String cpfBuscarUsuario) throws IOException {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    MDUsuario usuario = new MDUsuario();


                    OkHttpClient client = new OkHttpClient();


                    Request.Builder builder = new Request.Builder();

                    builder.url( getResources().getString(R.string.ipConexao) + getResources().getString(R.string.endpointBuscar)  + cpfBuscarUsuario);

                    MediaType mediaType =
                            MediaType.parse("application/json; charset=utf-8");

                    RequestBody body = RequestBody.create(mediaType, gson.toJson(usuario));

                    builder.post(body);

                    Request request = builder.build();
                    Response response = null;


                    response = client.newCall(request).execute();

                    final String jsonDeResposta = response.body().string();
                    System.out.println(jsonDeResposta);


                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    });


}

    public void evBuscarUsuarioNome(final String nomeBuscarUsuarioNome) throws IOException {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    MDUsuario usuario = new MDUsuario();


                    OkHttpClient client = new OkHttpClient();


                    Request.Builder builder = new Request.Builder();

                    builder.url(getResources().getString(R.string.ipConexao)  + getResources().getString(R.string.endpointBuscar) + nomeBuscarUsuarioNome);

                    MediaType mediaType =
                            MediaType.parse("application/json; charset=utf-8");

                    RequestBody body = RequestBody.create(mediaType, gson.toJson(usuario));

                    builder.post(body);

                    Request request = builder.build();
                    Response response = null;


                    response = client.newCall(request).execute();

                    final String jsonDeResposta = response.body().string();
                    System.out.println(jsonDeResposta);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent adm = new Intent(this, Adm.class);

        Bundle bundle = new Bundle();
        bundle.putString("nome", "Administrador");
        bundle.putString("cpf", "33333333333");
        bundle.putString("bairro", "Adm");
        bundle.putString("ip", Ip);
        bundle.putInt("porta", Porta);

        adm.putExtras(bundle);
        this.startActivity(adm);
        this.finish();
    }

}

