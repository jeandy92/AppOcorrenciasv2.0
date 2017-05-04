package appocorrencias.com.appocorrencias.ListView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jeanderson on 01/05/2017.
 */

public class Feed_Ocorrencias {

    String tipo_crime;
    String des_ocorrencia;
    String cpf_usuario;
    int id_ocorrencia;
    public static Random random = new Random();
    public static ArrayList<Feed_Ocorrencias> List = new ArrayList();


    public Feed_Ocorrencias(String tipo_crime, String des_ocorrencia, String cpf_usuario, int id_ocorrencia) {
        this.tipo_crime = tipo_crime;
        this.des_ocorrencia = des_ocorrencia;
        this.cpf_usuario = cpf_usuario;
        this.id_ocorrencia = id_ocorrencia;
    }

    public String getTipo_crime() {
        return tipo_crime;
    }

    public void setTipo_crime(String tipo_crime) {
        this.tipo_crime = tipo_crime;
    }

    public String getDes_ocorrencia() {
        return des_ocorrencia;
    }

    public void setDes_ocorrencia(String des_ocorrencia) {
        this.des_ocorrencia = des_ocorrencia;
    }

    public String getCpf_usuario() {
        return cpf_usuario;
    }

    public void setCpf_usuario(String cpf_usuario) {
        this.cpf_usuario = cpf_usuario;
    }

    public int getId_ocorrencia() {
        return id_ocorrencia;
    }

    public void setId_ocorrencia(int id_ocorrencia) {
        this.id_ocorrencia = id_ocorrencia;
    }

    public static ArrayList<Feed_Ocorrencias> criarfeedocorrencias (){

        ArrayList<Feed_Ocorrencias> List = new ArrayList();

        List.add(0,new Feed_Ocorrencias("Roubo","Sujeito no veiculo de plava cpi 2565","431313868",random.nextInt(1000)));
        List.add(1,new Feed_Ocorrencias("Tráfico de drogas","Jaqueta marrom , calça preta, boné preto, próximo ao ponto de onibus","431313868",random.nextInt(1000)));
        List.add(2,new Feed_Ocorrencias("Furto","Individuo furtou minha moto na alameda Grájau ","431313868",random.nextInt(1000)));
        List.add(3,new Feed_Ocorrencias("Homicídio","Homem assainou mulher a sangue frio com faca de serra, homem branco de baixa estatura  vestindo calça jeans e camiseta polo branca suja de sangue","431313868",random.nextInt(1000)));
        List.add(4,new Feed_Ocorrencias("Latrocínio","Jaqueta amarela, calça preta, boné verde","431313868",random.nextInt(1000)));
        List.add(5,new Feed_Ocorrencias("Abuso Sexual","Sujeito de barba usandoi regata branca, visto pela ultima vez próximo ao mercado santa sofia ","431313868",random.nextInt(1000)));
        List.add(6,new Feed_Ocorrencias("Furto","Homem roubou minha bolsa, dentro do shopping ","431313868",random.nextInt(1000)));


        return List;


    }
    public String toString(){

        return "Tipo Crime "+tipo_crime+"\n Descricao:"+des_ocorrencia+"Número Ocorrencia "+id_ocorrencia;

    }

    public  Feed_Ocorrencias buscar_ocorrencia(ArrayList<Feed_Ocorrencias> list, int id_ocorrencia){
        int i = 0;
        while( i <list.size()){
           if(list.get(i).getId_ocorrencia() == id_ocorrencia){
               return list.get(i);

           }else{
               i++;
           }

        }

        return null;

    }





}
