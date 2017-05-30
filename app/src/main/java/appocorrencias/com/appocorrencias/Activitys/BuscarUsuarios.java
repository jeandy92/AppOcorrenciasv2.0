package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Adapters.AdapterBuscaUsuario;
import appocorrencias.com.appocorrencias.ListView.DadosUsuarios;
import appocorrencias.com.appocorrencias.ListView.ItemBuscaUsuario;
import appocorrencias.com.appocorrencias.R;

public class BuscarUsuarios extends AppCompatActivity {

    private RadioButton rbtCPF, rbtNome;
    private TextInputLayout edtTextCPF, edtTextNome;
    private EditText edtFoco, edtCPF, edtNome;
    private ListView lvUsuariosEncontrados;
    private ArrayList<DadosUsuarios> listaUsuariosEncontrados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_de_usuarios);

        //List View para mostrar os usuários
        lvUsuariosEncontrados = (ListView) findViewById(R.id.lv_usuarios_encontrados);

        ArrayList<DadosUsuarios> listaUsuariosEncontrados = getListaUsuariosEncontrados();

        AdapterBuscaUsuario adapter = new AdapterBuscaUsuario(this, listaUsuariosEncontrados);

        lvUsuariosEncontrados.setAdapter(adapter);


        lvUsuariosEncontrados.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ItemBuscaUsuario.class);
                startActivity(intent);
            }
        });


        edtFoco = (EditText) findViewById(R.id.edtFoco);

        rbtCPF = (RadioButton) findViewById(R.id.rbtCPF);
        rbtNome = (RadioButton) findViewById(R.id.rbtNome);

        edtTextNome = (TextInputLayout) findViewById(R.id.input_layout_Nome);
        edtTextCPF = (TextInputLayout) findViewById(R.id.input_layout_CPF);

        edtCPF = (EditText) findViewById(R.id.edtCPF);
        edtNome = (EditText) findViewById(R.id.edtNome);

        edtTextCPF.setVisibility(View.INVISIBLE);
        edtTextCPF.setEnabled(false);

        edtTextNome.setVisibility(View.INVISIBLE);
        edtTextNome.setEnabled(false);

        rbtCPF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtFoco.requestFocus();

                rbtNome.setChecked(false);

                edtTextCPF.setVisibility(View.VISIBLE);
                edtTextCPF.setEnabled(true);
                edtCPF.clearFocus();

                edtNome.setText("");
                edtTextNome.setVisibility(View.INVISIBLE);
                edtTextNome.setEnabled(false);
            }
        });

        rbtNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtFoco.requestFocus();

                rbtCPF.setChecked(false);

                edtTextNome.setVisibility(View.VISIBLE);
                edtTextNome.setEnabled(true);
                edtTextNome.clearFocus();
                edtNome.clearFocus();


                edtCPF.setText("");
                edtTextCPF.setVisibility(View.INVISIBLE);
                edtTextCPF.setEnabled(false);
            }
        });

        edtCPF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edtNome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setContentView(R.layout.activity_login);
        this.startActivity(new Intent(this,Login.class));

    }

    public ArrayList<DadosUsuarios> getListaUsuariosEncontrados() {
        return listaUsuariosEncontrados;
    }
}

