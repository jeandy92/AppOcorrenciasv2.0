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
import appocorrencias.com.appocorrencias.ListView.OcorrenciasRegistradas;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.ListView.OcorrenciasRegistradas.criarocorrencias;

public class Listar_Ocorrencias extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listar_ocorrencias);
        lista  = (ListView)findViewById(R.id.lista_ocorrencias_do_usuario);
        TextView Id_Ocorrencia  =(TextView)findViewById(R.id.id_ocorrencia);
        TextView Desc_Ocorrencia  =(TextView)findViewById(R.id.desc_ocorrencia);

        ArrayList<OcorrenciasRegistradas> listadeocorrencias = criarocorrencias();

        AdapterParaOcorrencias adapter = new AdapterParaOcorrencias(listadeocorrencias,this);

        lista.setAdapter(adapter);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        Intent i = new Intent(view.getContext(), Item_Ocorrencia_Registradas.class);
                        String idocorrencia = ((TextView) view.findViewById(R.id.id_ocorrencia)).getText().toString();
                        String descocorrencia = ((TextView) view.findViewById(R.id.desc_ocorrencia)).getText().toString();


                        i.putExtra("id_ocorrencia", "1234");
                        i.putExtra("desc_ocorrencia", "Modelo");

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
}
