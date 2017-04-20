package appocorrencias.com.appocorrencias.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import appocorrencias.com.appocorrencias.ClassesSA.SlidingTabLayout;
import appocorrencias.com.appocorrencias.ClassesSA.TabAdapter;
import appocorrencias.com.appocorrencias.R;

public class Cliente extends AppCompatActivity {

        private Toolbar mToolbar;
        private Toolbar mToolbarBottom;
        private SlidingTabLayout slidingTabLayout;
        private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_pagina);

        //Configurar sliding tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent));

        //Configurar adapter
        TabAdapter tabAdapter = new TabAdapter( getSupportFragmentManager() );
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);


        try{


        SQLiteDatabase bancoDados = openOrCreateDatabase("app",MODE_PRIVATE,null);

        //tabela
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS grupousuarios(handle INT, apelido VARCHAR, senha INT)");

        //Inserir dados
       // bancoDados.execSQL("INSERT INTO grupousuarios (handle, apelido, senha) VALUES (1, '43131386843' , 1234) ");
        //bancoDados.execSQL("INSERT INTO grupousuarios (handle, apelido, senha) VALUES (1, 'adm' , 1234) ");

        Cursor cursor = bancoDados.rawQuery("SELECT handle,apelido,senha FROM grupousuarios",null);

        int indiceColunaHandle = cursor.getColumnIndex("handle");
        int indiceColunaApelido = cursor.getColumnIndex("apelido");
        int indiceColunaSenha = cursor.getColumnIndex("senha");

        cursor.moveToFirst();

        while (cursor!= null){

            Log.i("Resultado - handle: ", cursor.getString(indiceColunaHandle));
            Log.i("Resultado - apelido: ", cursor.getString(indiceColunaApelido));
            Log.i("Resultado - senha  ", cursor.getString(indiceColunaSenha));

            cursor.moveToNext();


        }}
        catch(Exception e){
                e.printStackTrace();
            }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setContentView(R.layout.activity_main);
        this.startActivity(new Intent(this,MainActivity.class));
    }

    protected  void CriaNotificaçoes(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.fab_plus_icon)
                .setContentTitle("@string/TituloNotificao")
                .setContentText("@string/TextoNotificação");


    }



}



