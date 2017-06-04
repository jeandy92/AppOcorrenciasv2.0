package appocorrencias.com.appocorrencias.ListView;

/**
 * Created by PamelaNycoly on 04/05/2017.
 */

import android.graphics.Bitmap;

import java.io.IOException;
import java.util.ArrayList;

public class ArrayImagens {

    static ArrayList<Bitmap> imagens = new ArrayList();

    private ArrayImagens() {
    }

    private static ArrayImagens Instance = null;

    static ArrayImagens getInstance() {
        if (Instance == null) {
            Instance = new ArrayImagens();
        }
        return Instance;
    }

    public static void adicionar(Bitmap d) throws IOException {
        imagens.add(d);
    }

    public static int getTamanho() {
        int n = ArrayImagens.imagens.size();
        return n;
    }

    public static void deleteAllArrayImagens() {
        imagens.clear();
    }

    public static ArrayList<Bitmap> getListaImagens () {
        {
            return imagens;
        }

    }


}
