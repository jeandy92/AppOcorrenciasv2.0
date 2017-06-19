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

import appocorrencias.com.appocorrencias.Activitys.BuscarUsuarios;
import appocorrencias.com.appocorrencias.Activitys.ListarOcorrencias;
import appocorrencias.com.appocorrencias.Adapters.AdapterCustomSwiper;
import appocorrencias.com.appocorrencias.ClassesSA.ProcessaSocket;
import appocorrencias.com.appocorrencias.R;

public class ItemBuscaUsuario extends AppCompatActivity {

    ViewPager viewPager;
    AdapterCustomSwiper adapterCustomSwiper;
    private static ProcessaSocket processa = new ProcessaSocket();

    public static String nome, telefone, cpfUsuario, email, rua, bairro, uf, cidade, nascimento, Ip;
    public static int Porta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_item_busca_usuario);

        TextView tvNome = (TextView) findViewById(R.id.txtCampoNome);
        TextView tvNascimento = (TextView) findViewById(R.id.txtCampoNascimento);
        TextView tvCpf = (TextView) findViewById(R.id.txtCampoCPF);
        TextView tvEmail = (TextView) findViewById(R.id.txtCampoEmail);
        TextView tvCidadeUF = (TextView) findViewById(R.id.txtCidadeUFUsuario);
        TextView tvRuaBairro = (TextView) findViewById(R.id.txtRuaBairroUsuario);
        TextView tvTeleone = (TextView) findViewById(R.id.txtCampoTelefone);
        TextView tvBairro = (TextView) findViewById(R.id.txtCampoBairro);
        Button btnExcluir = (Button) findViewById(R.id.btnExcluir);

        Intent intent = getIntent();
        Bundle dados = intent.getExtras();

        Ip = dados.getString("ip");
        Porta = dados.getInt("porta");

        cpfUsuario  = dados.getString("cpfUsuario").toString();
        rua         = ArrayUsuariosEncontrados.getRuaCPF(cpfUsuario);
        bairro      = ArrayUsuariosEncontrados.getBairroCPF(cpfUsuario);
        uf          = ArrayUsuariosEncontrados.getUFCPF(cpfUsuario);
        cidade      = ArrayUsuariosEncontrados.getCidadeCPF(cpfUsuario);
        nascimento  = ArrayUsuariosEncontrados.getNascimentoCPF(cpfUsuario);
        email       = ArrayUsuariosEncontrados.getEmailCPF(cpfUsuario);
        nome        = ArrayUsuariosEncontrados.getNomeCPF(cpfUsuario);
        telefone    = ArrayUsuariosEncontrados.getTelefoneCPF(cpfUsuario);

        Bitmap[] images = new Bitmap[1];
        images[0]  = ArrayImagensPerfilComentarios.getImgPerfil(cpfUsuario);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        adapterCustomSwiper = new AdapterCustomSwiper(this, images);
        viewPager.setAdapter(adapterCustomSwiper);


        tvCpf.setText(cpfUsuario);
        tvNome.setText(nome);
        tvEmail.setText(email);
        tvNascimento.setText(nascimento);
        tvTeleone.setText(telefone);
        tvRuaBairro.setText(rua);
        tvBairro.setText(bairro);
        tvCidadeUF.setText(cidade + ", " + uf);

    }

    public void evExcluirUsuario(View view) throws IOException {

        String ExcluirUsuario = "ExcluirUsuario " + cpfUsuario;
        String retornoExclusao = processa.primeiroCadastroNoServidor(ExcluirUsuario, Ip, Porta);

        if (retornoExclusao.equals("true")) {
            ArrayUsuariosEncontrados.deleteAllArrayUsuarios();
            Toast.makeText(this, "Usuario Excluído ", Toast.LENGTH_SHORT).show();

            Intent cliente = new Intent(this, BuscarUsuarios.class);
            Bundle bundle = new Bundle();
            bundle.putString("ip", Ip);
            bundle.putInt("porta", Porta);

            cliente.putExtras(bundle);
            this.startActivity(cliente);


        } else {
            if (retornoExclusao.equals("erro")) {
                Toast.makeText(this, "Erro de Conexão", Toast.LENGTH_SHORT).show();

                Intent cliente = new Intent(this, BuscarUsuarios.class);
                Bundle bundle = new Bundle();
                bundle.putString("ip", Ip);
                bundle.putInt("porta", Porta);

                cliente.putExtras(bundle);
                this.startActivity(cliente);
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();

        ArrayImagens.deleteBitmap();

        Intent cliente = new Intent(this, BuscarUsuarios.class);
        Bundle bundle = new Bundle();
        bundle.putString("ip", Ip);
        bundle.putInt("porta", Porta);

        cliente.putExtras(bundle);
        this.startActivity(cliente);

    }


}
