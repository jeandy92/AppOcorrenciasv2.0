package appocorrencias.com.appocorrencias.ListView;

import java.util.ArrayList;

/**
 * Created by Jeanderson on 23/04/2017.
 */

public class TiposDeCrime {

    public static String tipoCrime;
    public static String descricao;
    public static String imagem;

    public static ArrayList<TiposDeCrime> listadecrimes;
    public TiposDeCrime(String tipoCrime, String  descricao, String imagem ) {

        this.tipoCrime = tipoCrime;
        this.descricao = descricao;
        this.imagem =imagem;
    }

    public static String getTipoCrime() {
        return tipoCrime;
    }

    public static void setTipoCrime(String tipoCrime) {
        TiposDeCrime.tipoCrime = tipoCrime;
    }

    public static String getDescricao() {
        return descricao;
    }

    public static void setDescricao(String descricao) {
        TiposDeCrime.descricao = descricao;
    }

    public static String getImagem() {
        return imagem;
    }

    public static void setImagem(String imagem) {
        TiposDeCrime.imagem = imagem;
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
        return "tipo de crime"+ tipoCrime +"\ndescricao:"+ descricao +" "+ imagem;

    }
}
