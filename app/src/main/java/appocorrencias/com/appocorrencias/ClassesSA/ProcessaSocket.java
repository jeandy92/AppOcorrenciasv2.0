package appocorrencias.com.appocorrencias.ClassesSA;

import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Jeanderson on 12/04/2017.
 */

public class ProcessaSocket {
    static Socket cliente = null;
    static OutputStream canalSaida = null;
    static InputStream canalEntrada = null;


    public static String recebe_dados(InputStream in) throws IOException {
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
            cliente = new Socket("192.168.1.15", 2222);
            canalSaida = cliente.getOutputStream();
            canalEntrada = cliente.getInputStream();
            canalSaida.write(dados.getBytes());
            canalSaida.flush();
            canalSaida.close();
            cliente.close();

        } catch (Exception e) {
            //FIXME Tratar a Exception.
            e.printStackTrace();
        }
    }

    public static String cadastrar1_no_server(String dados) throws IOException {
        String str = null;

        try {
            cliente = new Socket("192.168.43.44", 3333);
        } catch (Exception e) {
            // MainActivity m = new MainActivity();
            // m.erro_de_conexao();
            //e.printStackTrace();
            str= "erro/";
            return str;
        }
                try {
                    canalSaida = cliente.getOutputStream();
                    canalEntrada = cliente.getInputStream();
                    canalSaida.write(dados.getBytes());

                    str = recebe_dados(canalEntrada);

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


    public static void cadastrarOcorrencia(String convDataOcorrencia, String convDescricao, String convEndereco, String dados) {
        //Envio de dados
        String cadastrarId_ocorrencia = "CadastrarId_ocorrencia" + " ";
        String cadastrarDataOcorrencia = "CadastrarDataOcorrencia" + " " + convDataOcorrencia + " " + convDataOcorrencia;
        String cadastrarDescricao = "CadastarDescricao" + " " + convDataOcorrencia + " " + convDataOcorrencia;
        String cadastrarEndereco = "CadastrarEndereco" + " " + convDataOcorrencia + " " + convDataOcorrencia;
        String cadastrarCidade = "CadastrarCidade" + " " + convDataOcorrencia + " " + convDataOcorrencia;

        ProcessaSocket.cadastrar_no_server(cadastrarId_ocorrencia);
        ProcessaSocket.cadastrar_no_server(cadastrarDataOcorrencia);
        ProcessaSocket.cadastrar_no_server(cadastrarDescricao);
        ProcessaSocket.cadastrar_no_server(cadastrarEndereco);
        ProcessaSocket.cadastrar_no_server(cadastrarCidade);
    }

    public static  boolean cadastrarUsuario(String convCpf, String senha, String email, String convTelefone,
                                            String convCep,EditText UF,String numero, String rua, String bairro,
                                            String nome, String cidade) throws IOException {

        String cadastro1 = "Cadastrar1" + " " + convCpf + " " + senha +
                " " + email + " " + convTelefone + " " + convCep +
                " " + UF.getText().toString() + " " + numero;
        String cadastroNome = "CadastrarNome" + " " + convCpf + " " + nome;
        String cadastroRua = "CadastrarRua" + " " + convCpf + " " + rua;
        String cadastroBairro = "CadastrarBairro" + " " + convCpf + " " + bairro;
        String cadastroCidade = "CadastrarCidade" + " " + convCpf + " " + cidade;

        String retorno = cadastrar1_no_server(cadastro1);

        if(retorno.equals("true")) {
            cadastrar_no_server(cadastroNome);
            cadastrar_no_server(cadastroRua);
            cadastrar_no_server(cadastroBairro);
            cadastrar_no_server(cadastroCidade);

            return true;
        } else {
            return false;
        }
    }



}



