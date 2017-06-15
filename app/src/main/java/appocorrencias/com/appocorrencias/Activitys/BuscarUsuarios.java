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
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfilComentarios;
import appocorrencias.com.appocorrencias.ListView.ArrayUsuariosEncontrados;
import appocorrencias.com.appocorrencias.ListView.DadosUsuarios;
import appocorrencias.com.appocorrencias.ListView.ItemBuscaUsuario;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.ListView.ArrayUsuariosEncontrados.deleteAllArrayUsuarios;
import static appocorrencias.com.appocorrencias.ListView.ArrayUsuariosEncontrados.getListaUsuarios;

public class BuscarUsuarios extends AppCompatActivity {

    private RadioButton rbtCPF, rbtNome;
    private TextInputLayout tinpCPF, tinpNome;
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

                Intent intent = new Intent(view.getContext(), ItemBuscaUsuario.class);

                String cpfUsuario = ((TextView) view.findViewById(R.id.txt_CPF)).getText().toString();

                //deleteAllArrayUsuarios();

                intent.putExtra("cpfUsuario", cpfUsuario);

                startActivity(intent);

            }
        });


        edtFoco = (EditText) findViewById(R.id.edtFoco);

        rbtCPF = (RadioButton) findViewById(R.id.rbtCPF);
        rbtNome = (RadioButton) findViewById(R.id.rbtNome);

        tinpNome = (TextInputLayout) findViewById(R.id.input_layout_Nome);
        tinpCPF = (TextInputLayout) findViewById(R.id.input_layout_CPF);

        edtCPF = (EditText) findViewById(R.id.edtCPF);
        edtNome = (EditText) findViewById(R.id.edtNome);

        tinpCPF.setVisibility(View.INVISIBLE);
        tinpCPF.setEnabled(false);

        tinpNome.setVisibility(View.INVISIBLE);
        tinpNome.setEnabled(false);

        rbtCPF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtFoco.requestFocus();

                rbtNome.setChecked(false);

                tinpCPF.setVisibility(View.VISIBLE);
                tinpCPF.setEnabled(true);
                edtCPF.clearFocus();

                edtNome.setText("");
                tinpNome.setVisibility(View.INVISIBLE);
                tinpNome.setEnabled(false);
            }
        });

        rbtNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtFoco.requestFocus();

                rbtCPF.setChecked(false);

                tinpNome.setVisibility(View.VISIBLE);
                tinpNome.setEnabled(true);
                tinpNome.clearFocus();
                edtNome.clearFocus();


                edtCPF.setText("");
                tinpCPF.setVisibility(View.INVISIBLE);
                tinpCPF.setEnabled(false);
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

    public void evBuscarUsuario(View view) throws IOException {

        deleteAllArrayUsuarios();

        if (rbtCPF.isChecked()) {
            String usuarioBusca = edtCPF.getText().toString();
            evBuscarUsuarioCpf(usuarioBusca);

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

    public void evBuscarUsuarioCpf(String cpfBuscarUsuario) throws IOException {

        String buscaUsuarioCPF = "BuscarUsuariosCPF " + cpfBuscarUsuario;
        //Toast.makeText(this, "Ocorrencias Registradas no meu bairro ", Toast.LENGTH_SHORT).show();

        ArrayImagensPerfilComentarios.deleteBitmap();
        String retorno = ProcessaSocket.buscarDadosImagensServer(buscaUsuarioCPF);

        if (retorno.equals("false")) {
            Toast.makeText(this, "Não há usuarios cadastrados com esse cpfAdm", Toast.LENGTH_SHORT).show();
        } else {
            // Pegando quantidade de usuarios
            int qtdUsuario = ArrayUsuariosEncontrados.getQuantidadeUsuarios(retorno);

            // Pegando dados e Adicioanando dados no Array
            for (int i = 0; i < qtdUsuario; i++) {
                String todosUsuarios[] = retorno.split("///");

                String usuario = todosUsuarios[i];
                String primeiroUsuario[] = usuario.split("//");
                String cpfUsuario = primeiroUsuario[1];
                String senhaUsuario = primeiroUsuario[2];
                String nomeUsuario = primeiroUsuario[3];
                String telefoneUsuario = primeiroUsuario[4];
                String emailUsuario = primeiroUsuario[5];
                String ruaUsuario = primeiroUsuario[6];
                String numeroUsuario = primeiroUsuario[7];
                String bairroUsuario = primeiroUsuario[8];
                String cidadeUsuario = primeiroUsuario[9];
                String cepUsuario = primeiroUsuario[10];
                String ufUsuario = primeiroUsuario[11];
                String complementoUsuario = primeiroUsuario[12];
                String nascimentoUsuario = primeiroUsuario[13];

                DadosUsuarios dado = new DadosUsuarios(cpfUsuario, senhaUsuario, nomeUsuario, telefoneUsuario, emailUsuario, ruaUsuario, numeroUsuario, bairroUsuario,
                        cidadeUsuario, cepUsuario, ufUsuario, complementoUsuario, nascimentoUsuario);

                ArrayUsuariosEncontrados.adicionar(dado);
            }
            //Toast.makeText(this, "Mostrando Ocorrencias no seu bairro ", Toast.LENGTH_SHORT).show();
        }
    }

    public void evBuscarUsuarioNome(String nomeBuscarUsuarioNome) throws IOException {

        String BuscarUsuarioNome = "BuscarUsuariosNome " + nomeBuscarUsuarioNome;
        //Toast.makeText(this, "Ocorrencias Registradas no meu bairro ", Toast.LENGTH_SHORT).show();
        ArrayImagensPerfilComentarios.deleteBitmap();

        String retorno = ProcessaSocket.buscarDadosImagensServer(BuscarUsuarioNome);

        if (retorno.equals("false")) {
            Toast.makeText(this, "Não há usuarios cadastrados com esse nomeBuscarOcorrencia", Toast.LENGTH_SHORT).show();
        } else {
            if (retorno.equals("erro")) {
                Toast.makeText(this, "Erro de Conexao", Toast.LENGTH_SHORT).show();
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
                    String Complemento = UsuarioUm[12];
                    String Nascimento = UsuarioUm[13];

                    DadosUsuarios dado = new DadosUsuarios(CPFUsu, Senha, NomeRetorno, Telefone, Email, RuaUsu, Numero, BairroUsu,
                            CidadeUsu, Cep, UFUsu, Complemento, Nascimento);

                    ArrayUsuariosEncontrados.adicionar(dado);

                }
                //Toast.makeText(this, "Mostrando Ocorrencias no seu bairro ", Toast.LENGTH_SHORT).show();
            }
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
        this.finish();
    }

}

