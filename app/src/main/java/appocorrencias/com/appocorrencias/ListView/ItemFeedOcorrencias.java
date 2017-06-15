package appocorrencias.com.appocorrencias.ListView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
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
import static appocorrencias.com.appocorrencias.Activitys.Login.evBuscarOcorrenciasBairro;
import static appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket.concat;
import static appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket.receberImagem;
import static appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket.receberImagemPerfil;
import static appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket.toBytes;
import static appocorrencias.com.appocorrencias.ListView.ArrayComentariosRegistrados.deleteAllArrayComentarios;
import static appocorrencias.com.appocorrencias.ListView.ArrayComentariosRegistrados.getListaComentarios;
import static appocorrencias.com.appocorrencias.ListView.ArrayImagens.getImagens;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.deleteAllArray;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getBairroNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getCidadeNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getDataNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getDescricaoNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getRuaNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getTipoNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getUFNr;

public class ItemFeedOcorrencias extends AppCompatActivity {

    private EditText edtComentario;
    ViewPager viewPager;
    AdapterCustomSwiper adapterCustomSwiper;
    private static ProcessaSocket processa = new ProcessaSocket();
    String idOcorrencia, descricao, rua, bairro, uf, cidade, data, tipo, cpf, nome, bairroCli, convComentario, cpfOcorrencia, tela;
    ListView listaComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_item_feed_ocorrencias);


        edtComentario = (EditText) findViewById(R.id.edtComentario);
        TextView tvIdOcorrencia = (TextView) findViewById(R.id.txtCampoNumeroOcorrencia);
        TextView tvTipoCrime = (TextView) findViewById(R.id.txtCampoCPF);
        TextView tvDataOcorrencia = (TextView) findViewById(R.id.txtCampoNascimento);
        TextView tvDescOcorrencia = (TextView) findViewById(R.id.txtCampoDescricaoDaOcorrencia);
        TextView tvCidadeUF = (TextView) findViewById(R.id.txtCidadeUFUsuario);
        TextView tvRuaBairro = (TextView) findViewById(R.id.txtRuaBairroUsuario);
        Button btnExcluir = (Button) findViewById(R.id.btnExcluir);
        ImageButton btnDelComent = (ImageButton) findViewById(R.id.btnDelComent);

        Intent intent = getIntent();

        Bundle dados = intent.getExtras();


        idOcorrencia   = dados.getString("id_ocorrencia").toString();
        cpf            = dados.getString("cpf").toString();
        nome           = dados.getString("nome").toString();
        bairroCli      = dados.getString("bairro").toString();
        tela           = dados.getString("tela").toString();
        descricao     = getDescricaoNr(idOcorrencia);
        rua           = getRuaNr(idOcorrencia);
        bairro        = getBairroNr(idOcorrencia);
        uf            = getUFNr(idOcorrencia);
        cidade        = getCidadeNr(idOcorrencia);
        data          = getDataNr(idOcorrencia);
        tipo          = getTipoNr(idOcorrencia);
        cpfOcorrencia = ArrayOcorrenciasRegistradas.getCPFNr(idOcorrencia);

        if (cpfOcorrencia.equals(cpf)) {
            btnExcluir.setVisibility(View.VISIBLE);
        }

        tvIdOcorrencia.setText(idOcorrencia);
        tvTipoCrime.setText(tipo);
        tvDataOcorrencia.setText(data);
        tvDescOcorrencia.setText(descricao);
        tvRuaBairro.setText(rua + "," + bairro);
        tvCidadeUF.setText(cidade + ", " + uf);

        ArrayList<DadosComentarios> listaComentarios = getListaComentarios();
        Collections.sort(listaComentarios);
        AdapterComentarios adapter = new AdapterComentarios(this, listaComentarios);

        this.listaComentarios = (ListView) findViewById(R.id.list_comentarios);
        this.listaComentarios.setAdapter(adapter);


        this.listaComentarios.setOnTouchListener(new ListView.OnTouchListener() {
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

        ArrayList<Bitmap> listaImagens = getImagens();
        Bitmap[] images = new Bitmap[listaImagens.size()];

        if (listaImagens.size() > 0) {

            for (int i = 0; i < listaImagens.size(); i++) {
                images[i] = listaImagens.get(i);
            }
        }
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapterCustomSwiper = new AdapterCustomSwiper(this, images);
        viewPager.setAdapter(adapterCustomSwiper);
    }

    public static String evBuscarImagens(String IDOcorrencia, String tipo) throws IOException {

        String buscarImagensOcorrencia;

        if (tipo.equals("cpf")) {
            buscarImagensOcorrencia = "BuscarImagemPerfil " + IDOcorrencia;
        } else {
            buscarImagensOcorrencia = "BuscarImagensOcorrencia " + IDOcorrencia;
        }

        String ip_conexao = /*"192.168.0.108";//*/ "52.34.140.131"; //"172.20.10.3";
        int porta_conexao = 63200;//2222;


        String          str            = null;
        OutputStream   canalSaida      = null;
        InputStream    canalEntrada    = null;
        Socket         segundoCliente  = new Socket();

        byte[] byteDados = buscarImagensOcorrencia.getBytes();
        int tamanhoDados = byteDados.length;

        byte[] byteTamanhoDados = toBytes(tamanhoDados);
        byte[] tamanhoEDados = concat(byteTamanhoDados, byteDados);
           int tamanhoPacote = tamanhoEDados.length;
        byte[] byteTamanhoPct = toBytes(tamanhoPacote);
        byte[] byteFinal = concat(byteTamanhoPct, tamanhoEDados);

        int millisecondsTimeOut = 5000;
        InetSocketAddress adress = new InetSocketAddress(ip_conexao, porta_conexao);

        try {
            segundoCliente.connect(adress, millisecondsTimeOut);
        } catch (Exception e) {
            str = "erro";
            return str;
        }
        try {
            canalSaida = segundoCliente.getOutputStream();
            canalEntrada = segundoCliente.getInputStream();
            canalSaida.write(byteFinal);

            if (tipo.equals("cpf")) {
                receberImagemPerfil(canalEntrada);
            } else {
                receberImagem(canalEntrada);
            }

            canalSaida.flush();
            canalSaida.close();
            canalEntrada.close();
            segundoCliente.close();

            str = "true";

            return str;

        } catch (Exception e) {
            //FIXME Tratar a Exception.
            e.printStackTrace();
        }
        return "true";

    }

    public void evExcluirOcorrencia(View view) throws IOException {

        String excluirOcorrencia = "ExcluirOcorrencia " + idOcorrencia;
        String retornoExclusao = processa.primeiroCadastroNoServidor(excluirOcorrencia);

        if (retornoExclusao.equals("true")) {

            deleteAllArray();

            String BuscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradas" + " " + cpf;

            Toast.makeText(this, "Minhas Ocorrencias Registradas ", Toast.LENGTH_SHORT).show();

            ArrayImagensPerfilComentarios.deleteBitmap();
            String retorno = ProcessaSocket.buscarDadosImagensServer(BuscarOcorrenciasRegistradas);

            if (retorno.equals("false")) {
                Toast.makeText(this, "Não há ocorrencias cadastradas", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_listar_ocorrencias);
                Intent cliente = new Intent(this, ListarOcorrencias.class);
                Bundle bundle = new Bundle();
                bundle.putString("nome", nome);
                bundle.putString("cpf", cpf);
                bundle.putString("bairro", bairroCli);

                cliente.putExtras(bundle);
                this.startActivity(cliente);

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
                bundle.putString("nome", nome);
                bundle.putString("cpf", cpf);
                bundle.putString("bairro", bairroCli);

                cliente.putExtras(bundle);
                this.startActivity(cliente);
            }
        }

    }

    public void evEnviarComentario(View view) throws IOException {

        String Comentario = edtComentario.getText().toString();

        if (Comentario.isEmpty()) {
            edtComentario.setError("Escreva um comentario ");
            edtComentario.setFocusable(true);
            edtComentario.requestFocus();
        } else {

            Random random = new Random();
            int x = random.nextInt(101);
            String NrAleatorio = Integer.toString(x);
            String BuscaId = "IDcomentario teste";
            String IDserver = processa.primeiroCadastroNoServidor(BuscaId);
            //String IDComentario = IDserver + NrAleatorio;


            String ArrayNome[] = nome.split(" ");
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


            String retorno = processa.cadastrarComentario(IDserver, idOcorrencia, cpf, Data, Hora, Apelido, convComentario);


            edtComentario.setText(null);

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


    public static String evBuscarComentario(String pIdOcorrencia) throws IOException {

        String BuscarComentariosRegistrados = "BuscarComentariosRegistrados " + pIdOcorrencia;

        ArrayImagensPerfilComentarios.deleteBitmap();
        String retorno = ProcessaSocket.buscarDadosImagensServer(BuscarComentariosRegistrados);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            if (retorno.equals("false")) {
                return "false";

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
        return "true";
    }


    public void evOcorrenciasInformadasItem(String pCpf) throws IOException {

        deleteAllArray();

        String BuscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradas" + " " + pCpf;

        ArrayImagensPerfilComentarios.deleteBitmap();
        String retorno = ProcessaSocket.buscarDadosImagensServer(BuscarOcorrenciasRegistradas);

        if (retorno.equals("false")) {

            Toast.makeText(this, "Não há ocorrencias cadastradas", Toast.LENGTH_SHORT).show();

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

        }
    }


    public void onBackPressed() {
        super.onBackPressed();

        ArrayImagens.deleteBitmap();

        if (tela.equals("ListarOcorrencia")) {
            Intent cliente = new Intent(this, ListarOcorrencias.class);

            deleteAllArray();
            try {
                evOcorrenciasInformadasItem(cpf);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle();
            bundle.putString("nome", nome);
            bundle.putString("cpf", cpf);
            bundle.putString("bairro", bairroCli);

            cliente.putExtras(bundle);
            this.startActivity(cliente);
            this.finish();

        } else {
            if (tela.equals("Cliente")) {

                deleteAllArray();

                try {
                    evBuscarOcorrenciasBairro(bairroCli);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent cliente = new Intent(this, Cliente.class);

                Bundle bundle = new Bundle();
                bundle.putString("nome", nome);
                bundle.putString("cpf", cpf);
                bundle.putString("bairro", bairroCli);

                cliente.putExtras(bundle);
                this.startActivity(cliente);
                this.finish();

            } else {
                Intent cliente = new Intent(this, BuscarOcorrencias.class);

                Bundle bundle = new Bundle();
                bundle.putString("nome", nome);
                bundle.putString("cpf", cpf);
                bundle.putString("bairro", bairroCli);
                bundle.putString("tela", tela);

                cliente.putExtras(bundle);
                this.startActivity(cliente);
                this.finish();


            }
        }
    }

}
