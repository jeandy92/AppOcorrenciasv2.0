package appocorrencias.com.appocorrencias.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
import appocorrencias.com.appocorrencias.R;

/**
 * Created by Jeanderson on 01/05/2017.
 */

public class AdapterFeed extends BaseAdapter {

    private final ArrayList<DadosOcorrencias> feed_ocorrencias;
    private final Activity act;

    public AdapterFeed(Activity act, ArrayList<DadosOcorrencias> feedocorrencias){
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
        DadosOcorrencias lista_feed_ocorrencias  = feed_ocorrencias.get(position);

        //pegando as referências das Views
        TextView tipodecrime = (TextView) view.findViewById(R.id.tv_bairro);
        TextView apelido = (TextView) view.findViewById(R.id.tv_nome);
        TextView descricao = (TextView)view.findViewById(R.id.txt_desc_comentario);
        TextView endereco = (TextView)view.findViewById(R.id.txEndereco);
        ImageView imagem = (ImageView)  view.findViewById(R.id.imagem_comentario);
        TextView idocorrencia = (TextView)  view.findViewById(R.id.txt_id_ocorrencia);


        String anonimo = lista_feed_ocorrencias.getAnonimo();
        if(anonimo.equals("true")){
            apelido.setText("Anonimo:");
        }
        else {
            apelido.setText(String.valueOf(lista_feed_ocorrencias.getApelido())+":");
        }
        tipodecrime.setText(String.valueOf(lista_feed_ocorrencias.getTipo()));
        descricao.setText(lista_feed_ocorrencias.getDescricao());
        endereco.setText("Ocorreu na"+ lista_feed_ocorrencias.getRua() + " no dia " + lista_feed_ocorrencias.getData());
        idocorrencia.setText(String.valueOf(lista_feed_ocorrencias.getNrOcorrencia()));

        if (lista_feed_ocorrencias.getTipo().equals(" Roubo")) {
            imagem.setImageResource(R.drawable.ic_assalto);
        } else if (lista_feed_ocorrencias.getTipo().equals(" Furto")) {
            imagem.setImageResource(R.drawable.ic_furto);
        } else if (lista_feed_ocorrencias.getTipo().equals(" Trafico de drogas")) {
            imagem.setImageResource(R.drawable.ic_trafico);
        }else if (lista_feed_ocorrencias.getTipo().equals(" Homicidio")) {
            imagem.setImageResource(R.drawable.ic_homicidio);
        }else if (lista_feed_ocorrencias.getTipo().equals(" Latrocinio")) {
            imagem.setImageResource(R.drawable.ic_latrocinio);
        }else if (lista_feed_ocorrencias.getTipo().equals(" Abuso Sexual")) {
            imagem.setImageResource(R.drawable.ic_abuso);
        }

        return view;
    }
}