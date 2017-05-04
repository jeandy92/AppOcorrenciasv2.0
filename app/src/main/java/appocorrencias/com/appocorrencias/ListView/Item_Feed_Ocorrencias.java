package appocorrencias.com.appocorrencias.ListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import appocorrencias.com.appocorrencias.R;

public class Item_Feed_Ocorrencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_item_feed_ocorrencias);

        TextView Edt_Id_Ocorrencia  =  (TextView) findViewById(R.id.edt_id_ocorrencia);
        TextView Edt_Desc_Ocorrencia  =  (TextView) findViewById(R.id.edt_desc_ocorrencia);
        TextView Edt_Tipo_Crime  =  (TextView) findViewById(R.id.edt_tipo_crime);

        Intent intent = getIntent();

        Bundle dados = intent.getExtras();

        String id     = dados.getString("id_ocorrencia").toString();
        String descricao         = dados.getString("desc_ocorrencia").toString();
        String tipocrime         = dados.getString("tipocrime").toString();



        Edt_Id_Ocorrencia.setText(id);
        Edt_Desc_Ocorrencia.setText(descricao);
        Edt_Tipo_Crime.setText(tipocrime);





    }




}
