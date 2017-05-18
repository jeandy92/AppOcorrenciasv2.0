package appocorrencias.com.appocorrencias.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.ListView.DadosComentarios;
import appocorrencias.com.appocorrencias.R;

/**
 * Created by Jeanderson on 01/05/2017.
 */

public class ComentariosAdapter extends BaseAdapter {

    private ArrayList<DadosComentarios> feed_comentarios = null;
    private final Activity act;

    public ComentariosAdapter(Activity act, ArrayList<DadosComentarios> feedocomentarios){
        this.feed_comentarios = feedocomentarios;
        this.act =act;

    }


    @Override
    public int getCount() {
        return feed_comentarios.size();
    }

    @Override
    public Object getItem(int position) {
        return feed_comentarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.acitivity_item_feed_comentarios,parent,false);
        DadosComentarios lista_feed_comentarios  = feed_comentarios.get(position);

        //pegando as referências das Views
        TextView apelido = (TextView) view.findViewById(R.id.txApelido);
        TextView descricao = (TextView)view.findViewById(R.id.txt_desc_comentario);
        TextView dataHora = (TextView)view.findViewById(R.id.txDataHora);
        ImageView imagem = (ImageView)  view.findViewById(R.id.imagem_comentario);
        TextView idocorrencia = (TextView)  view.findViewById(R.id.txt_id_comentario);

        apelido.setText(lista_feed_comentarios.getApelido());
        descricao.setText(lista_feed_comentarios.getDescricao());
        dataHora.setText("Dia "+ lista_feed_comentarios.getData() + " às " + lista_feed_comentarios.getHora());
        idocorrencia.setText(String.valueOf(lista_feed_comentarios.getNrOcorrencia()));

        imagem.setImageResource(R.drawable.ic_abuso);

        return view;
    }
}