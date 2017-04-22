package appocorrencias.com.appocorrencias.ClassesSA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeanderson on 22/04/2017.
 */

public class OcorrenciasRegistradas {

    public int Id_ocorrencias;
    public String Descricao;
   public static List<OcorrenciasRegistradas> lista;

    public OcorrenciasRegistradas(int id_ocorrencias,String descricao){

        Id_ocorrencias = id_ocorrencias;
        Descricao = descricao;

    }

    public static ArrayList<OcorrenciasRegistradas> criarocorrencias (){

        ArrayList<OcorrenciasRegistradas> cursosList = new ArrayList();

        cursosList.add(0,new OcorrenciasRegistradas(1234,"Jaqueta amarela, calça preta, boné verde"));
        cursosList.add(1,new OcorrenciasRegistradas(1234,"Jaqueta amarela, calça preta, boné verde"));
        cursosList.add(2,new OcorrenciasRegistradas(1234,"Jaqueta amarela, calça preta, boné verde"));
        cursosList.add(3,new OcorrenciasRegistradas(1234,"Jaqueta amarela, calça preta, boné verde"));
        cursosList.add(4,new OcorrenciasRegistradas(1234,"Jaqueta amarela, calça preta, boné verde"));


        return cursosList;

    }
 public String toString(){

     return "Número Ocorrencia "+Id_ocorrencias+"\n Descricao:"+Descricao+"   " +" \n Tipo Ocorrencia: "+"Roubo";

 }
}
