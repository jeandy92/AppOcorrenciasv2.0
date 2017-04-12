package appocorrencias.com.appocorrencias;

import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Jeanderson on 12/04/2017.
 */

public class ProcessaSocket {


    //Metodo que envia as informações para o Servidor (Socket)
    public static void cadastrar_no_server(String dados) {
        try {
            Socket socket = null;

            OutputStream canalSaida = null;
            ObjectInputStream canalEntrada = null;

            socket = new Socket("172.20.10.3", 2222);

            canalSaida = socket.getOutputStream();
            canalSaida.write(dados.getBytes());


            // canalEntrada = new ObjectInputStream(socket.getInputStream());
            // Object object = canalEntrada.readObject();
            // if ((object != null) && (object instanceof String)) {
            //   txvRetornoSocket.setText(object.toString());
            // }


        } catch (Exception e) {
            //FIXME Tratar a Exception.
            e.printStackTrace();
        }
    }
}
