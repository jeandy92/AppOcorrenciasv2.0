package appocorrencias.com.appocorrencias.Activitys;


        import android.content.Intent;
        import android.os.Bundle;
        import android.support.design.widget.TextInputLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
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

        import appocorrencias.com.appocorrencias.Adapters.FeedAdapter;
        import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
        import appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas;
        import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
        import appocorrencias.com.appocorrencias.ListView.Item_Feed_Ocorrencias;
        import appocorrencias.com.appocorrencias.R;

        import static appocorrencias.com.appocorrencias.ListView.ArrayComentariosRegistrados.deleteAllArrayComentarios;
        import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.deleteAllArray;
        import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getListaOcorrencia;
        import static appocorrencias.com.appocorrencias.ListView.Item_Feed_Ocorrencias.evBuscarComentario;

public class Buscar_Ocorrencias extends AppCompatActivity {

    private RadioButton rbtTipoOcorrencia, rbtBairro;
    private Spinner  spnTipoOcorrencia;
    private TextInputLayout edtBairro;
    private EditText edtFoco, edtBairroText;
    private ListView lvFeedOcorrencias;
    public static String Nome, CPF, Bairro;
    public static ProcessaSocket processa = new ProcessaSocket();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_de_ocorrencias);

        //Pegando valores que vem do Login  - TEM Q MANTER DESSA FORMA SE NAO QUANDO LOGAR COM OUTRO USUARIO O CPF MANTEM O MESMO
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Nome = bundle.getString("nome");
        CPF = bundle.getString("cpf");
        Bairro = bundle.getString("bairro");


        edtFoco = (EditText) findViewById(R.id.edtFoco);

        rbtTipoOcorrencia = (RadioButton) findViewById(R.id.rbtCPF);
        rbtBairro = (RadioButton) findViewById(R.id.rbtNome);

        spnTipoOcorrencia = (Spinner) findViewById(R.id.spnTipoOcorrencia);
        edtBairro = (TextInputLayout) findViewById(R.id.input_layout_Nome);
        edtBairroText = (EditText) findViewById(R.id.edtNome);
        lvFeedOcorrencias =  (ListView) findViewById(R.id.lv_feed_de_ocorrencias);

        spnTipoOcorrencia.setVisibility(View.INVISIBLE);
        spnTipoOcorrencia.setEnabled(false);

        edtBairro.setVisibility(View.INVISIBLE);
        edtBairro.setEnabled(false);

        rbtTipoOcorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbtBairro.setChecked(false);

                spnTipoOcorrencia.setVisibility(View.VISIBLE);
                spnTipoOcorrencia.setEnabled(true);

                edtBairroText.setText("");
                edtBairro.setVisibility(View.INVISIBLE);
                edtBairro.setEnabled(false);
            }
        });
        rbtBairro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                rbtTipoOcorrencia.setChecked(false);

                edtBairro.setVisibility(View.VISIBLE);
                edtBairro.setEnabled(true);
                edtBairro.clearFocus();
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
                if (position >= 0){

                    Intent i = new Intent(view.getContext(), Item_Feed_Ocorrencias.class);
                    String idocorrencia = ((TextView) view.findViewById(R.id.txt_id_ocorrencia)).getText().toString();
                    String descocorrencia = ((TextView) view.findViewById(R.id.txt_desc_comentario)).getText().toString();
                    String tipocrime = ((TextView) view.findViewById(R.id.txt_tipo_crime)).getText().toString();

                    i.putExtra("cpf", CPF);
                    i.putExtra("nome", Nome);
                    i.putExtra("bairro", Bairro);
                    i.putExtra("id_ocorrencia", idocorrencia);
                    i.putExtra("desc_ocorrencia", descocorrencia);
                    i.putExtra("tipocrime", tipocrime);

                    deleteAllArrayComentarios();

                    try {
                        evBuscarComentario(idocorrencia);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    startActivity(i);

                }
            }
        });

    }




    public void evBuscarOcorrencias (View view) throws IOException {

        deleteAllArray();

        if (rbtBairro.isChecked()) {
            String BairroBusca = edtBairroText.getText().toString();
            evBuscarOcorrenciasBairro(BairroBusca);

            ArrayList<DadosOcorrencias> listafeedocorrencias = getListaOcorrencia();

            FeedAdapter adapter = new FeedAdapter(this, listafeedocorrencias);

            lvFeedOcorrencias.setAdapter(adapter);


        } else {

        }
    }



    public static void evBuscarOcorrenciasBairro (String Bairro2) throws IOException {

        String BuscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradasBairro " +  Bairro2;
        //Toast.makeText(this, "Ocorrencias Registradas no meu Bairro ", Toast.LENGTH_SHORT).show();
        String retorno = processa.cadastrar1_no_server(BuscarOcorrenciasRegistradas);

        if (retorno.equals("false")) {
            // Toast.makeText(this, "Não há ocorrencias cadastradas no seu bairro", Toast.LENGTH_SHORT).show();
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

                DadosOcorrencias dado = new DadosOcorrencias(Nr, CPFOco, Rua, Bairro, Cidade, UF, Descricao, Data, Tipo, Anonimo,Apelido);

                ArrayOcorrenciasRegistradas.adicionar(dado);
            }
            //Toast.makeText(this, "Mostrando Ocorrencias no seu Bairro ", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent cliente = new Intent(this, Cliente.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome", Nome);
        bundle.putString("cpf", CPF);
        bundle.putString("bairro", Bairro);

        cliente.putExtras(bundle);
        this.startActivity(cliente);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}
