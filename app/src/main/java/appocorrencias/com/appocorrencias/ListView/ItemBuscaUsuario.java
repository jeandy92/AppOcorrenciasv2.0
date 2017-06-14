package appocorrencias.com.appocorrencias.ListView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import appocorrencias.com.appocorrencias.Activitys.BuscarUsuarios;
import appocorrencias.com.appocorrencias.Adapters.AdapterCustomSwiper;
import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.R;

import static appocorrencias.com.appocorrencias.ListView.ArrayImagens.getImagens;

public class ItemBuscaUsuario extends AppCompatActivity {

    ViewPager viewPager;
    AdapterCustomSwiper adapterCustomSwiper;
    private static ProcessaSocket processa = new ProcessaSocket();
    String nome, telefone, cpfUsuario, email, rua, bairro, uf, cidade, nascimento, CPF, Nome, BairroCli, tela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_item_busca_usuario);

        TextView Tv_Nome = (TextView) findViewById(R.id.txtCampoNome);
        TextView Tv_Nascimento = (TextView) findViewById(R.id.txtCampoNascimento);
        TextView Tv_CPF = (TextView) findViewById(R.id.txtCampoCPF);
        TextView Tv_Email = (TextView) findViewById(R.id.txtCampoEmail);
        TextView Tv_Cidade_UF = (TextView) findViewById(R.id.txtCidadeUFUsuario);
        TextView Tv_Rua_Bairro = (TextView) findViewById(R.id.txtRuaBairroUsuario);
        TextView Tv_teleone = (TextView) findViewById(R.id.txtCampoTelefone);
        TextView Tv_Bairro = (TextView) findViewById(R.id.txtCampoBairro);
        Button btnExcluir = (Button) findViewById(R.id.btnExcluir);


        Intent intent = getIntent();

        Bundle dados = intent.getExtras();

        cpfUsuario = dados.getString("cpfUsuario").toString();



        rua = ArrayUsuariosEncontrados.getRuaCPF(cpfUsuario);
        bairro = ArrayUsuariosEncontrados.getBairroCPF(cpfUsuario);
        uf = ArrayUsuariosEncontrados.getUFCPF(cpfUsuario);
        cidade = ArrayUsuariosEncontrados.getCidadeCPF(cpfUsuario);
        nascimento = ArrayUsuariosEncontrados.getNascimentoCPF(cpfUsuario);
        email = ArrayUsuariosEncontrados.getEmailCPF(cpfUsuario);
        nome = ArrayUsuariosEncontrados.getNomeCPF(cpfUsuario);
        telefone = ArrayUsuariosEncontrados.getTelefoneCPF(cpfUsuario);

        Bitmap[] images = new Bitmap[1];
        images[0]  = ArrayImagensPerfilComentarios.GetImgPerfil(cpfUsuario);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        adapterCustomSwiper = new AdapterCustomSwiper(this, images);
        viewPager.setAdapter(adapterCustomSwiper);


        Tv_CPF.setText(cpfUsuario);
        Tv_Nome.setText(nome);
        Tv_Email.setText(email);
        Tv_Nascimento.setText(nascimento);
        Tv_teleone.setText(telefone);
        Tv_Rua_Bairro.setText(rua);
        Tv_Bairro.setText(bairro);
        Tv_Cidade_UF.setText(cidade + ", " + uf);

    }

    public void evExcluirUsuario(View view) throws IOException {

        String ExcluirUsuario = "ExcluirUsuario " + cpfUsuario;
        String retornoExclusao = processa.cadastrar1_no_server(ExcluirUsuario);

        if (retornoExclusao.equals("true")) {
            ArrayUsuariosEncontrados.deleteAllArrayUsuarios();
            Toast.makeText(this, "Usuario Excluído ", Toast.LENGTH_SHORT).show();

            Intent cliente = new Intent(this, BuscarUsuarios.class);
            this.startActivity(cliente);


        } else {
            if (retornoExclusao.equals("erro")) {
                Toast.makeText(this, "Erro de Conexão", Toast.LENGTH_SHORT).show();

                Intent cliente = new Intent(this, BuscarUsuarios.class);
                this.startActivity(cliente);
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();

        ArrayImagens.deleteBitmap();

        Intent cliente = new Intent(this, BuscarUsuarios.class);
        this.startActivity(cliente);

    }


}
