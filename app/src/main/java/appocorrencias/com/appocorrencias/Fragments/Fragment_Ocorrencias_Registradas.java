package appocorrencias.com.appocorrencias.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Adapters.AdapterParaOcorrencias;
import appocorrencias.com.appocorrencias.ListView.Item_Ocorrencia_Registradas;
import appocorrencias.com.appocorrencias.ListView.OcorrenciasRegistradas;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.ListView.OcorrenciasRegistradas.criarocorrencias;


public class Fragment_Ocorrencias_Registradas extends Fragment {

    private Fragment_Perfil.CriarReferencia referencia;


    public Fragment_Ocorrencias_Registradas() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_ocorrencias_registradas, container, false);
        ListView lstItems = (ListView)v.findViewById(R.id.lvListaOcorrencias);
        TextView Id_Ocorrencia  =(TextView)v.findViewById(R.id.id_ocorrencia);
        TextView Desc_Ocorrencia  =(TextView)v.findViewById(R.id.desc_ocorrencia);


        ArrayList<OcorrenciasRegistradas> listadeocorrencias = criarocorrencias();

//        prueba.add("Element1");
//        prueba.add("Element2");
//        prueba.add("Element3");

        //ArrayAdapter<OcorrenciasRegistradas> allItemsAdapter =
        // new ArrayAdapter<OcorrenciasRegistradas>(getActivity().getBaseContext(),
        // android.R.layout.simple_list_item_1,prueba);
        AdapterParaOcorrencias adapter = new AdapterParaOcorrencias(listadeocorrencias,getActivity());

        lstItems.setAdapter(adapter);

        lstItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:

                        Intent i = new Intent(view.getContext(), Item_Ocorrencia_Registradas.class);
                        String idocorrencia =  ((TextView) view.findViewById(R.id.id_ocorrencia)).getText().toString();
                        String descocorrencia =  ((TextView) view.findViewById(R.id.desc_ocorrencia)).getText().toString();


                        i.putExtra("id_ocorrencia", idocorrencia);
                        i.putExtra("desc_ocorrencia", descocorrencia);

                        startActivity(i);

                        Toast.makeText(view.getContext(), " case 1 Exibir Sobre", Toast.LENGTH_SHORT).show();
                        break;
                    case 1: Toast.makeText(view.getContext(), " case 2 Exibir Sobre", Toast.LENGTH_SHORT).show();

                        break;
                    case 2: Toast.makeText(view.getContext(), " case 3Exibir Sobre", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        break;
                }
            }
        });



        return v;

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_ocorrencias_registradas, container, false);

    }
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            referencia =(Fragment_Perfil.CriarReferencia) activity;

        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()+"Deve Implementar a CriarReferencia");
        }
    }

    public interface CriarReferencia{

        public void onCreateOcorrencia();


    }

}
