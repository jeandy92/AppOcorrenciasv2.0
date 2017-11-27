package appocorrencias.com.appocorrencias.Activitys;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Adapters.AdapterFeed;
import appocorrencias.com.appocorrencias.ClassesSA.ProtocoloErlang;
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfilComentarios;
import appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas;
import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
import appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.Activitys.CadastraOcorrencia.removerAcentos;
import static appocorrencias.com.appocorrencias.ListView.ArrayComentariosRegistrados.deleteAllArrayComentarios;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.deleteAllArray;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getListaOcorrencia;
import static appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias.evBuscarComentario;
import static appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias.evBuscarImagens;

public class BuscaOcorrencias extends AppCompatActivity {

    private RadioButton rbtTipoOcorrencia, rbtBairro;
    private Spinner spnTipoOcorrencia;
    private TextInputLayout tinpBairro;
    private EditText edtFoco, edtBairroText;
    private ListView lvFeedOcorrencias;
    public static String nomeBuscarOcorrencia, cpfBuscarOcorrencia, bairroBuscarOcorrencia, telaBuscarOcorrencia, telaBusca , Ip;
    public static ProtocoloErlang processa = new ProtocoloErlang();
    public static int Porta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_de_ocorrencias);

        //Pegando valores que vem do Login  - TEM Q MANTER DESSA FORMA SE NAO QUANDO LOGAR COM OUTRO USUARIO O cpf MANTEM O MESMO
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nomeBuscarOcorrencia = bundle.getString("nome");
        cpfBuscarOcorrencia = bundle.getString("cpf");
        bairroBuscarOcorrencia = bundle.getString("bairro");
        telaBuscarOcorrencia = bundle.getString("tela");
        telaBusca = bundle.getString("telaBusca");
        Ip = bundle.getString("ip");
        Porta = bundle.getInt("porta");


        edtFoco = (EditText) findViewById(R.id.edtFoco);

        rbtTipoOcorrencia = (RadioButton) findViewById(R.id.rbtCPF);
        rbtBairro = (RadioButton) findViewById(R.id.rbtNome);

        spnTipoOcorrencia = (Spinner) findViewById(R.id.spnTipoOcorrencia);
        tinpBairro = (TextInputLayout) findViewById(R.id.input_layout_Nome);
        edtBairroText = (EditText) findViewById(R.id.edtNome);
        lvFeedOcorrencias = (ListView) findViewById(R.id.lv_usuarios_encontrados);

        spnTipoOcorrencia.setVisibility(View.INVISIBLE);
        spnTipoOcorrencia.setEnabled(false);

        tinpBairro.setVisibility(View.INVISIBLE);
        tinpBairro.setEnabled(false);

        rbtTipoOcorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtBairro.setChecked(false);

                spnTipoOcorrencia.setVisibility(View.VISIBLE);
                spnTipoOcorrencia.setEnabled(true);

                edtBairroText.setText("");
                tinpBairro.setVisibility(View.INVISIBLE);
                tinpBairro.setEnabled(false);
            }
        });
        rbtBairro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtTipoOcorrencia.setChecked(false);

                tinpBairro.setVisibility(View.VISIBLE);
                tinpBairro.setEnabled(true);
                tinpBairro.clearFocus();
                edtFoco.requestFocus();

                spnTipoOcorrencia.setVisibility(View.INVISIBLE);
                spnTipoOcorrencia.setEnabled(false);
            }
        });

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.TIPOS_CRIME, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoOcorrencia.setAdapter(adapter);


        lvFeedOcorrencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {

                    Intent i = new Intent(view.getContext(), ItemFeedOcorrencias.class);
                    String idOcorrencia = ((TextView) view.findViewById(R.id.txt_id_ocorrencia)).getText().toString();
                    String descOcorrencia = ((TextView) view.findViewById(R.id.txt_desc_comentario)).getText().toString();
                    String tipoCrime = ((TextView) view.findViewById(R.id.tv_bairro)).getText().toString();

                    i.putExtra("cpf", cpfBuscarOcorrencia);
                    i.putExtra("nome", nomeBuscarOcorrencia);
                    i.putExtra("bairro", bairroBuscarOcorrencia);
                    i.putExtra("id_ocorrencia", idOcorrencia);
                    i.putExtra("desc_ocorrencia", descOcorrencia);
                    i.putExtra("tipocrime", tipoCrime);
                    i.putExtra("tela", telaBuscarOcorrencia);
                    i.putExtra("telaBusca", "Busca");
                    i.putExtra("ip", Ip);
                    i.putExtra("porta", Porta);

                    deleteAllArrayComentarios();

                    try {
                        evBuscarImagens(idOcorrencia, "ocorrencia", Ip, Porta);
                        evBuscarComentario(idOcorrencia, Ip, Porta);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    startActivity(i);

                }
            }
        });

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


    public void evBuscarOcorrencias(View view) throws IOException {

        deleteAllArray();

        if (rbtBairro.isChecked()) {
            String bairroBusca = edtBairroText.getText().toString();
            evBuscarOcorrenciasBairro(bairroBusca, Ip, Porta);

            ArrayList<DadosOcorrencias> listaFeedOcorrencias = getListaOcorrencia();

            AdapterFeed adapter = new AdapterFeed(this, listaFeedOcorrencias);

            lvFeedOcorrencias.setAdapter(adapter);

        } else {

            if (rbtTipoOcorrencia.isChecked()) {

                String segundoTipoDeCrime = spnTipoOcorrencia.getSelectedItem().toString();
                String primeiroTipoDeCrime = removerAcentos(segundoTipoDeCrime);

                evBuscarOcorrenciasTipo(primeiroTipoDeCrime);

                ArrayList<DadosOcorrencias> listaFeedOcorrencias = getListaOcorrencia();

                AdapterFeed adapter = new AdapterFeed(this, listaFeedOcorrencias);

                lvFeedOcorrencias.setAdapter(adapter);

            } else {

                Toast.makeText(this, "Escolha um tipo de Busca ", Toast.LENGTH_SHORT).show();
            }
        }

        tinpBairro.clearFocus();
        edtFoco.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }


    public void evBuscarOcorrenciasBairro(String segundoBairro, String Ip, int Porta) throws IOException {

        String buscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradasBairro " + segundoBairro;
        //Toast.makeText(this, "Ocorrencias Registradas no meu bairro ", Toast.LENGTH_SHORT).show();
        ArrayImagensPerfilComentarios.deleteBitmap();
        String retorno = ProtocoloErlang.buscarDadosImagensServer(buscarOcorrenciasRegistradas, Ip, Porta);

        if (retorno.equals("false")) {
            Toast.makeText(this, "Não há ocorrências cadastradas neste bairro", Toast.LENGTH_SHORT).show();
        } else {
            // Pegando quantidade de Ocorrencias
            int qtdOcorrencia = ArrayOcorrenciasRegistradas.getQuantidadeOcorrencia(retorno);


            // Pegando dados e Adicioanando dados no Array
            for (int i = 0; i < qtdOcorrencia; i++) {

                String todasOcorrencias[] = retorno.split("///");
                String ocorrencia           = todasOcorrencias[i];
                String primeiraOcorrencia[] = ocorrencia.split("//");
                String NumeroOcorrencia     = primeiraOcorrencia[1];
                String focoCPF              = primeiraOcorrencia[2];
                String ruaOcorrencia        = primeiraOcorrencia[3];
                String bairroOcorrencia     = primeiraOcorrencia[4];
                String cidadeOcorrencia     = primeiraOcorrencia[5];
                String ufOcorrencia         = primeiraOcorrencia[6];
                String descOcorrencia       = primeiraOcorrencia[7];
                String dataOcorrencia       = primeiraOcorrencia[8];
                String tipoOcorrencia       = primeiraOcorrencia[9];
                String anonimoOcorrencia    = primeiraOcorrencia[10];
                String apelidoOcorrencia    = primeiraOcorrencia[11];

                DadosOcorrencias dado = new DadosOcorrencias(NumeroOcorrencia, focoCPF, ruaOcorrencia, bairroOcorrencia, cidadeOcorrencia, ufOcorrencia, descOcorrencia, dataOcorrencia, tipoOcorrencia, anonimoOcorrencia, apelidoOcorrencia);

                ArrayOcorrenciasRegistradas.adicionar(dado);
            }
            //Toast.makeText(this, "Mostrando Ocorrencias no seu bairro ", Toast.LENGTH_SHORT).show();
        }
    }


    public void evBuscarOcorrenciasTipo(String tipo) throws IOException {

        String BuscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradasTipo " + tipo;
        //Toast.makeText(this, "Ocorrencias Registradas no meu bairro ", Toast.LENGTH_SHORT).show();
        ArrayImagensPerfilComentarios.deleteBitmap();

        String retorno = ProtocoloErlang.buscarDadosImagensServer(BuscarOcorrenciasRegistradas, Ip,Porta );

        if (retorno.equals("false")) {
            Toast.makeText(this, "Não há ocorrencias cadastradas com esse tipo", Toast.LENGTH_SHORT).show();
        } else {
            // Pegando quantidade de Ocorrencias
            int qtdOcorrencia = ArrayOcorrenciasRegistradas.getQuantidadeOcorrencia(retorno);


            // Pegando dados e Adicioanando dados no Array
            for (int i = 0; i < qtdOcorrencia; i++) {
                String TodasOcorrencias[] = retorno.split("///");


                String todasOcorrencias[] = retorno.split("///");
                String ocorrencia           = todasOcorrencias[i];
                String primeiraOcorrencia[] = ocorrencia.split("//");
                String NumeroOcorrencia     = primeiraOcorrencia[1];
                String focoCPF              = primeiraOcorrencia[2];
                String ruaOcorrencia        = primeiraOcorrencia[3];
                String bairroOcorrencia     = primeiraOcorrencia[4];
                String cidadeOcorrencia     = primeiraOcorrencia[5];
                String ufOcorrencia         = primeiraOcorrencia[6];
                String descOcorrencia       = primeiraOcorrencia[7];
                String dataOcorrencia       = primeiraOcorrencia[8];
                String tipoOcorrencia       = primeiraOcorrencia[9];
                String anonimoOcorrencia    = primeiraOcorrencia[10];
                String apelidoOcorrencia    = primeiraOcorrencia[11];


                DadosOcorrencias dado = new DadosOcorrencias(NumeroOcorrencia, focoCPF, ruaOcorrencia, bairroOcorrencia, cidadeOcorrencia, ufOcorrencia, descOcorrencia, dataOcorrencia, tipoOcorrencia, anonimoOcorrencia, apelidoOcorrencia);

                ArrayOcorrenciasRegistradas.adicionar(dado);
            }
            //Toast.makeText(this, "Mostrando Ocorrencias no seu bairro ", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();


        if (telaBuscarOcorrencia.equals("Adm") ) {
            Intent adm = new Intent(this, Adm.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", nomeBuscarOcorrencia);
            bundle.putString("cpf", cpfBuscarOcorrencia);
            bundle.putString("bairro", bairroBuscarOcorrencia);

            adm.putExtras(bundle);
            this.startActivity(adm);
            this.finish();
        } else {


            deleteAllArray();

            try {
                Login.evBuscarOcorrenciasBairro(bairroBuscarOcorrencia, Ip, Porta);

                Intent cliente = new Intent(this, Cliente.class);
                Bundle bundle = new Bundle();
                bundle.putString("nome", nomeBuscarOcorrencia);
                bundle.putString("cpf", cpfBuscarOcorrencia);
                bundle.putString("bairro", bairroBuscarOcorrencia);
                bundle.putString("ip", Ip);
                bundle.putInt("porta", Porta);

                cliente.putExtras(bundle);
                this.startActivity(cliente);
                this.finish();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}
