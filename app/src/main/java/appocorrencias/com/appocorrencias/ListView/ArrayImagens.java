package appocorrencias.com.appocorrencias.ListView;

/**
 * Created by PamelaNycoly on 04/05/2017.
 */

import android.graphics.Bitmap;

import java.io.IOException;
import java.util.ArrayList;

public class ArrayImagens {

    static ArrayList<Bitmap> imagens = new ArrayList();

    public ArrayImagens() {
    }

    static ArrayImagens Instance = null;

    static ArrayImagens getInstance() {
        if (Instance == null) {
            Instance = new ArrayImagens();
        }
        return Instance;
    }

    public static void adicionarImg(Bitmap d) throws IOException {
        imagens.add(d);
    }

    public static ArrayList<Bitmap> getImagens() {
        return imagens;
    }

    public static void setImagens(ArrayList<Bitmap> imagens) {
        ArrayImagens.imagens = imagens;
    }

    public static void removerImg(Bitmap d) {
        imagens.remove(d);
    }

    public static void deleteBitmap() {
        imagens.clear();
    }
}
