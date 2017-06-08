package appocorrencias.com.appocorrencias.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Adapters.AdapterBuscaUsuario;
import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.ListView.ArrayUsuariosEncontrados;
import appocorrencias.com.appocorrencias.ListView.DadosUsuarios;
import appocorrencias.com.appocorrencias.ListView.ItemBuscaUsuario;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.ListView.ArrayUsuariosEncontrados.deleteAllArrayUsuarios;
import static appocorrencias.com.appocorrencias.ListView.ArrayUsuariosEncontrados.getListaUsuarios;

public class BuscarUsuarios extends AppCompatActivity {

    private RadioButton rbtCPF, rbtNome;
    private TextInputLayout edtTextCPF, edtTextNome;
    private EditText edtFoco, edtCPF, edtNome;
    private ListView lvUsuariosEncontrados;
    private ArrayList<DadosUsuarios> listaUsuariosEncontrados;
    ProcessaSocket processa = new ProcessaSocket();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_de_usuarios);

        //List View para mostrar os usuários
        lvUsuariosEncontrados = (ListView) findViewById(R.id.lv_usuarios_encontrados);

        ArrayList<DadosUsuarios> listaUsuariosEncontrados = getListaUsuarios();

        AdapterBuscaUsuario adapter = new AdapterBuscaUsuario(this, listaUsuariosEncontrados);

        lvUsuariosEncontrados.setAdapter(adapter);


        lvUsuariosEncontrados.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(view.getContext(), ItemBuscaUsuario.class);


                String cpfusuario = ((TextView) view.findViewById(R.id.txtCPF)).getText().toString();

                deleteAllArrayUsuarios();

                i.putExtra("cpfUsuario", cpfusuario);

                startActivity(i);

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



    public void evBuscarOcorrencias(View view) throws IOException {

        deleteAllArrayUsuarios();

        if (rbtCPF.isChecked()) {
            String UsuarioBusca = edtCPF.getText().toString();
            evBuscarUsuarioCPF(UsuarioBusca);

            ArrayList<DadosUsuarios> listaUsuarios = getListaUsuarios();

            AdapterBuscaUsuario adapter = new AdapterBuscaUsuario(this, listaUsuarios);

            lvUsuariosEncontrados.setAdapter(adapter);

        } else {

            if (rbtNome.isChecked()) {

                String nome = edtNome.getText().toString();

                evBuscarUsuarioNome(nome);

                ArrayList<DadosUsuarios> listaUsuarios = getListaUsuarios();

                AdapterBuscaUsuario adapter = new AdapterBuscaUsuario(this, listaUsuarios);

                lvUsuariosEncontrados.setAdapter(adapter);

            } else {

                Toast.makeText(this, "Escolha um tipo de Busca ", Toast.LENGTH_SHORT).show();
            }
        }


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }


    public void evBuscarUsuarioCPF(String CPF) throws IOException {

        String BuscaUsuarioCPF = "BuscarUsuariosCPF " + CPF;
        //Toast.makeText(this, "Ocorrencias Registradas no meu Bairro ", Toast.LENGTH_SHORT).show();
        String retorno = processa.cadastrar1_no_server(BuscaUsuarioCPF);

        if (retorno.equals("false")) {
            Toast.makeText(this, "Não há usuarios cadastrados com esse CPF", Toast.LENGTH_SHORT).show();
        } else {
            // Pegando quantidade de usuarios
            int qtdUsuario = ArrayUsuariosEncontrados.getQuantidadeUsuarios(retorno);

            // Pegando dados e Adicioanando dados no Array
            for (int i = 0; i < qtdUsuario; i++) {
                String TodosUsuarios[] = retorno.split("///");

                String Usuario = TodosUsuarios[i];
                String UsuarioUm[] = Usuario.split("//");
                String CPFUsu = UsuarioUm[1];
                String Senha = UsuarioUm[2];
                String Nome = UsuarioUm[3];
                String Telefone = UsuarioUm[4];
                String Email = UsuarioUm[5];
                String RuaUsu = UsuarioUm[6];
                String Numero = UsuarioUm[7];
                String BairroUsu = UsuarioUm[8];
                String CidadeUsu = UsuarioUm[9];
                String Cep = UsuarioUm[10];
                String UFUsu = UsuarioUm[11];
                String Complemento = UsuarioUm[11];
                String Nascimento = UsuarioUm[11];

                DadosUsuarios dado = new DadosUsuarios(CPFUsu, Senha, Nome, Telefone, Email, RuaUsu, Numero, BairroUsu,
                        CidadeUsu, Cep, UFUsu, Complemento, Nascimento);

                ArrayUsuariosEncontrados.adicionar(dado);
            }
            //Toast.makeText(this, "Mostrando Ocorrencias no seu Bairro ", Toast.LENGTH_SHORT).show();
        }
    }


    public void evBuscarUsuarioNome(String Nome) throws IOException {

        String BuscarUsuarioNome = "BuscarUsuariosNome " + Nome;
        //Toast.makeText(this, "Ocorrencias Registradas no meu Bairro ", Toast.LENGTH_SHORT).show();
        String retorno = processa.cadastrar1_no_server(BuscarUsuarioNome);

        if (retorno.equals("false")) {
            Toast.makeText(this, "Não há usuarios cadastrados com esse Nome", Toast.LENGTH_SHORT).show();
        } else {
            // Pegando quantidade de Ocorrencias
            int qtdUsuario = ArrayUsuariosEncontrados.getQuantidadeUsuarios(retorno);

            // Pegando dados e Adicioanando dados no Array
            for (int i = 0; i < qtdUsuario; i++) {
                String TodosUsuarios[] = retorno.split("///");

                String Usuario = TodosUsuarios[i];
                String UsuarioUm[] = Usuario.split("//");
                String CPFUsu = UsuarioUm[1];
                String Senha = UsuarioUm[2];
                String NomeRetorno = UsuarioUm[3];
                String Telefone = UsuarioUm[4];
                String Email = UsuarioUm[5];
                String RuaUsu = UsuarioUm[6];
                String Numero = UsuarioUm[7];
                String BairroUsu = UsuarioUm[8];
                String CidadeUsu = UsuarioUm[9];
                String Cep = UsuarioUm[10];
                String UFUsu = UsuarioUm[11];
                String Complemento = UsuarioUm[11];
                String Nascimento = UsuarioUm[11];

                DadosUsuarios dado = new DadosUsuarios(CPFUsu, Senha, NomeRetorno, Telefone, Email, RuaUsu, Numero, BairroUsu,
                        CidadeUsu, Cep, UFUsu, Complemento, Nascimento);

                ArrayUsuariosEncontrados.adicionar(dado);
            }
            //Toast.makeText(this, "Mostrando Ocorrencias no seu Bairro ", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent adm = new Intent(this, Adm.class);

        Bundle bundle = new Bundle();
        bundle.putString("nome", "Administrador");
        bundle.putString("cpf", "33333333333");
        bundle.putString("bairro", "Adm");

        adm.putExtras(bundle);
        this.startActivity(adm);

    }

}

