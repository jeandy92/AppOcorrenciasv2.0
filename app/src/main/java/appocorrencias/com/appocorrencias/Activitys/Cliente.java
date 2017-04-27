package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import appocorrencias.com.appocorrencias.ClassesSA.SlidingTabLayout;
import appocorrencias.com.appocorrencias.Adapters.TabAdapter;
import appocorrencias.com.appocorrencias.Fragments.PerfilFragment;
import appocorrencias.com.appocorrencias.R;

public class Cliente extends AppCompatActivity implements PerfilFragment.CriarReferencia {

        private Toolbar mToolbar;
        private Toolbar mToolbarBottom;
        private SlidingTabLayout slidingTabLayout;
        private ViewPager viewPager;
        private ImageButton cadastrarocorrencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);


        PerfilFragment fragment = new PerfilFragment();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String nomecompleto = bundle.getString("nomecompleto");

        Log.i("RESULTADO",nomecompleto);

        Bundle bundle1 = new Bundle();
        bundle1.putString("nomecompleto",nomecompleto);
        fragment.setArguments(bundle1);


        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_pagina);
        //cadastrarocorrencia = (ImageButton) findViewById(R.id.roubo);


        //Configurar sliding tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));

        //Configurar adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);


    }

    public void cadastrarroubo (View v){


        setContentView(R.layout.activity_cadastrar_ocorrencia);
        Bundle b  = new Bundle();
        b.putString("tipocrime","roubo");

        this.startActivity(new Intent(this,Cadastrar_Ocorrencia.class));




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
           finish();
        //setContentView(R.layout.activity_main);
        //this.startActivity(new Intent(this,MainActivity.class));
    }

    protected  void CriaNotificaçoes(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.fab_plus_icon)
                .setContentTitle("@string/TituloNotificao")
                .setContentText("@string/TextoNotificação");


    }

    public  void evCadastrarOcorrencia(){

        setContentView(R.layout.activity_cadastrar_ocorrencia);
        this.startActivity(new Intent(this,Cadastrar_Ocorrencia.class));
    }


    public void onCreateOcorrencia() {

        setContentView(R.layout.activity_cadastrar_ocorrencia);
        this.startActivity(new Intent(this,Cadastrar_Ocorrencia.class));
    }
}



