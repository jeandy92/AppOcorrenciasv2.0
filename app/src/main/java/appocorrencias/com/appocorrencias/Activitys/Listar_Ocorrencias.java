package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Adapters.AdapterParaOcorrencias;
import appocorrencias.com.appocorrencias.ListView.Item_Ocorrencia_Registradas;
import appocorrencias.com.appocorrencias.ListView.Lista_Ocorrencias_Registradas;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.ListView.Lista_Ocorrencias_Registradas.criarocorrencias;

public class Listar_Ocorrencias extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listar_ocorrencias);

        lista = (ListView) findViewById(R.id.lista_ocorrencias_do_usuario);

        ArrayList<Lista_Ocorrencias_Registradas> listadeocorrencias = criarocorrencias();

        AdapterParaOcorrencias adapter = new AdapterParaOcorrencias(this, listadeocorrencias);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        Intent i = new Intent(view.getContext(), Item_Ocorrencia_Registradas.class);
                        String idocorrencia = ((TextView) view.findViewById(R.id.id_ocorrencia)).getText().toString();
                        String descocorrencia = ((TextView) view.findViewById(R.id.desc_ocorrencia)).getText().toString();


                        i.putExtra("id_ocorrencia", idocorrencia);
                        i.putExtra("desc_ocorrencia", descocorrencia);

                        startActivity(i);

                        Toast.makeText(view.getContext(), " case 1 Exibir Sobre", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(view.getContext(), " case 2 Exibir Sobre", Toast.LENGTH_SHORT).show();

                        break;
                    case 2:
                        Toast.makeText(view.getContext(), " case 3Exibir Sobre", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        break;
                }
            }
        });

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setContentView(R.layout.activity_cliente);
        this.startActivity(new Intent(this,Cliente.class));
    }
}
