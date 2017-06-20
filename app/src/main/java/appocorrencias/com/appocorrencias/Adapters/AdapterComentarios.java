package appocorrencias.com.appocorrencias.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfilComentarios;
import appocorrencias.com.appocorrencias.ListView.DadosComentarios;
import appocorrencias.com.appocorrencias.R;

/**
 * Created by Jeanderson on 01/05/2017.
 */

public class AdapterComentarios extends BaseAdapter {

    private ArrayList<DadosComentarios> feedComentarios;
    private final Activity act;

    public AdapterComentarios(Activity act, ArrayList<DadosComentarios> feedComentarios){
        this.feedComentarios = feedComentarios;
        this.act =act;

    }


    @Override
    public int getCount() {
        return feedComentarios.size();
    }

    @Override
    public Object getItem(int position) {
        return feedComentarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.acitivity_item_feed_comentarios,parent,false);
        DadosComentarios lista_feed_comentarios  = feedComentarios.get(position);


        //pegando as referências das Views
        TextView apelido = (TextView) view.findViewById(R.id.tv_nome);
        TextView descricao = (TextView)view.findViewById(R.id.txt_desc_comentario);
        TextView dataHora = (TextView)view.findViewById(R.id.txDataHora);
        ImageView imagem = (ImageView)  view.findViewById(R.id.imagem_comentario);
        TextView idocorrencia = (TextView)  view.findViewById(R.id.txt_id_comentario);

        apelido.setText(lista_feed_comentarios.getApelido());
        descricao.setText(lista_feed_comentarios.getDescricao());
        dataHora.setText("Dia "+ lista_feed_comentarios.getData() + " às " + lista_feed_comentarios.getHora());
        idocorrencia.setText(String.valueOf(lista_feed_comentarios.getNrOcorrencia()));

        String cpfComentario = lista_feed_comentarios.getCpf();
        Bitmap imgPerfil = ArrayImagensPerfilComentarios.getImgPerfil(cpfComentario);

        imagem.setImageBitmap(imgPerfil);


        return view;
    }
}