package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Adapters.AdapterOcorrencias;
import appocorrencias.com.appocorrencias.ListView.ArrayImagens;
import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
import appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.Activitys.Login.evBuscarOcorrenciasBairro;
import static appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket.concat;
import static appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket.recebe_dados_img;
import static appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket.receber_imagem;
import static appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket.toBytes;
import static appocorrencias.com.appocorrencias.ListView.ArrayComentariosRegistrados.deleteAllArrayComentarios;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.deleteAllArray;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getListaOcorrencia;
import static appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias.evBuscarComentario;


public class ListarOcorrencias extends AppCompatActivity {

    private ListView lista;
    public String Nome, CPF, Bairro;

    static byte[] imagem;
    static byte[] imagem2;
    static byte[] imagem3;


    static Bitmap img;
    static Bitmap img2;
    static Bitmap img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listar_ocorrencias);

        //Pegando valores que vem do Login
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Nome = bundle.getString("nome");
        CPF = bundle.getString("cpf");
        Bairro = bundle.getString("bairro");


        lista = (ListView) findViewById(R.id.lista_ocorrencias_do_usuario);

        ArrayList<DadosOcorrencias> listadeocorrencias = getListaOcorrencia();

        AdapterOcorrencias adapter = new AdapterOcorrencias(this, listadeocorrencias);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {

                    Intent i = new Intent(view.getContext(), ItemFeedOcorrencias.class);
                    String idocorrencia = ((TextView) view.findViewById(R.id.txt_id_ocorrencia)).getText().toString();

                    deleteAllArrayComentarios();

                    try {
                        evBuscarComentario(idocorrencia);

                        //evBuscarImagens(idocorrencia);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    // String descocorrencia = ((TextView) view.findViewById(R.id.desc_ocorrencia)).getText().toString();
                    String tela = "ListarOcorrencia";

                    i.putExtra("cpf", CPF);
                    i.putExtra("nome", Nome);
                    i.putExtra("bairro", Bairro);
                    i.putExtra("id_ocorrencia", idocorrencia);
                    i.putExtra("tela", tela);


                    startActivity(i);
                    Toast.makeText(view.getContext(), " case 1 Exibir Sobre", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public static void evBuscarImagens(String IDOcorrencia) throws IOException {

        String BuscarImagensOcorrencia = "BuscarImagensOcorrencia " + IDOcorrencia;

        String ip_conexao = "172.20.10.3";
        int porta_conexao = 2222;
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

        int millisecondsTimeOut = 3000;
        InetSocketAddress adress = new InetSocketAddress(ip_conexao, porta_conexao);

        try {
            cliente2.connect(adress, millisecondsTimeOut);
        } catch (Exception e) {
            str = "erro";
        }
        try {
            canalSaida = cliente2.getOutputStream();
            canalEntrada = cliente2.getInputStream();
            canalSaida.write(byteFinal);

            str = recebe_dados_img(canalEntrada);

            if (str.equals("trueImagem")) {
                imagem = receber_imagem(canalEntrada);
                img = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
                ArrayImagens.adicionar(img);

                str = recebe_dados_img(canalEntrada);

                if (str.equals("trueImagem")) {
                    imagem2 = receber_imagem(canalEntrada);
                    img2 = BitmapFactory.decodeByteArray(imagem2, 0, imagem2.length);
                    ArrayImagens.adicionar(img2);

                    str = recebe_dados_img(canalEntrada);

                    if (str.equals("trueImagem")) {
                        imagem3 = receber_imagem(canalEntrada);
                        img3 = BitmapFactory.decodeByteArray(imagem3, 0, imagem3.length);
                        ArrayImagens.adicionar(img3);

                        //str = recebe_dados_img(canalEntrada);

                    }
                }
            }

            str = "true";


            canalSaida.flush();
            canalSaida.close();
            canalEntrada.close();
            cliente2.close();

        } catch (Exception e) {
            //FIXME Tratar a Exception.
            e.printStackTrace();
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        deleteAllArray();

        try {
            evBuscarOcorrenciasBairro(Bairro);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_cliente);
        Intent cliente = new Intent(this, Cliente.class);

        Bundle bundle = new Bundle();
        bundle.putString("nome", Nome);
        bundle.putString("cpf", CPF);
        bundle.putString("bairro", Bairro);

        cliente.putExtras(bundle);
        this.startActivity(cliente);

    }


}
