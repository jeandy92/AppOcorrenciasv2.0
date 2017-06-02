package appocorrencias.com.appocorrencias.ClassesSA;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Jeanderson on 12/04/2017.
 */

public class ProcessaSocket {
    static Socket cliente = new Socket();
    static OutputStream canalSaida = null;
    static InputStream canalEntrada = null;

    //private static String ip_conexao = "192.168.1.12";// "52.34.140.131";
    private static String ip_conexao = "172.20.10.3";
    private static int  porta_conexao = 2222;

    public static String recebe_dados(InputStream in) throws IOException {
        byte[] resulBuff = new byte[0];
        byte[] buff = new byte[4096];
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


    public static String recebe_dados_img(InputStream in) throws IOException {
        byte[] resulBuff = new byte[0];
        byte[] buff = new byte[4096];
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

        //in.close();
        return str;

    }



    public static byte [] receber_imagem(InputStream in) throws IOException {

        byte[] resulBuff = new byte[0];
        byte[] buff = new byte[6022386];
        int k = 0;

        k = in.read(buff, 0, buff.length);
        byte[] tbuff = new byte[resulBuff.length + k];
        System.arraycopy(resulBuff, 0, tbuff, 0, resulBuff.length);
        System.arraycopy(buff, 0, tbuff, resulBuff.length, k);
        resulBuff = tbuff;

       // in.close();
        return resulBuff;

        }




    //Metodo que envia as informações para o Servidor (Socket)
    public static void cadastrar_no_server(String dados) {
        try {
            cliente = new Socket(ip_conexao, porta_conexao);
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

    //Metodo que envia as informações para o Servidor (Socket)
    public static String Bytes(byte[] bites) {
        String str = null;

        try {

            Socket cliente2 = new Socket();

            int millisecondsTimeOut = 3000;
            InetSocketAddress adress = new InetSocketAddress(ip_conexao, porta_conexao);

            try {
                cliente2.connect(adress, millisecondsTimeOut);
            } catch (Exception e) {
                str = "erro";
                return str;
            }

            canalSaida = cliente2.getOutputStream();

            canalSaida.write(bites, 0, bites.length);
            canalSaida.flush();
            canalSaida.close();

            str = "true";

            cliente2.close();

        } catch (Exception e) {
            //FIXME Tratar a Exception.
            e.printStackTrace();
        }
        return str;
    }


    public static String cadastrar1_no_server(String dados) throws IOException {
        String str = null;
        Socket cliente2 = new Socket();


        byte[] byteDados  = dados.getBytes();
        int tamanhoDados = byteDados.length;

        byte[] byteTamanhoDados = toBytes(tamanhoDados);
        byte[] TamanhoEDados = concat(byteTamanhoDados,byteDados);

        int tamanhoPacote = TamanhoEDados.length;
        byte [] byteTamanhoPct = toBytes(tamanhoPacote);
        byte [] byteFinal = concat(byteTamanhoPct,TamanhoEDados);


        int millisecondsTimeOut = 3000;
        InetSocketAddress adress = new InetSocketAddress(ip_conexao, porta_conexao);

        try {
            cliente2.connect(adress, millisecondsTimeOut);
        } catch (Exception e) {
            str = "erro";
            return str;
        }
        try {
            canalSaida = cliente2.getOutputStream();
            canalEntrada = cliente2.getInputStream();
            canalSaida.write(byteFinal);
            str = recebe_dados(canalEntrada);

            canalSaida.flush();
            canalSaida.close();
            canalEntrada.close();
            cliente2.close();

        } catch (Exception e) {
            //FIXME Tratar a Exception.
            e.printStackTrace();
        }
        return str;
    }


    public static String cadastrar_Ocorrencia(String ID, String CPFCliente, String tipo_crime, String convDataOcorrencia,
                                              String UF, String convDescricao, String convEndereco, String convCidade,
                                              String convBairro, String Anonimo, String PriNome) throws IOException {
        //Envio de dados
        String CadastrarOcorrencia = "CadastrarOcorrencia" + " " + ID + " " + CPFCliente + " " + UF + " " + convDataOcorrencia +
                " " + Anonimo + " " + PriNome;

        String OcorrenciaRua = "OcorrenciaRua" + " " + ID + " " + convEndereco;
        String OcorrenciaBairro = "OcorrenciaBairro" + " " + ID + " " + convBairro;
        String OcorrenciaCidade = "OcorrenciaCidade" + " " + ID + " " + convCidade;
        String OcorrenciaDescricao = "OcorrenciaDescricao" + " " + ID + " " + convDescricao;
        String OcorrenciaTipo = "OcorrenciaTipo" + " " + ID + " " + tipo_crime;

        String retorno = cadastrar1_no_server(CadastrarOcorrencia);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            if (retorno.equals("true")) {
                retorno = cadastrar1_no_server(OcorrenciaRua);
                if (retorno.equals("erro")) {
                    return "erro";
                } else {
                    if (retorno.equals("true")) {
                        retorno = cadastrar1_no_server(OcorrenciaBairro);
                        if (retorno.equals("erro")) {
                            return "erro";
                        } else {
                            if (retorno.equals("true")) {
                                retorno = cadastrar1_no_server(OcorrenciaCidade);
                                if (retorno.equals("erro")) {
                                    return "erro";
                                } else {
                                    if (retorno.equals("true")) {
                                        retorno = cadastrar1_no_server(OcorrenciaDescricao);
                                        if (retorno.equals("erro")) {
                                            return "erro";
                                        } else {
                                            if (retorno.equals("true")) {
                                                retorno = cadastrar1_no_server(OcorrenciaTipo);
                                                if (retorno.equals("erro")) {
                                                    return "erro";
                                                } else {
                                                    return "true";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return "false";

        }

    }

    public static String cadastrarUsuario(String convCpf, String senha, String email, String convTelefone,
                                          String convCep, String uf, String numero, String rua, String bairro,
                                          String nome, String cidade, String dataNasc, String complemento) throws IOException {

        String cadastro1 = "Cadastrar1" + " " + convCpf + " " + senha +
                " " + email + " " + convTelefone + " " + convCep +
                " " + uf + " " + numero + " " + dataNasc;
        String cadastroNome = "CadastrarNome" + " " + convCpf + " " + nome;
        String cadastroRua = "CadastrarRua" + " " + convCpf + " " + rua;
        String cadastroBairro = "CadastrarBairro" + " " + convCpf + " " + bairro;
        String cadastroCidade = "CadastrarCidade" + " " + convCpf + " " + cidade;
        String cadastroComplemento = "CadastrarComplemento" + " " + convCpf + " " + complemento;

        String retorno = cadastrar1_no_server(cadastro1);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            if (retorno.equals("true")) {
                retorno = cadastrar1_no_server(cadastroNome);
                if (retorno.equals("erro")) {
                    return "erro";
                } else {
                    if (retorno.equals("true")) {
                        retorno = cadastrar1_no_server(cadastroRua);
                        if (retorno.equals("erro")) {
                            return "erro";
                        } else {
                            if (retorno.equals("true")) {
                                retorno = cadastrar1_no_server(cadastroBairro);
                                if (retorno.equals("erro")) {
                                    return "erro";
                                } else {
                                    if (retorno.equals("true")) {
                                        retorno = cadastrar1_no_server(cadastroCidade);
                                        if (retorno.equals("erro")) {
                                            return "erro";
                                        } else {
                                            if (retorno.equals("true")) {
                                                retorno = cadastrar1_no_server(cadastroComplemento);
                                                if (retorno.equals("erro")) {
                                                    return "erro";
                                                } else {
                                                    return "true";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return "false";
    }

    public static String cadastrar_Comentario(String IDComentario, String IDOcorrencia, String CPFCliente, String Data, String Hora,
                                              String Apelido, String convDescricao) throws IOException {
        //Envio de dados
        String CadastrarComentario = "CadastrarComentario" + " " + IDComentario + " " + IDOcorrencia + " " + CPFCliente + " " + Data +
                " " + Hora + " " + Apelido;

        String ComentarioDescricao = "ComentarioDescricao" + " " + IDComentario + " " + convDescricao;

        String retorno = cadastrar1_no_server(CadastrarComentario);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            if (retorno.equals("true")) {
                retorno = cadastrar1_no_server(ComentarioDescricao);
                if (retorno.equals("erro")) {
                    return "erro";
                } else {
                    return "true";
                }
            }
        }
        return "false";
    }


    //// juntando 2 bytes arrays
    public static byte[] concat(byte[]... inputs) {
        int i = 0;
        for (byte[] b : inputs) {
            i += b.length;
        }
        byte[] r = new byte[i];
        i = 0;
        for (byte[] b : inputs) {
            System.arraycopy(b, 0, r, i, b.length);
            i += b.length;
        }
        return r;
    }

    //// Convertento inteiro para Byte
    public static byte[] toBytes(int i) {
        byte[] result = new byte[4];

        result[0] = (byte) (i);
        result[1] = (byte) (i >> 8);
        result[2] = (byte) (i >> 16);
        result[3] = (byte) (i >> 24);

        return result;
    }

    ////////////// enviar os bytes
    public static String envia_Img(String IDImg, String ID, String CPF, String nomeImg, byte [] byteImagem ) throws IOException {

        String dados = "ImagemOcorrencia " + IDImg+ " " + ID + " " + CPF + " " + nomeImg;

        byte[] byteDados  = dados.getBytes();
        int tamanhoDados = byteDados.length;

        int tamanhoImagem = byteImagem.length;

        byte[] byteTamanhoDados = toBytes(tamanhoDados);
        byte[] byteTamanhoImagem = toBytes(tamanhoImagem);


        byte[] TamanhoEDados = concat(byteTamanhoDados,byteDados);
        byte[] TamanhoEImagem = concat(byteTamanhoImagem,byteImagem);

        byte[] DadosImagem = concat(TamanhoEDados,TamanhoEImagem);

        int tamanhoPacote = DadosImagem.length;
        byte[] byteTamanho = toBytes(tamanhoPacote);
        byte[] byteFinal = concat(byteTamanho,DadosImagem);

        String retorno = Bytes(byteFinal);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            return "true";
        }
    }
}
