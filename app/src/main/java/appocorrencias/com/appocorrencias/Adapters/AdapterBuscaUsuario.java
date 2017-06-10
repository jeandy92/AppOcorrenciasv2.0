package appocorrencias.com.appocorrencias.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.ListView.DadosUsuarios;
import appocorrencias.com.appocorrencias.R;

/**
 * Created by Jeanderson on 28/05/2017.
 */

public class AdapterBuscaUsuario extends BaseAdapter{


    private ArrayList<DadosUsuarios> listaDeUsuarios;
    private final Activity act;

    public AdapterBuscaUsuario( Activity act , ArrayList<DadosUsuarios> listaDeUsuarios ) {
        this.listaDeUsuarios = listaDeUsuarios;
        this.act =act;
    }



    @Override
    public int getCount() {
        return listaDeUsuarios.size();
    }

    @Override
    public Object getItem(int position) {
       return listaDeUsuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.activity_item_busca_usuario,parent,false);
        DadosUsuarios usuariosEcontrados  = listaDeUsuarios.get(position);



        //pegando as referências das Views
        ImageView imagemUsuario = (ImageView)  view.findViewById(R.id.img_imagem_usuario);
        TextView nome = (TextView) view.findViewById(R.id.tv_nome);
        TextView cidade = (TextView)view.findViewById(R.id.tv_cidade);
        TextView bairro = (TextView)view.findViewById(R.id.tv_bairro);
        TextView endereco = (TextView)view.findViewById(R.id.tv_rua);
        TextView cpf = (TextView)view.findViewById(R.id.txt_CPF);


        cpf.setText(usuariosEcontrados.getCpf());
        nome.setText(usuariosEcontrados.getNome());
        cidade.setText(usuariosEcontrados.getCidade());
        bairro.setText(usuariosEcontrados.getBairro());
        endereco.setText(String.valueOf( usuariosEcontrados.getRua()+ " N° "+usuariosEcontrados.getNumeroCasa() ) );
        imagemUsuario.setImageResource(R.drawable.ic_app);


    return  view;

    }
}
