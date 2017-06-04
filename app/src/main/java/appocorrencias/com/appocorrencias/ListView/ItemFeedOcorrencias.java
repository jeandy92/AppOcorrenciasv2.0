package appocorrencias.com.appocorrencias.ListView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import appocorrencias.com.appocorrencias.Activitys.BuscarOcorrencias;
import appocorrencias.com.appocorrencias.Activitys.Cliente;
import appocorrencias.com.appocorrencias.Activitys.ListarOcorrencias;
import appocorrencias.com.appocorrencias.Adapters.AdapterComentarios;
import appocorrencias.com.appocorrencias.Adapters.AdapterCustomSwiper;
import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.Activitys.CadastrarOcorrencia.removerAcentos;
import static appocorrencias.com.appocorrencias.ListView.ArrayComentariosRegistrados.deleteAllArrayComentarios;
import static appocorrencias.com.appocorrencias.ListView.ArrayComentariosRegistrados.getListaComentarios;
import static appocorrencias.com.appocorrencias.ListView.ArrayImagens.deleteAllArrayImagens;
import static appocorrencias.com.appocorrencias.ListView.ArrayImagens.getListaImagens;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.deleteAllArray;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getBairroNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getCidadeNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getDataNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getDescricaoNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getRuaNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getTipoNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getUFNr;

public class ItemFeedOcorrencias extends AppCompatActivity {
    private EditText txtComentario;
    ViewPager viewPager;
    AdapterCustomSwiper adapterCustomSwiper;
    private static ProcessaSocket processa = new ProcessaSocket();
    String idOcorrencia, descricao, rua, bairro, uf, cidade, data, tipo, CPF, Nome, BairroCli, convComentario, CPFOcorrencia, tela;
    ListView listaComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_item_feed_ocorrencias);

        txtComentario = (EditText) findViewById(R.id.edtComentario);
        TextView Tv_Id_Ocorrencia = (TextView) findViewById(R.id.txtCampoNumeroOcorrencia);
        TextView Tv_Tipo_Crime = (TextView) findViewById(R.id.txtCampoTipoOcorrencia);
        TextView Tv_Data_Ocorrencia = (TextView) findViewById(R.id.txtCampoDataOcorrencia);
        TextView Tv_Desc_Ocorrencia = (TextView) findViewById(R.id.txtCampoDescricaoDaOcorrencia);
        TextView Tv_Cidade_UF = (TextView) findViewById(R.id.txtCidadeUFOcorrencia);
        TextView Tv_Rua_Bairro = (TextView) findViewById(R.id.txtRuaBairroOcorrencia);
        Button btnExcluir = (Button) findViewById(R.id.btnExcluir);
        ImageButton btnDelComent = (ImageButton) findViewById(R.id.btnDelComent);

        Intent intent = getIntent();

        Bundle dados = intent.getExtras();


        idOcorrencia = dados.getString("id_ocorrencia").toString();
        CPF = dados.getString("cpf").toString();
        Nome = dados.getString("nome").toString();
        BairroCli = dados.getString("bairro").toString();
        tela = dados.getString("tela").toString();


        descricao = getDescricaoNr(idOcorrencia);
        rua = getRuaNr(idOcorrencia);
        bairro = getBairroNr(idOcorrencia);
        uf = getUFNr(idOcorrencia);
        cidade = getCidadeNr(idOcorrencia);
        data = getDataNr(idOcorrencia);
        tipo = getTipoNr(idOcorrencia);
        CPFOcorrencia = ArrayOcorrenciasRegistradas.getCPFNr(idOcorrencia);

        ArrayList<Bitmap> listaImagens = getListaImagens();


        if (CPFOcorrencia.equals(CPF)) {
            btnExcluir.setVisibility(View.VISIBLE);
        }

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        int[] image_resources = null;
        if (tipo.equals(" Roubo")) {
            image_resources = new int[]{R.drawable.ic_assalto, R.drawable.roubo};
        } else if (tipo.equals(" Furto")) {
            image_resources = new int[]{R.drawable.ic_furto, R.drawable.roubo};
        } else if (tipo.equals(" Trafico de drogas")) {
            image_resources = new int[]{R.drawable.ic_trafico, R.drawable.roubo};
        } else if (tipo.equals(" Homicidio")) {
            image_resources = new int[]{R.drawable.ic_homicidio, R.drawable.roubo};
        } else if (tipo.equals(" Latrocinio")) {
            image_resources = new int[]{R.drawable.ic_latrocinio, R.drawable.roubo};
        } else if (tipo.equals(" Abuso Sexual")) {
            image_resources = new int[]{R.drawable.ic_abuso, R.drawable.roubo};
        }

        adapterCustomSwiper = new AdapterCustomSwiper(this, image_resources, listaImagens);
        viewPager.setAdapter(adapterCustomSwiper);

        Tv_Id_Ocorrencia.setText(idOcorrencia);
        Tv_Tipo_Crime.setText(tipo);
        Tv_Data_Ocorrencia.setText(data);
        Tv_Desc_Ocorrencia.setText(descricao);
        Tv_Rua_Bairro.setText(rua + "," + bairro);
        Tv_Cidade_UF.setText(cidade + ", " + uf);

        ArrayList<DadosComentarios> listadecomentarios = getListaComentarios();
        Collections.sort(listadecomentarios);
        AdapterComentarios adapter = new AdapterComentarios(this, listadecomentarios);

        listaComentarios = (ListView) findViewById(R.id.list_comentarios);
        listaComentarios.setAdapter(adapter);


        listaComentarios.setOnTouchListener(new ListView.OnTouchListener() {
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

    public void evExcluirOcorrencia(View view) throws IOException {

        String ExcluirOcorrencia = "ExcluirOcorrencia " + idOcorrencia;
        String retornoExclusao = processa.cadastrar1_no_server(ExcluirOcorrencia);

        if (retornoExclusao.equals("true")) {

            deleteAllArray();

            String BuscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradas" + " " + CPF;


            Toast.makeText(this, "Minhas Ocorrencias Registradas ", Toast.LENGTH_SHORT).show();

            String retorno = processa.cadastrar1_no_server(BuscarOcorrenciasRegistradas);

            if (retorno.equals("false")) {
                Toast.makeText(this, "Não há ocorrencias cadastradas", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_listar_ocorrencias);
                Intent cliente = new Intent(this, ListarOcorrencias.class);
                Bundle bundle = new Bundle();
                bundle.putString("nome", Nome);
                bundle.putString("cpf", CPF);
                bundle.putString("bairro", BairroCli);

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
                bundle.putString("bairro", BairroCli);

                cliente.putExtras(bundle);
                this.startActivity(cliente);
            }
        }

    }

    public void evEnviarComentario(View view) throws IOException {

        String Comentario = txtComentario.getText().toString();

        if (Comentario.isEmpty()) {
            txtComentario.setError("Escreva um comentario ");
            txtComentario.setFocusable(true);
            txtComentario.requestFocus();
        } else {

            Random random = new Random();
            int x = random.nextInt(101);
            String NrAleatorio = Integer.toString(x);
            String BuscaId = "IDcomentario teste";
            String IDserver = processa.cadastrar1_no_server(BuscaId);
            //String IDComentario = IDserver + NrAleatorio;


            String ArrayNome[] = Nome.split(" ");
            String Apelido = ArrayNome[1];

            convComentario = removerAcentos(Comentario);

            Date data = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            String Data = formatador.format(data);

            GregorianCalendar dtI = new GregorianCalendar(TimeZone.getTimeZone("GMT-3"), new Locale("pt_BR"));
            Date data2 = dtI.getTime();
            data2.setHours(data.getHours() - 3);
            dtI.setTime(data2);
            SimpleDateFormat hora2 = new SimpleDateFormat("HH:mm:h");
            String Hora = hora2.format(dtI.getTime());


            String retorno = processa.cadastrar_Comentario(IDserver, idOcorrencia, CPF, Data, Hora, Apelido, convComentario);


            txtComentario.setText(null);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            if (retorno.equals("erro")) {
                Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();
            } else {
                if (retorno.equals("true")) {
                    Toast.makeText(this, "Comantario Salvo com sucesso", Toast.LENGTH_SHORT).show();

                    deleteAllArrayComentarios();
                    evBuscarComentario(idOcorrencia);

                    listaComentarios = (ListView) findViewById(R.id.list_comentarios);
                    ArrayList<DadosComentarios> listadecomentarios = getListaComentarios();
                    Collections.sort(listadecomentarios);
                    AdapterComentarios adapter = new AdapterComentarios(this, listadecomentarios);
                    listaComentarios.setAdapter(adapter);

                } else {
                }
            }
        }

    }


    public static void evBuscarComentario(String IDOcorrencia) throws IOException {

        String BuscarComentariosRegistrados = "BuscarComentariosRegistrados " + IDOcorrencia;

        String retorno = processa.cadastrar1_no_server(BuscarComentariosRegistrados);

        if (retorno.equals("false")) {

        } else {
            // Pegando quantidade de Ocorrencias
            int qtdComentario = ArrayComentariosRegistrados.getQuantidadeComentario(retorno);

            // Pegando dados e Adicioanando dados no Array
            for (int i = 0; i < qtdComentario; i++) {
                String TodosComentarios[] = retorno.split("///");

                String Comentario = TodosComentarios[i];
                String ComentarioUm[] = Comentario.split("//");
                String IdComentario = ComentarioUm[1];
                String IdOcorrencia = ComentarioUm[2];
                String CPFComentario = ComentarioUm[3];
                String DataComentario = ComentarioUm[4];
                String HoraComentario = ComentarioUm[5];
                String ApelidoComentario = ComentarioUm[6];
                String DescricaoComentario = ComentarioUm[7];

                DadosComentarios dado = new DadosComentarios(IdComentario, IdOcorrencia, CPFComentario, DataComentario,
                        HoraComentario, ApelidoComentario, DescricaoComentario);

                ArrayComentariosRegistrados.adicionar(dado);


            }
        }
    }


    public void onBackPressed() {
        super.onBackPressed();

        deleteAllArrayImagens();

        if (tela.equals("ListarOcorrencia")) {
            Intent cliente = new Intent(this, ListarOcorrencias.class);

            Bundle bundle = new Bundle();
            bundle.putString("nome", Nome);
            bundle.putString("cpf", CPF);
            bundle.putString("bairro", BairroCli);

            cliente.putExtras(bundle);
            this.startActivity(cliente);

        } else {
            if (tela.equals("Cliente")) {

                Intent cliente = new Intent(this, Cliente.class);

                Bundle bundle = new Bundle();
                bundle.putString("nome", Nome);
                bundle.putString("cpf", CPF);
                bundle.putString("bairro", BairroCli);

                cliente.putExtras(bundle);
                this.startActivity(cliente);
            } else {
                Intent cliente = new Intent(this, BuscarOcorrencias.class);

                Bundle bundle = new Bundle();
                bundle.putString("nome", Nome);
                bundle.putString("cpf", CPF);
                bundle.putString("bairro", BairroCli);

                cliente.putExtras(bundle);
                this.startActivity(cliente);

            }
        }
    }

}
