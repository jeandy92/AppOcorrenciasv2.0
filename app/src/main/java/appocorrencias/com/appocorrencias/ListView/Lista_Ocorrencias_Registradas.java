package appocorrencias.com.appocorrencias.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jeanderson on 22/04/2017.
 */

public class Lista_Ocorrencias_Registradas {

    public int Id_ocorrencias;
    public String Descricao;
    public String Tipocrime;
    public String CPF;

    public static List<Lista_Ocorrencias_Registradas> lista;
     public static Random random = new Random();


    public int getId_ocorrencias() {
        return Id_ocorrencias;
    }

    public void setId_ocorrencias(int id_ocorrencias) {
        Id_ocorrencias = id_ocorrencias;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
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



    public Lista_Ocorrencias_Registradas(int id_ocorrencias, String descricao, String tipocrime, String cpf){

        Id_ocorrencias = id_ocorrencias;
        Descricao = descricao;
        Tipocrime = tipocrime;
        CPF = cpf;
    }

    public static ArrayList<Lista_Ocorrencias_Registradas> criarocorrencias (){

        ArrayList<Lista_Ocorrencias_Registradas> List = new ArrayList();

        List.add(0,new Lista_Ocorrencias_Registradas(random.nextInt(1000),"Jaqueta amarela, calça preta, boné verde","ROUBO","431313868"));
        List.add(1,new Lista_Ocorrencias_Registradas(random.nextInt(1000),"Jaqueta amarela, calça preta, boné verde","ASSALTO","431313868"));
        List.add(2,new Lista_Ocorrencias_Registradas(random.nextInt(1000),"Jaqueta amarela, calça preta, boné verde","ASSALTO","431313868"));
        List.add(3,new Lista_Ocorrencias_Registradas(random.nextInt(1000),"Jaqueta amarela, calça preta, boné verde","ROUBO","431313868"));
        List.add(4,new Lista_Ocorrencias_Registradas(random.nextInt(1000),"Jaqueta amarela, calça preta, boné verde","ASSALTO","431313868"));


        return List;

    }
 public String toString(){

     return "Número Ocorrencia "+Id_ocorrencias+"\n Descricao:"+Descricao+"   " +" \n Tipo Ocorrencia: "+Tipocrime;

 }
}
