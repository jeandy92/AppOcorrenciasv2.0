package appocorrencias.com.appocorrencias.ListView;

import android.graphics.Bitmap;

/**
 * Created by PamelaNycoly on 06/05/2017.
 */
public class DadosImagensComentarios {
    public String cpf;
    Bitmap img;


    public DadosImagensComentarios(String pCpf, Bitmap pImg) {

        this.cpf = pCpf;
        this.img = pImg;

    }

    public String getCpf() {
        return cpf;
    }


    public Bitmap getImg() {
        return img;
    }




}
