package appocorrencias.com.appocorrencias.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appocorrencias.com.appocorrencias.Activitys.Cadastrar_Ocorrencia;
import appocorrencias.com.appocorrencias.Activitys.Cliente;
import appocorrencias.com.appocorrencias.R;

import static android.view.View.inflate;


public class Fragment_Perfil extends Fragment {

    private FloatingActionButton btnCriarOcorrencia,btnAlteraDados, btnCadastrarOcorrencias;
    private TextView tvNomeCompleto;
    static String  Nome, CPF, Bairro;

    private static final String PREF_NAME = "MainActivityPreferences";

    public Fragment_Perfil() {
        // Required empty public constructor


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        btnCriarOcorrencia = (FloatingActionButton) view.findViewById(R.id.btnCadastrarOcorrencias);
        btnCadastrarOcorrencias = (FloatingActionButton) view.findViewById(R.id.btnCadastrarOcorrencias);

        tvNomeCompleto = (TextView) view.findViewById(R.id.tvNomeCompleto);
        tvNomeCompleto.setText(Cliente.getNome());

        btnCriarOcorrencia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Chama aqui o método da activity
            }
        });

        btnCadastrarOcorrencias.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Nome = Cliente.getNome();
                CPF = Cliente.getCPF();
                Bairro = Cliente.getBairro();

                // dataPasser.OnDataPass(Nome);
                // dataPasser.OnDataPass(CPF);
                //  dataPasser.OnDataPass(Bairro);



                //Chama aqui o método da activity
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle!=null){
            String nomecompleto = bundle.getString("nomecompleto");
            Log.i("SCRIPT frgagment",nomecompleto);
        }


//        String nomecompleto="Não funciona";
//        if (bundle != null) {
//            nomecompleto = bundle.getString("nomecompleto");
//            System.out.println("Teste" +nomecompleto+ " funcionou");
//
//        }
//        Log.i("RESULTADO",nomecompleto);
//        //nomecompleto = bundle.getString("nomecompleto");

    }





    public interface OnDataPass{

        public void OnDataPass(String nome);

    }

    OnDataPass dataPasser;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dataPasser = (OnDataPass) activity;

    }
}
