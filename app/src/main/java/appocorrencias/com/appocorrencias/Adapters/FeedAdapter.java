package appocorrencias.com.appocorrencias.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.ListView.Feed_Ocorrencias;
import appocorrencias.com.appocorrencias.R;

/**
 * Created by Jeanderson on 01/05/2017.
 */

public class FeedAdapter extends BaseAdapter {

    private final ArrayList<Feed_Ocorrencias> feed_ocorrencias;
    private final Activity act;

    public FeedAdapter(Activity act,ArrayList<Feed_Ocorrencias> feedocorrencias){
        this.feed_ocorrencias = feedocorrencias;
        this.act =act;

    }


    @Override
    public int getCount() {
        return feed_ocorrencias.size();
    }

    @Override
    public Object getItem(int position) {
        return feed_ocorrencias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.acitivity_item_feed_ocorrencias,parent,false);
        Feed_Ocorrencias lista_feed_ocorrencias  = feed_ocorrencias.get(position);

        //pegando as referências das Views
        TextView tipodecrime = (TextView) view.findViewById(R.id.txt_tipo_crime);
        TextView descricao = (TextView)view.findViewById(R.id.txt_desc_ocorrencia);
        ImageView imagem = (ImageView)  view.findViewById(R.id.imagem_ocorrencia);
        TextView idocorrencia = (TextView)  view.findViewById(R.id.txt_id_ocorrencia);




        tipodecrime.setText(String.valueOf(lista_feed_ocorrencias.getTipo_crime()));
        descricao.setText(lista_feed_ocorrencias.getDes_ocorrencia());
        idocorrencia.setText(String.valueOf(lista_feed_ocorrencias.getId_ocorrencia()));

        if (lista_feed_ocorrencias.getTipo_crime().equals("Roubo")) {
            imagem.setImageResource(R.drawable.ic_assalto);
        } else if (lista_feed_ocorrencias.getTipo_crime().equals("Furto")) {
            imagem.setImageResource(R.drawable.ic_furto);
        }else if (lista_feed_ocorrencias.getTipo_crime().equals("Homicídio")) {
            imagem.setImageResource(R.drawable.ic_homicidio);
        }else if (lista_feed_ocorrencias.getTipo_crime().equals("Latrocínio")) {
            imagem.setImageResource(R.drawable.ic_furto);
        }else if (lista_feed_ocorrencias.getTipo_crime().equals("Abuso Sexual")) {
            imagem.setImageResource(R.drawable.ic_abuso);
        }

 /*<item>Tráfico de drogas</item>
        <item>Roubo</item>
        <item>Furto</item>
        <item>Homicídio</item>
        <item>Latrocínio</item>
        <item>Abuso Sexual</item>*/

//        imagem.setImageResource(R.drawable.ic_assalto);

        return view;
    }
}