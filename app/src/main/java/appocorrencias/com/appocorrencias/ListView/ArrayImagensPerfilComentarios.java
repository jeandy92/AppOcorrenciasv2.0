package appocorrencias.com.appocorrencias.ListView;

/**
 * Created by PamelaNycoly on 04/05/2017.
 */

import android.graphics.Bitmap;

import java.io.IOException;
import java.util.ArrayList;

public class ArrayImagensPerfilComentarios {

    static ArrayList<DadosImagensComentarios> imagens = new ArrayList();

    public ArrayImagensPerfilComentarios() {
    }

    static ArrayImagensPerfilComentarios Instance = null;

    static ArrayImagensPerfilComentarios getInstance() {
        if (Instance == null) {
            Instance = new ArrayImagensPerfilComentarios();
        }
        return Instance;
    }

    public static void adicionarImg(DadosImagensComentarios d) throws IOException {
        imagens.add(d);
    }

    public static Bitmap getImgPerfil(String pCpf) {
        Bitmap img = null;

        int n = imagens.size();
        for (int i = 0; i < n; i++) {
            if (pCpf.equals(imagens.get(i).cpf)) {

                img = imagens.get(i).img;
            }
        }
        return img;
    }

    public static ArrayList<DadosImagensComentarios> getImagens() {
        return imagens;
    }

    public static void setImagens(ArrayList<DadosImagensComentarios> imagens) {
        ArrayImagensPerfilComentarios.imagens = imagens;
    }

    public static void removerImg(Bitmap d) {
        imagens.remove(d);
    }

    public static void deleteBitmap() {
        imagens.clear();
    }
}
