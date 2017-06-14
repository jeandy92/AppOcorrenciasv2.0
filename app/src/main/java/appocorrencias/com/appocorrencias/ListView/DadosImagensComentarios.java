package appocorrencias.com.appocorrencias.ListView;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by PamelaNycoly on 06/05/2017.
 */
public class DadosImagensComentarios {
    public String CPF;
    Bitmap img;


    public DadosImagensComentarios(String CPF, Bitmap img) {

        this.CPF = CPF;
        this.img = img;

    }

    public String getCPF() {
        return CPF;
    }


    public Bitmap getImg() {
        return img;
    }




}
