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
 * Created by Jeanderson on 22/04/2017.
 */

public class AdapterParaOcorrencias extends BaseAdapter {

    private final ArrayList<DadosOcorrencias> ocorrenciasregistradas;
    private final Activity act;

    public AdapterParaOcorrencias(Activity act,ArrayList<DadosOcorrencias> ocorrenciasregistradas){
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
        View view = act.getLayoutInflater().inflate(R.layout.activity_item_ocorrencias_registradas,parent,false);
        DadosOcorrencias listaOcorrenciasRegistradas = ocorrenciasregistradas.get(position);

        //pegando as referências das Views
        TextView nome = (TextView) view.findViewById(R.id.id_ocorrencia);
        TextView descricao = (TextView)view.findViewById(R.id.desc_ocorrencia);
        ImageView imagem = (ImageView)  view.findViewById(R.id.imagem_ocorrencia);





        nome.setText(listaOcorrenciasRegistradas.getTipo());
        descricao.setText(listaOcorrenciasRegistradas.getDescricao());

        if (listaOcorrenciasRegistradas.getTipo().equals("ASSALTO")) {
            imagem.setImageResource(R.drawable.ic_assalto);
        } else if (listaOcorrenciasRegistradas.getTipo().equals("ROUBO")) {
            imagem.setImageResource(R.drawable.ic_assalto);
        }

//        imagem.setImageResource(R.drawable.ic_assalto);

        return view;
    }
}
