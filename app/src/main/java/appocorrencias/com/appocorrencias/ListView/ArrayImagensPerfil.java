package appocorrencias.com.appocorrencias.ListView;

/**
 * Created by PamelaNycoly on 04/05/2017.
 */

import android.graphics.Bitmap;

import java.io.IOException;
import java.util.ArrayList;

public class ArrayImagensPerfil {

    static ArrayList<Bitmap> imagens = new ArrayList();

    public ArrayImagensPerfil() {
    }

    static ArrayImagensPerfil Instance = null;

    static ArrayImagensPerfil getInstance() {
        if (Instance == null) {
            Instance = new ArrayImagensPerfil();
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
        ArrayImagensPerfil.imagens = imagens;
    }

    public static void removerImg(Bitmap d) {
        imagens.remove(d);
    }

    public static void deleteBitmap() {
        imagens.clear();
    }
}
