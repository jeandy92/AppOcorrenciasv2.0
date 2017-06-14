package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Adapters.AdapterOcorrencias;
import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
import appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.Activitys.Login.evBuscarOcorrenciasBairro;
import static appocorrencias.com.appocorrencias.ListView.ArrayComentariosRegistrados.deleteAllArrayComentarios;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.deleteAllArray;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getListaOcorrencia;
import static appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias.evBuscarComentario;
import static appocorrencias.com.appocorrencias.ListView.ItemFeedOcorrencias.evBuscarImagens;


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


                    // String descocorrencia = ((TextView) view.findViewById(R.id.desc_ocorrencia)).getText().toString();
                    String tela = "ListarOcorrencia";

                    i.putExtra("cpf", CPF);
                    i.putExtra("nome", Nome);
                    i.putExtra("bairro", Bairro);
                    i.putExtra("id_ocorrencia", idocorrencia);
                    i.putExtra("tela", tela);


                    deleteAllArrayComentarios();

                    String retornoImagem = null;
                    try {
                        retornoImagem = evBuscarImagens(idocorrencia, "ocorrencia");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (retornoImagem.equals("true") || retornoImagem.equals("false")) {
                        String retornoComent = null;
                        try {
                            retornoComent = evBuscarComentario(idocorrencia);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (retornoComent.equals("true") || retornoComent.equals("false")) {
                            startActivity(i);
                        }
                    }
                }
            }
        });
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

        Intent cliente = new Intent(this, Cliente.class);

        Bundle bundle = new Bundle();
        bundle.putString("nome", Nome);
        bundle.putString("cpf", CPF);
        bundle.putString("bairro", Bairro);

        cliente.putExtras(bundle);
        this.startActivity(cliente);

    }


}
