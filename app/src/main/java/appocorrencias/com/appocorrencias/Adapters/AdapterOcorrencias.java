package appocorrencias.com.appocorrencias.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfilComentarios;
import appocorrencias.com.appocorrencias.ListView.DadosOcorrencias;
import appocorrencias.com.appocorrencias.R;

/**
 * Created by Jeanderson on 22/04/2017.
 */

public class AdapterOcorrencias extends BaseAdapter {

    private ArrayList<DadosOcorrencias> ocorrenciasRegistradas;
    private Activity act;

    private ImageButton BtnDeletarOcorrencia;

    public AdapterOcorrencias(Activity act, ArrayList<DadosOcorrencias> ocorrenciasregistradas){
        this.ocorrenciasRegistradas = ocorrenciasregistradas;
        this.act =act;

    }


    @Override
    public int getCount() {
        return ocorrenciasRegistradas.size();
    }

    @Override
    public Object getItem(int position) {
        return ocorrenciasRegistradas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.activity_item_ocorrencias_registradas,parent,false);
        DadosOcorrencias listaOcorrenciasRegistradas = ocorrenciasRegistradas.get(position);

        //pegando as referências das Views
        TextView tipodecrime = (TextView) view.findViewById(R.id.tv_bairro);
        TextView descricao = (TextView)view.findViewById(R.id.txt_desc_comentario);
        TextView endereco = (TextView)view.findViewById(R.id.txEndereco);
        ImageView imagem = (ImageView)  view.findViewById(R.id.imagem_comentario);
        TextView idocorrencia = (TextView)  view.findViewById(R.id.txt_id_ocorrencia);


        tipodecrime.setText(String.valueOf(listaOcorrenciasRegistradas.getTipo()));
        descricao.setText(listaOcorrenciasRegistradas.getDescricao());
        endereco.setText("Ocorreu na"+ listaOcorrenciasRegistradas.getRua() + " no dia " + listaOcorrenciasRegistradas.getData());
        idocorrencia.setText(String.valueOf(listaOcorrenciasRegistradas.getNrOcorrencia()));

        String CPF = listaOcorrenciasRegistradas.getCPF();

            Bitmap img = ArrayImagensPerfilComentarios.getImgPerfil(CPF);
            if(img != null) {
                imagem.setImageBitmap(img);
            }else{
                imagem.setImageResource(R.drawable.ic_app);
            }

        return view;
    }

    public void evDeletarOcorrencia(View v) {

        //remove(listaOcorrenciasRegistradas);
        act.startActivity(act.getIntent());
    }
}
