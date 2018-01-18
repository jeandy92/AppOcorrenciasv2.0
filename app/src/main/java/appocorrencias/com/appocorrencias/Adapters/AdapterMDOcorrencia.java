package appocorrencias.com.appocorrencias.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.ClassesSA.MDOcorrencia;
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfilComentarios;
import appocorrencias.com.appocorrencias.R;

    /**
     * Created by Jeanderson on 01/05/2017.
     */

    public class AdapterMDOcorrencia extends BaseAdapter {

        private final ArrayList<MDOcorrencia> feedOcorrencias;
        private final Activity act;

        public AdapterMDOcorrencia(Activity act, ArrayList<MDOcorrencia> feedOcorrencias){
            this.feedOcorrencias = feedOcorrencias;
            this.act =act;

        }


        @Override
        public int getCount() {
            return feedOcorrencias.size();
        }

        @Override
        public Object getItem(int position) {
            return feedOcorrencias.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = act.getLayoutInflater().inflate(R.layout.acitivity_item_feed_ocorrencias,parent,false);
            MDOcorrencia listaFeedOcorrencias  = feedOcorrencias.get(position);

            //pegando as referências das Views
            TextView tipodecrime  = (TextView) view.findViewById(R.id.tv_bairro);
            TextView apelido      = (TextView) view.findViewById(R.id.tv_nome);
            TextView descricao    = (TextView)view.findViewById(R.id.txt_desc_comentario);
            TextView endereco     = (TextView)view.findViewById(R.id.txEndereco);
            ImageView imagem      = (ImageView)  view.findViewById(R.id.imagem_comentario);
            TextView idocorrencia = (TextView)  view.findViewById(R.id.txt_id_ocorrencia);


            String anonimo = listaFeedOcorrencias.getAnonimo();

            if(anonimo.equals("1")){
                apelido.setText("anonimo:");
            }
            else {
                apelido.setText(String.valueOf(listaFeedOcorrencias.getUsuario().getNome())+":");
            }

            tipodecrime.setText(String.valueOf(listaFeedOcorrencias.getTipo()));
            descricao.setText(listaFeedOcorrencias.getDescricao());
            endereco.setText("Ocorreu na "+ listaFeedOcorrencias.getRua() + " no dia " + listaFeedOcorrencias.getData());
            idocorrencia.setText(String.valueOf(listaFeedOcorrencias.getId()));

            String CPF = listaFeedOcorrencias.getUsuario().getCpf();


            if(anonimo.equals("1")){
                imagem.setImageResource(R.drawable.ic_anonimo);
            }
            else {
                Bitmap img = ArrayImagensPerfilComentarios.getImgPerfil(CPF);
                if(img != null) {
                    imagem.setImageBitmap(img);
                }else{
                    imagem.setImageResource(R.drawable.ic_app);
                }
            }


            return view;
        }
    }
