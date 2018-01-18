package appocorrencias.com.appocorrencias.ListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import appocorrencias.com.appocorrencias.Adapters.AdapterCustomSwiper;
import appocorrencias.com.appocorrencias.ClassesSA.ProtocoloErlang;
import appocorrencias.com.appocorrencias.R;

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
    private static ProtocoloErlang processa = new ProtocoloErlang();
    public static String idOcorrencia, descricao, rua, bairro, uf, cidade, data, tipo, CPF, Nome, BairroCli, convComentario, CPFOcorrencia, tela, Ip, telaBusca;
    public static int Porta;
    ListView listaComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_item_feed_ocorrencias);


        txtComentario = (EditText) findViewById(R.id.edtComentario);
        TextView Tv_Id_Ocorrencia = (TextView) findViewById(R.id.txtCampoNumeroOcorrencia);
        TextView Tv_Tipo_Crime = (TextView) findViewById(R.id.txtCampoCPF);
        TextView Tv_Data_Ocorrencia = (TextView) findViewById(R.id.txtCampoNascimento);
        TextView Tv_Desc_Ocorrencia = (TextView) findViewById(R.id.txtCampoDescricaoDaOcorrencia);
        TextView Tv_Cidade_UF = (TextView) findViewById(R.id.txtCidadeUFUsuario);
        TextView Tv_Rua_Bairro = (TextView) findViewById(R.id.txtRuaBairroUsuario);
        Button btnExcluir = (Button) findViewById(R.id.btnExcluir);

        Intent intent = getIntent();

        Bundle dados = intent.getExtras();


        idOcorrencia = dados.getString("id_ocorrencia").toString();
        CPF = dados.getString("cpf").toString();
        Nome = dados.getString("nome").toString();
        BairroCli = dados.getString("bairro").toString();
        tela = dados.getString("tela").toString();
        telaBusca = dados.getString("telaBusca").toString();



        descricao = getDescricaoNr(idOcorrencia);
        rua = getRuaNr(idOcorrencia);
        bairro = getBairroNr(idOcorrencia);
        uf = getUFNr(idOcorrencia);
        cidade = getCidadeNr(idOcorrencia);
        data = getDataNr(idOcorrencia);
        tipo = getTipoNr(idOcorrencia);


        Tv_Id_Ocorrencia.setText(idOcorrencia);
        Tv_Tipo_Crime.setText(tipo);
        Tv_Data_Ocorrencia.setText(data);
        Tv_Desc_Ocorrencia.setText(descricao);
        Tv_Rua_Bairro.setText(rua + "," + bairro);
        Tv_Cidade_UF.setText(cidade + ", " + uf);


    }
/*
    public static String evBuscarImagens(String IDOcorrencia, String tipo, String IpServer, int PortaServer) throws IOException {

        String BuscarImagensOcorrencia;

        if (tipo.equals("cpf")) {
            BuscarImagensOcorrencia = "BuscarImagemPerfil " + IDOcorrencia;
        } else {
            BuscarImagensOcorrencia = "BuscarImagensOcorrencia " + IDOcorrencia;
        }

        String str = null;
        OutputStream canalSaida = null;
        InputStream canalEntrada = null;
        Socket cliente2 = new Socket();

        byte[] byteDados = BuscarImagensOcorrencia.getBytes();
        int tamanhoDados = byteDados.length;

        byte[] byteTamanhoDados = toBytes(tamanhoDados);
        byte[] TamanhoEDados = concat(byteTamanhoDados, byteDados);
        int tamanhoPacote = TamanhoEDados.length;
        byte[] byteTamanhoPct = toBytes(tamanhoPacote);
        byte[] byteFinal = concat(byteTamanhoPct, TamanhoEDados);

        int millisecondsTimeOut = 5000;
        InetSocketAddress adress = new InetSocketAddress(IpServer, PortaServer);

        try {
            cliente2.connect(adress, millisecondsTimeOut);
        } catch (Exception e) {
            str = "erro";
            return str;
        }
        try {
            canalSaida = cliente2.getOutputStream();
            canalEntrada = cliente2.getInputStream();
            canalSaida.write(byteFinal);

            if (tipo.equals("cpf")) {
                receber_imagem_perfil(canalEntrada);
            } else {
                receber_imagem(canalEntrada);
            }

            canalSaida.flush();
            canalSaida.close();
            canalEntrada.close();
            cliente2.close();

            str = "true";

            return str;

        } catch (Exception e) {
            //FIXME Tratar a Exception.
            e.printStackTrace();
        }
        return "true";

    }

    public void evExcluirOcorrencia(View view) throws IOException {

        String ExcluirOcorrencia = "ExcluirOcorrencia " + idOcorrencia;
        String retornoExclusao = processa.primeiroCadastroNoServidor(ExcluirOcorrencia, Ip, Porta);

        if (tela.equals("Adm")){
            Intent buscarOcorrencia = new Intent(this, BuscaOcorrencias.class);
            Bundle bundle = new Bundle();
            bundle.putString("nome", "Administrador");
            bundle.putString("cpf", "33333333333");
            bundle.putString("bairro", "Adm");
            bundle.putString("tela" , "Adm");
            bundle.putString("telaBusca", "Busca");
            bundle.putString("ip", Ip);
            bundle.putInt("porta", Porta);

            buscarOcorrencia.putExtras(bundle);
            this.startActivity(buscarOcorrencia);

            this.finish();

        }else {

            if (retornoExclusao.equals("true")) {

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
                    bundle.putString("bairro", BairroCli);
                    bundle.putString("ip", Ip);
                    bundle.putInt("porta", Porta);

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

                    Intent cliente = new Intent(this, ListaOcorrencias.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("nome", Nome);
                    bundle.putString("cpf", CPF);
                    bundle.putString("bairro", BairroCli);
                    bundle.putString("ip", Ip);
                    bundle.putInt("porta", Porta);

                    cliente.putExtras(bundle);
                    this.startActivity(cliente);
                }
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
            String BuscaId = "IDcomentario teste";
            String IDserver = processa.primeiroCadastroNoServidor(BuscaId, Ip, Porta);

            String ArrayNome[] = Nome.split(" ");
            String Apelido = ArrayNome[0];

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

            String retorno = processa.cadastrar_Comentario(IDserver, idOcorrencia, CPF, Data, Hora, Apelido, convComentario, Ip, Porta);

            txtComentario.setText(null);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            if (retorno.equals("erro")) {
                Toast.makeText(this, "Erro na Conexão com o Servidor", Toast.LENGTH_SHORT).show();
            } else {
                if (retorno.equals("true")) {
                    Toast.makeText(this, "Comentário Salvo com sucesso", Toast.LENGTH_SHORT).show();

                    deleteAllArrayComentarios();
                    evBuscarComentario(idOcorrencia, Ip, Porta);

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


    public static String evBuscarComentario(String IDOcorrencia, String IpServer, int PortaServer) throws IOException {

        String BuscarComentariosRegistrados = "BuscarComentariosRegistrados " + IDOcorrencia;

        ArrayImagensPerfilComentarios.deleteBitmap();
        String retorno = ProtocoloErlang.buscarDadosImagensServer(BuscarComentariosRegistrados, IpServer, PortaServer);

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


    public void evOcorrenciasInformadasItem(String CPF, String Ip, int Porta) throws IOException {

        deleteAllArray();

        String BuscarOcorrenciasRegistradas = "BuscarOcorrenciasRegistradas" + " " + CPF;

        ArrayImagensPerfilComentarios.deleteBitmap();
        String retorno = ProtocoloErlang.buscarDadosImagensServer(BuscarOcorrenciasRegistradas, Ip, Porta);

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
*/
    public void onBackPressed() {
        super.onBackPressed();

       /* ArrayImagens.deleteBitmap();

        if (tela.equals("ListarOcorrencia")) {
            Intent cliente = new Intent(this, ListaOcorrencias.class);

            deleteAllArray();
            try {
                evOcorrenciasInformadasItem(CPF, Ip, Porta);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle();
            bundle.putString("nome", Nome);
            bundle.putString("cpf", CPF);
            bundle.putString("bairro", BairroCli);
            bundle.putString("ip", Ip);
            bundle.putInt("porta", Porta);

            cliente.putExtras(bundle);
            this.startActivity(cliente);
            this.finish();

        } else {
            if (tela.equals("Cliente") && (telaBusca.equals("Busca") == false)) {

                deleteAllArray();

                try {
                    evBuscarOcorrenciasBairro(BairroCli, Ip, Porta);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent cliente = new Intent(this, Cliente.class);

                Bundle bundle = new Bundle();
                bundle.putString("nome", Nome);
                bundle.putString("cpf", CPF);
                bundle.putString("bairro", BairroCli);
                bundle.putString("ip", Ip);
                bundle.putInt("porta", Porta);

                cliente.putExtras(bundle);
                this.startActivity(cliente);
                this.finish();

            } else {
                if (tela.equals("Cliente") && telaBusca.equals("Busca")) {
                    Intent cliente = new Intent(this, BuscaOcorrencias.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("nome", Nome);
                    bundle.putString("cpf", CPF);
                    bundle.putString("bairro", BairroCli);
                    bundle.putString("tela", tela);
                    bundle.putString("telaBusca", telaBusca);
                    bundle.putString("ip", Ip);
                    bundle.putInt("porta", Porta);

                    cliente.putExtras(bundle);
                    this.startActivity(cliente);
                    this.finish();
                } else {
                    if (tela.equals("Adm") && telaBusca.equals("Busca")) {
                        Intent cliente = new Intent(this, BuscaOcorrencias.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("nome", Nome);
                        bundle.putString("cpf", CPF);
                        bundle.putString("bairro", BairroCli);
                        bundle.putString("tela", tela);
                        bundle.putString("telaBusca", telaBusca);
                        bundle.putString("ip", Ip);
                        bundle.putInt("porta", Porta);

                        cliente.putExtras(bundle);
                        this.startActivity(cliente);
                        this.finish();
                    }
                }
            }
        }*/
    }
}
