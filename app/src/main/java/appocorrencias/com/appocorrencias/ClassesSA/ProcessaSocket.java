package appocorrencias.com.appocorrencias.ClassesSA;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import appocorrencias.com.appocorrencias.ListView.ArrayImagens;
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfil;

/**
 * Created by Jeanderson on 12/04/2017.
 */

public class ProcessaSocket {
    static Socket cliente = new Socket();
    static OutputStream canalSaida = null;
    static InputStream canalEntrada = null;

    //private static String ip_conexao = "192.168.1.12";// "52.34.140.131";
    private static String ip_conexao = "192.168.43.98";
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



    public static int valor_to_int(byte[] valor){
        return valor[3] <<24 |
                (valor[2] & 0xFF) << 16 |
                (valor[1] & 0xFF) << 8 |
                (valor[0] & 0xFF);
    }



    public static void receber_imagem(InputStream in) throws IOException {

        // ler
        byte[] buff = new byte[4];
        int k = 0,lidos = 0, lidos_total = 0;

        // le tamanho total
        in.read(buff, 0, 4);
        int ttotal = valor_to_int(buff);
        Log.i("Tamanho total recebido", "------------------------" + String.valueOf(ttotal));
        // le o resto do pacote
        byte[] pacote = new byte[ttotal];
        byte[] tmp = new byte[ttotal];
        //lidos = in.read(pacote, 0, ttotal);
        while (lidos_total < ttotal) {
            lidos = in.read(tmp, 0, ttotal - lidos_total);
            System.arraycopy(tmp, 0, pacote, lidos_total, lidos);
            lidos_total += lidos;
        }

        Bitmap imagem_convertida = null;

        // obtem qtd de imagens
        int qtd_imagens = valor_to_int(pacote);
        byte[][] imagens = new byte[qtd_imagens][];

        Log.i("Bytes lidos", "------------------------" + String.valueOf(lidos));
        Log.i("IMAGENS Processa", "------------------------" + String.valueOf(qtd_imagens));


        int deslocamento = 4; //ignora 4 primeiros bytes da qtd de imagens

        Log.i("IMAGENS Processa FOR", "------------------------" + String.valueOf(qtd_imagens));
        Log.i("PACOTE Processa FOR", "------------------------" + String.valueOf(pacote.length));

        for (int i = 0; i < qtd_imagens; i++) {
            Log.i("I Processa FOR", "------------------------" + String.valueOf(i));
            // copia tamanho da imagem
            System.arraycopy(pacote, deslocamento, buff, 0, 4);
            deslocamento += 4;

            Log.i("bUFF Processa FOR", "------------------------" + String.valueOf(buff.length));
            Log.i("bUFF2 Processa FOR", "------------------------" + String.valueOf(valor_to_int(buff)));
            // copia imagem
            imagens[i] = new byte[valor_to_int(buff)];
            Log.i("IMAGEMTA Processa FOR", "------------------------" + String.valueOf(imagens[i].length));
            Log.i("DESLOCAMENTO 1 Processa", "------------------------" + String.valueOf(deslocamento));
            System.arraycopy(pacote, deslocamento, imagens[i], 0, valor_to_int(buff));

            imagem_convertida = BitmapFactory.decodeByteArray(imagens[i], 0, valor_to_int(buff));
            ArrayImagens.adicionarImg(imagem_convertida);

            // desloca imagem e o seu tamanho (4 bytes)

            deslocamento += imagens[i].length;//valor_to_int(buff);

            Log.i("DESLOCAMENTO Processa", "------------------------" + String.valueOf(deslocamento));
        }
    }




    public static void receber_imagem_perfil(InputStream in) throws IOException {

        // ler
        byte[] buff = new byte[4];
        int k = 0;

        // le tamanho total
        in.read(buff, 0, 4);
        int ttotal = valor_to_int(buff);
        Log.i("Tamanho total recebido", "------------------------" + String.valueOf(ttotal));
        // le o resto do pacote
        byte[] pacote = new byte[ttotal];
        in.read(pacote, 0, ttotal);
        Bitmap imagem_convertida = null;

        // obtem qtd de imagens
        int qtd_imagens = valor_to_int(pacote);
        byte[][] imagens = new byte[qtd_imagens][];

        Log.i("IMAGENS Processa", "------------------------" + String.valueOf(qtd_imagens));

        int deslocamento = 4; //ignora 4 primeiros bytes da qtd de imagens

        for (int i = 0; i < qtd_imagens; i++) {
            // copia tamanho da imagem
            System.arraycopy(pacote, deslocamento, buff, 0, 4);
            deslocamento += 4;

            // copia imagem
            imagens[i] = new byte[valor_to_int(buff)];
            System.arraycopy(pacote, deslocamento, imagens[i], 0, valor_to_int(buff));

            imagem_convertida = BitmapFactory.decodeByteArray(imagens[i], 0, valor_to_int(buff));
            ArrayImagensPerfil.adicionarImg(imagem_convertida);

            // desloca imagem e o seu tamanho (4 bytes)

            deslocamento += imagens[i].length;//valor_to_int(buff);

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


    public static String envia_Img_Perfil(String CPF, String nomeImg, byte [] byteImagem ) throws IOException {

        String dados = "ImagemPerfil " + CPF + " " + nomeImg;

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
