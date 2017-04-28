
package appocorrencias.com.appocorrencias.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.ListView.TiposDeCrime;
import appocorrencias.com.appocorrencias.R;

/**
 * Created by Jeanderson on 23/04/2017.
 */

public class AdapterSpinner extends BaseAdapter {

    private final Activity act;
    private final ArrayList<TiposDeCrime> listadecrimes;

    public AdapterSpinner(Activity act, ArrayList<TiposDeCrime> listadecrimes) {
        this.act = act;
        this.listadecrimes = listadecrimes;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = act.getLayoutInflater().inflate(R.layout.activity_lista_de_crimes,parent,false);
        TiposDeCrime tiposdecrime = listadecrimes.get(position);

        //pegando as referências das Views
        TextView nome = (TextView) view.findViewById(R.id.id_ocorrencia);
        EditText descricao = (EditText) view.findViewById(R.id.desc_ocorrencia);
        ImageView imagem = (ImageView)  view.findViewById(R.id.imagem_ocorrencia);




        nome.setText(tiposdecrime.getTipo_crime());
        descricao.setText(tiposdecrime.getDescricao());

        if (tiposdecrime.getImagem().equals("Roubo")) {
            imagem.setImageResource(R.drawable.ic_assalto);
        } else if (tiposdecrime.getImagem().equals("Furto")) {
            imagem.setImageResource(R.drawable.ic_roubo1);
        }

//        imagem.setImageResource(R.drawable.ic_assalto);

        return view;
    }
}
