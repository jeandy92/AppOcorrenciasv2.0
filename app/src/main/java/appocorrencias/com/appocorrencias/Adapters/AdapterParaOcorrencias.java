package appocorrencias.com.appocorrencias.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.ClassesSA.OcorrenciasRegistradas;
import appocorrencias.com.appocorrencias.R;

/**
 * Created by Jeanderson on 22/04/2017.
 */

public class AdapterParaOcorrencias extends BaseAdapter {

    private final ArrayList<OcorrenciasRegistradas> ocorrenciasregistradas;
    private final Activity act;

    public AdapterParaOcorrencias(ArrayList<OcorrenciasRegistradas> ocorrenciasregistradas,Activity act){
        this.ocorrenciasregistradas = ocorrenciasregistradas;
        this.act =act;

    }


    @Override
    public int getCount() {
        return ocorrenciasregistradas.size();
    }

    @Override
    public Object getItem(int position) {
        return ocorrenciasregistradas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.activity_lista_ocorrencias,parent,false);
        OcorrenciasRegistradas ocorrenciasRegistradas = ocorrenciasregistradas.get(position);

        //pegando as referências das Views
        TextView nome = (TextView) view.findViewById(R.id.lista_curso_personalizada_nome);
        TextView descricao = (TextView)view.findViewById(R.id.lista_curso_personalizada_descricao);
        ImageView imagem = (ImageView)  view.findViewById(R.id.lista_curso_personalizada_imagem);




        nome.setText(String.valueOf(ocorrenciasRegistradas.getId_ocorrencias()));
        descricao.setText(ocorrenciasRegistradas.getDescricao());

        if (ocorrenciasRegistradas.getTipocrime().equals("ASSALTO")) {
            imagem.setImageResource(R.drawable.ic_assalto);
        } else if (ocorrenciasRegistradas.getTipocrime().equals("ROUBO")) {
            imagem.setImageResource(R.drawable.ic_roubo1);
        }

//        imagem.setImageResource(R.drawable.ic_assalto);

        return view;
    }
}
