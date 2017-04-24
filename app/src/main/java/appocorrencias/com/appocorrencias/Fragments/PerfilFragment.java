package appocorrencias.com.appocorrencias.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appocorrencias.com.appocorrencias.Activitys.Cliente;
import appocorrencias.com.appocorrencias.R;



public class PerfilFragment extends Fragment{

    String Nome;
    String CPF;

    public PerfilFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Nome= Cliente.getNome();
        CPF= Cliente.getCPF();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }


}
