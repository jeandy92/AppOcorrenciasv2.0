package appocorrencias.com.appocorrencias.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.ClassesSA.AdapterParaOcorrencias;
import appocorrencias.com.appocorrencias.ClassesSA.OcorrenciasRegistradas;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.ClassesSA.OcorrenciasRegistradas.criarocorrencias;


public class CadastrarOcorrenciasFragment extends Fragment {

    public CadastrarOcorrenciasFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_ocorrencias_registradas, container, false);
        ListView lstItems = (ListView)v.findViewById(R.id.lvListaOcorrencias);

        ArrayList<OcorrenciasRegistradas> prueba = criarocorrencias();

//        prueba.add("Element1");
//        prueba.add("Element2");
//        prueba.add("Element3");

        //ArrayAdapter<OcorrenciasRegistradas> allItemsAdapter =
        // new ArrayAdapter<OcorrenciasRegistradas>(getActivity().getBaseContext(),
        // android.R.layout.simple_list_item_1,prueba);
        AdapterParaOcorrencias adapter = new AdapterParaOcorrencias(prueba,getActivity());

        lstItems.setAdapter(adapter);

        return v;

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_ocorrencias_registradas, container, false);











    }



}
