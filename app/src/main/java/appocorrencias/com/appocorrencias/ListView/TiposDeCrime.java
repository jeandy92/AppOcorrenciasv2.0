package appocorrencias.com.appocorrencias.ListView;

import java.util.ArrayList;

/**
 * Created by Jeanderson on 23/04/2017.
 */

public class TiposDeCrime {

    public static String Tipo_crime;
    public static String Descricao;
    public static String Imagem;

    public static ArrayList<TiposDeCrime> listadecrimes;
    public TiposDeCrime(String tipo_crime ,String  descricao, String imagem ) {

        this.Tipo_crime = tipo_crime;
        this.Descricao = descricao;
        this.Imagem =imagem;
    }

    public static String getTipo_crime() {
        return Tipo_crime;
    }

    public static void setTipo_crime(String tipo_crime) {
        Tipo_crime = tipo_crime;
    }

    public static String getDescricao() {
        return Descricao;
    }

    public static void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public static String getImagem() {
        return Imagem;
    }

    public static void setImagem(String imagem) {
        Imagem = imagem;
    }

    public static ArrayList<TiposDeCrime> criarcrimes(){

        ArrayList<TiposDeCrime> cursosList = new ArrayList();

        cursosList.add(new TiposDeCrime("Roubo","ato de subtrair coisa móvel alheia, para si ou para outro, mediante grave ameaça","ROUBO"));
        cursosList.add(new TiposDeCrime("Furto","ato de subtrair coisa móvel alheia, para si ou para outro, mediante grave ameaça","FURTO"));
        cursosList.add(new TiposDeCrime("Furto","ato de subtrair coisa móvel alheia, para si ou para outro, mediante grave ameaça","FURTO"));
        cursosList.add(new TiposDeCrime("Roubo","ato de subtrair coisa móvel alheia, para si ou para outro, mediante grave ameaça","ROUBO"));
        cursosList.add(new TiposDeCrime("Furto","ato de subtrair coisa móvel alheia, para si ou para outro, mediante grave ameaça","FURTO"));


        return cursosList;
    }

    @Override
    public String toString() {
        return "Tipo de crime"+Tipo_crime+"\nDescricao:"+Descricao+" "+Imagem;

    }
}
