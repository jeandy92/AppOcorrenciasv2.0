package appocorrencias.com.appocorrencias.ListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import appocorrencias.com.appocorrencias.R;

public class Item_Ocorrencia_Registradas extends AppCompatActivity {
    String nome = "Valor não recebido",descricao = "Valor não recebido";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_ocorrencias_registradas);

        EditText Edt_Id_Ocorrencia  =  (EditText) findViewById(R.id.edt_id_ocorrencia);
        EditText Edt_Desc_Ocorrencia  =  (EditText) findViewById(R.id.edt_desc_ocorrencia);


        Intent intent = getIntent();

        Bundle dados = intent.getExtras();

        String id     = dados.getString("id_ocorrencia").toString();
        String descricao         = dados.getString("desc_ocorrencia").toString();


        Edt_Id_Ocorrencia.setText(id);
        Edt_Desc_Ocorrencia.setText(descricao);



    }
}
