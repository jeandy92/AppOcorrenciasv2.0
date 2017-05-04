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

        TextView Tv_Id_Ocorrencia    =  (TextView) findViewById(R.id.tv_id_ocorrencia);
        TextView Tv_Tipo_Crime       =  (TextView) findViewById(R.id.tv_tipo_crime);
        TextView Tv_Data_Ocorrencia  =  (TextView) findViewById(R.id.tv_data_ocorrencia);
        TextView Tv_Desc_Ocorrencia =  (TextView) findViewById(R.id.tv_desc_ocorrencia);
        TextView Tv_Endereco  =  (TextView) findViewById(R.id.tv_endereco);
        TextView Tv_Bairro  =  (TextView) findViewById(R.id.tv_bairro);

        Intent intent = getIntent();

        Bundle dados = intent.getExtras();

        String id = dados.getString("id_ocorrencia").toString();
        String tipocrime = dados.getString("tipocrime").toString();
        String descocorrencia = dados.getString("desc_ocorrencia").toString();




        Tv_Id_Ocorrencia.setText(id);
        Tv_Desc_Ocorrencia.setText(descocorrencia);
        Tv_Tipo_Crime.setText(tipocrime);





    }




}
