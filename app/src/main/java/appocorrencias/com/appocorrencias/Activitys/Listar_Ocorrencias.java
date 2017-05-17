package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Adapters.AdapterParaOcorrencias;
import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
import appocorrencias.com.appocorrencias.ListView.Item_Feed_Ocorrencias;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.Activitys.Login.evBuscarOcorrenciasBairro;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.deleteAllArray;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getBairroNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getCidadeNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getDataNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getDescricaoNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getListaOcorrencia;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getRuaNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getTipoNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getUFNr;


public class Listar_Ocorrencias extends AppCompatActivity {

    private ListView lista;
    static String Nome, CPF, Bairro;

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

        AdapterParaOcorrencias adapter = new AdapterParaOcorrencias(this, listadeocorrencias);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0){

                    Intent i = new Intent(view.getContext(), Item_Feed_Ocorrencias.class);

                    String idocorrencia = ((TextView) view.findViewById(R.id.txIdOcorrencia)).getText().toString();
                   // String descocorrencia = ((TextView) view.findViewById(R.id.desc_ocorrencia)).getText().toString();

                    i.putExtra("id_ocorrencia", idocorrencia);

                    startActivity(i);

                    Toast.makeText(view.getContext(), " case 1 Exibir Sobre", Toast.LENGTH_SHORT).show();

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
        setContentView(R.layout.activity_cliente);
        Intent cliente = new Intent(this, Cliente.class);

        Bundle bundle = new Bundle();
        bundle.putString("nome", Nome);
        bundle.putString("cpf" , CPF);
        bundle.putString("bairro" , Bairro);

        cliente.putExtras(bundle);
        this.startActivity(cliente);

    }


}
