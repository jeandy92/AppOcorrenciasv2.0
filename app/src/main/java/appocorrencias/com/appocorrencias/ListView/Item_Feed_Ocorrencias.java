package appocorrencias.com.appocorrencias.ListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import appocorrencias.com.appocorrencias.Adapters.CustomSwiperAdapter;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getBairroNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getCidadeNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getDataNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getDescricaoNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getRuaNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getTipoNr;
import static appocorrencias.com.appocorrencias.ListView.ArrayOcorrenciasRegistradas.getUFNr;

public class Item_Feed_Ocorrencias extends AppCompatActivity {

    ViewPager viewPager;
    CustomSwiperAdapter customSwiperAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_item_feed_ocorrencias);




        TextView Tv_Id_Ocorrencia    =  (TextView) findViewById(R.id.txtCampoNumeroOcorrencia);
        TextView Tv_Tipo_Crime       =  (TextView) findViewById(R.id.txtCampoTipoOcorrencia);
        TextView Tv_Data_Ocorrencia  =  (TextView) findViewById(R.id.txtCampoDataOcorrencia);
        TextView Tv_Desc_Ocorrencia =  (TextView) findViewById(R.id.txtCampoDescricaoDaOcorrencia);
        TextView Tv_Cidade_UF  =  (TextView) findViewById(R.id.txtCidadeUFOcorrencia);
        TextView Tv_Rua_Bairro  =  (TextView) findViewById(R.id.txtRuaBairroOcorrencia);

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

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        int[] image_resources = null;
        if (tipo.equals(" Roubo")) {
            image_resources = new int[]{R.drawable.ic_assalto, R.drawable.roubo};
        } else if (tipo.equals(" Furto")) {
            image_resources = new int[]{R.drawable.ic_furto, R.drawable.roubo};
        } else if (tipo.equals(" Trafico de drogas")) {
            image_resources = new int[]{R.drawable.ic_trafico, R.drawable.roubo};
        }else if (tipo.equals(" Homicidio")) {
            image_resources = new int[]{R.drawable.ic_homicidio, R.drawable.roubo};
        }else if (tipo.equals(" Latrocinio")) {
            image_resources = new int[]{R.drawable.ic_latrocinio, R.drawable.roubo};
        }else if (tipo.equals(" Abuso Sexual")) {
            image_resources = new int[]{R.drawable.ic_abuso, R.drawable.roubo};
        }

        customSwiperAdapter = new CustomSwiperAdapter(this,image_resources);
        viewPager.setAdapter(customSwiperAdapter);

        Tv_Id_Ocorrencia.setText(id);
        Tv_Tipo_Crime.setText(tipo);
        Tv_Data_Ocorrencia.setText(data);
        Tv_Desc_Ocorrencia.setText(descricao);
        Tv_Rua_Bairro.setText(rua+ "," + bairro);
        Tv_Cidade_UF.setText(cidade + ", " +uf);

    }

}
