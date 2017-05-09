package appocorrencias.com.appocorrencias.ListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getBairroNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getCidadeNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getDataNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getDescricaoNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getRuaNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getTipoNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getUFNr;

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

        String descricao = getDescricaoNr(id);
        String rua = getRuaNr(id);
        String bairro  = getBairroNr(id);
        String uf = getUFNr(id);
        String cidade = getCidadeNr(id);
        String data = getDataNr(id);
        String tipo = getTipoNr(id);



        Tv_Id_Ocorrencia.setText(id);
        Tv_Tipo_Crime.setText(tipo);
        Tv_Data_Ocorrencia.setText(data);
        Tv_Desc_Ocorrencia.setText(descricao);
        Tv_Endereco.setText(rua+ "," + bairro);
        Tv_Bairro.setText(cidade + "," +uf);


    }




}
