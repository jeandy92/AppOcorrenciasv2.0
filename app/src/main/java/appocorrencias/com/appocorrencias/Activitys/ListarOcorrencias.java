package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
    public static String loginNome, loginCpf, loginBairro, Ip;
    public static int Porta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listar_ocorrencias);

        //Pegando valores que vem do Login
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        loginNome     = bundle.getString("nome");
        loginCpf      = bundle.getString("cpf");
        loginBairro   = bundle.getString("bairro");
        Ip = bundle.getString("ip");
        Porta = bundle.getInt("porta");


        lista = (ListView) findViewById(R.id.lista_ocorrencias_do_usuario);

        ArrayList<DadosOcorrencias> listaDeOcorrencias = getListaOcorrencia();

        AdapterOcorrencias adapter = new AdapterOcorrencias(this, listaDeOcorrencias);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {

                    Intent i = new Intent(view.getContext(), ItemFeedOcorrencias.class);

                    String idocorrencia = ((TextView) view.findViewById(R.id.txt_id_ocorrencia)).getText().toString();
                    String tela = "ListarOcorrencia";

                    i.putExtra("cpf", loginCpf);
                    i.putExtra("nome", loginNome);
                    i.putExtra("bairro", loginBairro);
                    i.putExtra("id_ocorrencia", idocorrencia);
                    i.putExtra("tela", tela);
                    i.putExtra("telaBusca", "Busca");
                    i.putExtra("ip", Ip);
                    i.putExtra("porta", Porta);

                    deleteAllArrayComentarios();

                    String retornoImagem = null;
                    try {
                        retornoImagem = evBuscarImagens(idocorrencia, "ocorrencia", Ip, Porta);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (retornoImagem.equals("true") || retornoImagem.equals("false")) {
                        String retornoComent = null;
                        try {
                            retornoComent = evBuscarComentario(idocorrencia, Ip, Porta);

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

        lista.setOnTouchListener(new ListView.OnTouchListener() {
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        deleteAllArray();

        try {
            evBuscarOcorrenciasBairro(loginBairro, Ip, Porta);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent cliente = new Intent(this, Cliente.class);

        Bundle bundle = new Bundle();
        bundle.putString("nome", loginNome);
        bundle.putString("cpf", loginCpf);
        bundle.putString("bairro", loginBairro);
        bundle.putString("ip", Ip);
        bundle.putInt("porta", Porta);

        cliente.putExtras(bundle);
        this.startActivity(cliente);
        this.finish();
    }
}
