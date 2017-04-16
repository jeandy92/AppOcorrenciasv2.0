package appocorrencias.com.appocorrencias;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Jeanderson on 12/04/2017.
 */

public class ProcessaSocket {
    static Socket cliente = null;
    static OutputStream canalSaida = null;
    static InputStream canalEntrada = null;

    // public ProcessaSocket(String host, int port){
    //  try {
    //    cliente = new Socket(host, port);
    //    canalSaida = cliente.getOutputStream();
    //      canalEntrada = cliente.getInputStream();

    //    } catch (Exception e) {
            //FIXME Tratar a Exception.
    //       e.printStackTrace();
    //     }

    // }


    public static String recebe_dados(InputStream in) throws IOException {
        //InputStream canalEntrada = cliente.getInputStream();
        byte[] resulBuff = new byte[0];
        byte[] buff = new byte[1024];
        int k = 0;
        String str = null;
        StringBuilder sb = new StringBuilder();

        k = in.read(buff, 0, buff.length);
        byte[] tbuff = new byte[resulBuff.length + k];
        System.arraycopy(resulBuff, 0, tbuff, 0, resulBuff.length);
        System.arraycopy(buff, 0, tbuff, resulBuff.length, k);
        resulBuff = tbuff;
        for (int i = 0; i < resulBuff.length; i++) {
            char t = (char) tbuff[i];
            sb.append(t);
            str = sb.toString();
        }
        in.close();
        return str;

    }

    //Metodo que envia as informações para o Servidor (Socket)
    public static void cadastrar_no_server(String dados) {
        try {
            //   Socket socket = null;

            cliente = new Socket("192.168.1.13", 2222);

            canalSaida = cliente.getOutputStream();
            canalEntrada = cliente.getInputStream();
            canalSaida.write(dados.getBytes());
            canalSaida.flush();
            canalSaida.close();
            cliente.close();



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



    public static String cadastrar1_no_server(String dados) {
        String str = null;
        try {
            //   Socket socket = null;

            cliente = new Socket("192.168.1.13", 2222);

            canalSaida = cliente.getOutputStream();
            canalEntrada = cliente.getInputStream();
            canalSaida.write(dados.getBytes());

            str=  recebe_dados(canalEntrada);

            canalSaida.flush();
            canalSaida.close();
            canalEntrada.close();
            cliente.close();


        } catch (Exception e) {
            //FIXME Tratar a Exception.
            e.printStackTrace();
        }
        return str;
    }
}