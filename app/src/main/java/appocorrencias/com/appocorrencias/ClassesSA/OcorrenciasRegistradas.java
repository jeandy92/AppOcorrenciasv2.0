package appocorrencias.com.appocorrencias.ClassesSA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jeanderson on 22/04/2017.
 */

public class OcorrenciasRegistradas {

    public int Id_ocorrencias;
    public String Descricao;
    public String Tipocrime;

    public static List<OcorrenciasRegistradas> lista;
     public static Random random = new Random();


    public int getId_ocorrencias() {
        return Id_ocorrencias;
    }

    public void setId_ocorrencias(int id_ocorrencias) {
        Id_ocorrencias = id_ocorrencias;
    }

    public String getTipocrime() {
        return Tipocrime;
    }

    public void setTipocrime(String tipocrime) {
        Tipocrime = tipocrime;
    }

    public String getDescricao() {
        return Descricao;

    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }



    public OcorrenciasRegistradas(int id_ocorrencias,String descricao,String tipocrime){

        Id_ocorrencias = id_ocorrencias;
        Descricao = descricao;
        Tipocrime = tipocrime;

    }

    public static ArrayList<OcorrenciasRegistradas> criarocorrencias (){

        ArrayList<OcorrenciasRegistradas> cursosList = new ArrayList();

        cursosList.add(0,new OcorrenciasRegistradas(random.nextInt(1000),"Jaqueta amarela, calça preta, boné verde","ROUBO"));
        cursosList.add(1,new OcorrenciasRegistradas(random.nextInt(1000),"Jaqueta amarela, calça preta, boné verde","ASSALTO"));
        cursosList.add(2,new OcorrenciasRegistradas(random.nextInt(1000),"Jaqueta amarela, calça preta, boné verde","ASSALTO"));
        cursosList.add(3,new OcorrenciasRegistradas(random.nextInt(1000),"Jaqueta amarela, calça preta, boné verde","ROUBO"));
        cursosList.add(4,new OcorrenciasRegistradas(random.nextInt(1000),"Jaqueta amarela, calça preta, boné verde","ASSALTO"));


        return cursosList;

    }
 public String toString(){

     return "Número Ocorrencia "+Id_ocorrencias+"\n Descricao:"+Descricao+"   " +" \n Tipo Ocorrencia: "+Tipocrime;

 }
}
