package appocorrencias.com.appocorrencias.ClassesSA;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import appocorrencias.com.appocorrencias.ListView.ArrayImagens;
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfil;
import appocorrencias.com.appocorrencias.ListView.ArrayImagensPerfilComentarios;
import appocorrencias.com.appocorrencias.ListView.DadosImagensComentarios;

/**
 * Created by Jeanderson on 12/04/2017.
 */

public class ProcessaSocket {
   
    static Socket cliente = new Socket();
    static OutputStream canalSaida = null;
    static InputStream canalEntrada = null;
    static Socket socket;


    //private static String IP_CONEXAO = "192.168.1.12";// "52.34.140.131";
    private static String IP_CONEXAO = "52.34.140.131";
    private static int PORTA_CONEXAO = 63200;

    public static String recebeDados(InputStream in) throws IOException {
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
        return str;
    }


    public static String recebeDadosImg(InputStream in) throws IOException {
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

        return str;

    }


    public static int calculaValorToInt(byte[] valor) {
        return valor[3] << 24 |
                (valor[2] & 0xFF) << 16 |
                (valor[1] & 0xFF) << 8 |
                (valor[0] & 0xFF);
    }


    public static void receberImagem(InputStream in) throws IOException {

        // ler
        byte[] buff = new byte[4];
        int k = 0, lidos = 0, lidos_total = 0;

        // le tamanho total
        in.read(buff, 0, 4);
        int ttotal = calculaValorToInt(buff);
        Log.i("Tamanho total recebido", "------------------------" + String.valueOf(ttotal));
        // le o resto do pacote
        byte[] pacote = new byte[ttotal];
        byte[] tmp = new byte[ttotal];
        //lidos = in.read(pacote, 0, ttotal);
        while (lidos_total < ttotal) {
            lidos = in.read(tmp, 0, ttotal - lidos_total);
            Log.i("Lidos", "------------------------" + String.valueOf(lidos));
            if (lidos > 0) {
                System.arraycopy(tmp, 0, pacote, lidos_total, lidos);
                lidos_total += lidos;
            } else {

                break;
            }
        }

        Bitmap imagem_convertida = null;

        // obtem qtd de imagens
        int qtd_imagens = calculaValorToInt(pacote);
        byte[][] imagens = new byte[qtd_imagens][];

        Log.i("bytes lidos", "------------------------" + String.valueOf(lidos));
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
            Log.i("bUFF2 Processa FOR", "------------------------" + String.valueOf(calculaValorToInt(buff)));
            // copia imagem
            imagens[i] = new byte[calculaValorToInt(buff)];
            Log.i("IMAGEMTA Processa FOR", "------------------------" + String.valueOf(imagens[i].length));
            Log.i("DESLOCAMENTO 1 Processa", "------------------------" + String.valueOf(deslocamento));
            System.arraycopy(pacote, deslocamento, imagens[i], 0, calculaValorToInt(buff));

            imagem_convertida = BitmapFactory.decodeByteArray(imagens[i], 0, calculaValorToInt(buff));
            ArrayImagens.adicionarImg(imagem_convertida);

            // desloca imagem e o seu tamanho (4 bytes)

            deslocamento += imagens[i].length;//calculaValorToInt(buff);

            Log.i("DESLOCAMENTO Processa", "------------------------" + String.valueOf(deslocamento));


        }

    }


    public static void receberImagemPerfil(InputStream in) throws IOException {

        // ler
        byte[] buff = new byte[4];
        int k = 0, lidos = 0, lidos_total = 0;

        // le tamanho total
        in.read(buff, 0, 4);
        int ttotal = calculaValorToInt(buff);
        Log.i("Tamanho total recebido", "------------------------" + String.valueOf(ttotal));
        // le o resto do pacote
        byte[] pacote = new byte[ttotal];
        byte[] tmp = new byte[ttotal];
        //lidos = in.read(pacote, 0, ttotal);
        while (lidos_total < ttotal) {
            lidos = in.read(tmp, 0, ttotal - lidos_total);
            if (lidos > 0) {
                System.arraycopy(tmp, 0, pacote, lidos_total, lidos);
                lidos_total += lidos;
            } else {

                break;
            }
        }

        Bitmap imagem_convertida = null;

        // obtem qtd de imagens
        int qtd_imagens = calculaValorToInt(pacote);
        byte[][] imagens = new byte[qtd_imagens][];

        Log.i("IMAGENS Processa", "------------------------" + String.valueOf(qtd_imagens));

        int deslocamento = 4; //ignora 4 primeiros bytes da qtd de imagens

        for (int i = 0; i < qtd_imagens; i++) {
            // copia tamanho da imagem
            System.arraycopy(pacote, deslocamento, buff, 0, 4);
            deslocamento += 4;

            // copia imagem
            imagens[i] = new byte[calculaValorToInt(buff)];
            System.arraycopy(pacote, deslocamento, imagens[i], 0, calculaValorToInt(buff));

            imagem_convertida = BitmapFactory.decodeByteArray(imagens[i], 0, calculaValorToInt(buff));
            ArrayImagensPerfil.adicionarImg(imagem_convertida);

            // desloca imagem e o seu tamanho (4 bytes)

            deslocamento += imagens[i].length;//calculaValorToInt(buff);


        }

    }


    public static String receberImagemPerfilComentarios(InputStream in) throws IOException {

        // ler
        byte[] buff = new byte[4];
        int k = 0, lidos = 0, lidos_total = 0;

        // le tamanho total
        /*in.read(buff, 0, 4);
        int ttotal2 = calculaValorToInt(buff);
        Log.i("Teste de Total", "------------------------" + String.valueOf(ttotal2));
        byte[] pacote3 = new byte[ttotal2];
        byte[][] teste2 = new byte[1][];

        System.arraycopy(pacote3, 0, buff, 0, 4);
        teste2[0] = new byte[calculaValorToInt(buff)];

        System.arraycopy(pacote3, 4, teste2[0], 0, calculaValorToInt(buff));
        Log.i("Teste de Total222", "------------------------" + String.valueOf(calculaValorToInt(buff)));
        String testeFalse = new String(teste2[0], "UTF-8");
        Log.i("Teste de False", "------------------------" + testeFalse); */


        byte[] resulBuff = new byte[0];
        //byte[] buff2 = new byte[4];
        int k2 = 0;
        String str2 = null;
        StringBuilder sb = new StringBuilder();

        k2 = in.read(buff, 0, buff.length);
        byte[] tbuff = new byte[resulBuff.length + k2];
        System.arraycopy(resulBuff, 0, tbuff, 0, resulBuff.length);
        System.arraycopy(buff, 0, tbuff, resulBuff.length, k2);
        resulBuff = tbuff;
        for (int i = 0; i < resulBuff.length; i++) {
            char t = (char) tbuff[i];
            sb.append(t);
            str2 = sb.toString();
        }

        Log.i("Teste de False2", "------------------------" + str2);

        if (str2.equals("fals")) {
            return "false";
        } else {

            int ttotal = calculaValorToInt(buff);

            Log.i("Tamanho total recebido", "------------------------" + String.valueOf(ttotal));
            // le o resto do pacote
            byte[] pacote2 = new byte[ttotal];
            byte[] tmp2 = new byte[ttotal];
            //lidos = in.read(pacote, 0, ttotal);
            while (lidos_total < ttotal) {
                lidos = in.read(tmp2, 0, ttotal - lidos_total);
                if (lidos > 0) {
                    System.arraycopy(tmp2, 0, pacote2, lidos_total, lidos);
                    lidos_total += lidos;
                } else {

                    break;
                }
            }

            Bitmap imagem_convertida = null;

            // obtem qtd de imagens
            int qtd_imagens = calculaValorToInt(pacote2);
            byte[][] imagens = new byte[qtd_imagens][];
            byte[][] cpf = new byte[qtd_imagens][];

            Log.i("IMAGENS Processa", "------------------------" + String.valueOf(qtd_imagens));

            int deslocamento = 4; //ignora 4 primeiros bytes da qtd de imagens

            for (int i = 0; i < qtd_imagens; i++) {
                // copia tamanho do cpfBuscarOcorrencia
                System.arraycopy(pacote2, deslocamento, buff, 0, 4);
                deslocamento += 4;

                // copia cpf
                cpf[i] = new byte[calculaValorToInt(buff)];
                System.arraycopy(pacote2, deslocamento, cpf[i], 0, calculaValorToInt(buff));

                String cpfDecode2 = new String(cpf[i], "UTF-8");

                Log.i("cpfBuscarOcorrencia", "------------------------" + cpfDecode2);

                deslocamento += cpf[i].length;//calculaValorToInt(buff);

                // copia tamanho da imagem
                System.arraycopy(pacote2, deslocamento, buff, 0, 4);
                deslocamento += 4;

                // copia imagem
                imagens[i] = new byte[calculaValorToInt(buff)];
                System.arraycopy(pacote2, deslocamento, imagens[i], 0, calculaValorToInt(buff));

                imagem_convertida = BitmapFactory.decodeByteArray(imagens[i], 0, calculaValorToInt(buff));

                String cpfDecode = new String(cpf[i], "UTF-8");

                Log.i("cpfBuscarOcorrencia","---------------------" + cpfDecode);

                DadosImagensComentarios dados = new DadosImagensComentarios(cpfDecode, imagem_convertida);

                ArrayImagensPerfilComentarios.adicionarImg(dados);

                // desloca imagem e o seu tamanho (4 bytes)

                deslocamento += imagens[i].length;//calculaValorToInt(buff);

            }
        }
        return "true";

    }

    //Metodo que envia as informações para o Servidor (Socket)
    public static String bytes(byte[] bites) {
        String str = null;

        try {

            Socket cliente2 = new Socket();

            int millisecondsTimeOut = 5000;
            InetSocketAddress adress = new InetSocketAddress(IP_CONEXAO, PORTA_CONEXAO);

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


    public static String primeiroCadastroNoServidor(String dados) throws IOException {
        String str = null;
        Socket cliente2 = new Socket();


        byte[] byteDados = dados.getBytes();
        int tamanhoDados = byteDados.length;

        byte[] byteTamanhoDados = toBytes(tamanhoDados);
        byte[] TamanhoEDados = concat(byteTamanhoDados, byteDados);

        int tamanhoPacote = TamanhoEDados.length;
        byte[] byteTamanhoPct = toBytes(tamanhoPacote);
        byte[] byteFinal = concat(byteTamanhoPct, TamanhoEDados);


        int millisecondsTimeOut = 5000;
        InetSocketAddress adress = new InetSocketAddress(IP_CONEXAO, PORTA_CONEXAO);

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
            str = recebeDados(canalEntrada);

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


    public static String buscarDadosImagensServer(String dados) throws IOException {
        String str = null;
        Socket cliente2 = new Socket();


        byte[] byteDados = dados.getBytes();
        int tamanhoDados = byteDados.length;

        byte[] byteTamanhoDados = toBytes(tamanhoDados);
        byte[] TamanhoEDados = concat(byteTamanhoDados, byteDados);

        int tamanhoPacote = TamanhoEDados.length;
        byte[] byteTamanhoPct = toBytes(tamanhoPacote);
        byte[] byteFinal = concat(byteTamanhoPct, TamanhoEDados);


        int millisecondsTimeOut = 5000;
        InetSocketAddress adress = new InetSocketAddress(IP_CONEXAO, PORTA_CONEXAO);

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

            str = receberImagemPerfilComentarios(canalEntrada);

            if (str.equals("false")) {
                return "false";
            } else {
                str = recebeDados(canalEntrada);
            }

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

    //Mé
    public static void adicionandoUsuarioNotificacao(String tokenUsuario , String bairroUsuario) {

        try {

                socket = new Socket(IP_CONEXAO, 63300);

                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                out.writeUTF("ADICIONAR_AO_GRUPO");

                if (in.readBoolean()) {

                    //out.writeUTF("eH27cLSAdao:APA91bFd74mH1n8mSOKoolLpYlb2m8-DFLu3GTNY4hMi3ZEOdnDiSgy1cmPg0n5uyx1O2J5nezpBGCci8kFpf0vmpUAQDr93H4bZTZwoFrGwaSoMwhqf8ElvzJHoHpa97hsJ1ZsN1tzb");
                    //out.writeUTF("APA91bFnTL6jR5rgFwnXz63t47TAXVIpfuiZIjx0gggLkjAmgLwiTmTUxhG7kaXESIo2al3wEcbqB3heUq7wP66AvBLbIcFv9JyNCGz1U7vFJQmBrxtj5vAk8F23JVY97gn0Ji0cHNb7");

                    System.out.println(tokenUsuario);
                    out.writeUTF(tokenUsuario);

                    System.out.println(bairroUsuario.trim());
                    out.writeUTF(bairroUsuario);
                    //out.writeUTF("APA91bFnTL6jR5rgFwnXz63t47TAXVIpfuiZIjx0gggLkjAmgLwiTmTUxhG7kaXESIo2al3wEcbqB3heUq7wP66AvBLbIcFv9JyNCGz1U7vFJQmBrxtj5vAk8F23JVY97gn0Ji0cHNb7");


                    Boolean resultado_servidor = in.readBoolean();

                    if (resultado_servidor) {

                        System.out.println("USUÁRIO ADICIONADO AO GRUPO DE NOTIFICAÇÕES DO  BAIRRO "+bairroUsuario);

                    } else {
                        System.out.println("USUÁRIO NÃO ADICIONADO AO GRUPO DE NOTIFICAÇÕES DO  BAIRRO "+bairroUsuario);
                    }

                }


        } catch (IOException e) {
            e.printStackTrace();

        }

    }


    public static void criandoGrupoNotificacao (String tokenUsuario, String nomeGrupoNotificacao, String bairroUsuario){

        try {

            socket = new Socket(IP_CONEXAO, 63300);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());


            out.writeUTF("CRIAR_GRUPO_NOTIFICACAO");

            if (in.readBoolean()) {

                out.writeUTF( tokenUsuario );
                out.writeUTF( nomeGrupoNotificacao );
                out.writeUTF( bairroUsuario);


                Boolean resultado_servidor = in.readBoolean();


                if (resultado_servidor) {

                    System.out.println("USUÁRIO ADICIONADO AO GRUPO DE NOTIFICAÇÕES DO  BAIRRO" + bairroUsuario);

                } else {
                    System.out.println("USUÁRIO NÃO ADICIONADO AO GRUPO DE NOTIFICAÇÕES DO  BAIRRO" + bairroUsuario);
                }

            }

        } catch (IOException e) {
        System.out.println("Erro ao se conectar com o servidor \n");
        System.out.println(e.getMessage());

         }
    }

    public static void enviandoNotificacaoGrupo (String tokenUsuario, String bairroUsu){
        try {

            socket = new Socket("52.34.140.131", 63300);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF("ENVIAR_NOTIFICACAO");

            if (in.readBoolean()) {

                out.writeUTF(tokenUsuario);
                out.writeUTF("Novo Crime Registrado");
                out.writeUTF("Assalto");
                out.writeUTF(bairroUsu);


                Boolean resultado_servidor = in.readBoolean();

                if (resultado_servidor) {

                    System.out.println("USUÁRIO ADICIONADO AO GRUPO DE NOTIFICAÇÕES DO  BAIRRO  JARDIM SILVEIRA");

                } else {
                    System.out.println("USUÁRIO NÃO ADICIONADO AO GRUPO DE NOTIFICAÇÕES DO  BAIRRO  JARDIM SILVEIRA");
                }

            } else {

            }

        } catch (IOException e) {
            System.out.println("Erro ao se conectar com o servidor \n");
            System.out.println(e.getMessage());

        }

    }


    public static String cadastrarOcorrencia(String convIdOcorrencia, String convCpf, String convTpoCrime, String convDataOcorrencia,
                                             String convUf, String convDescricao, String convEndereco, String convCidade,
                                             String convBairro, String convAnonimo, String convPrimeiroNome) throws IOException {
        //Envio de dados
        String CadastrarOcorrencia = "CadastrarOcorrencia" + " " + convIdOcorrencia + " " + convCpf + " " + convUf + " " + convDataOcorrencia +
                " " + convAnonimo + " " + convPrimeiroNome;

        String OcorrenciaRua = "OcorrenciaRua" + " " + convIdOcorrencia + " " + convEndereco;
        String OcorrenciaBairro = "OcorrenciaBairro" + " " + convIdOcorrencia + " " + convBairro;
        String OcorrenciaCidade = "OcorrenciaCidade" + " " + convIdOcorrencia + " " + convCidade;
        String OcorrenciaDescricao = "OcorrenciaDescricao" + " " + convIdOcorrencia + " " + convDescricao;
        String OcorrenciaTipo = "OcorrenciaTipo" + " " + convIdOcorrencia + " " + convTpoCrime;

        String retorno = primeiroCadastroNoServidor(CadastrarOcorrencia);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            if (retorno.equals("true")) {
                retorno = primeiroCadastroNoServidor(OcorrenciaRua);
                if (retorno.equals("erro")) {
                    return "erro";
                } else {
                    if (retorno.equals("true")) {
                        retorno = primeiroCadastroNoServidor(OcorrenciaBairro);
                        if (retorno.equals("erro")) {
                            return "erro";
                        } else {
                            if (retorno.equals("true")) {
                                retorno = primeiroCadastroNoServidor(OcorrenciaCidade);
                                if (retorno.equals("erro")) {
                                    return "erro";
                                } else {
                                    if (retorno.equals("true")) {
                                        retorno = primeiroCadastroNoServidor(OcorrenciaDescricao);
                                        if (retorno.equals("erro")) {
                                            return "erro";
                                        } else {
                                            if (retorno.equals("true")) {
                                                retorno = primeiroCadastroNoServidor(OcorrenciaTipo);
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

    public static String cadastrarUsuario(String cpf, String senha, String email, String telefone,
                                          String cep, String uf, String numero, String rua, String bairro,
                                          String cidade, String nome, String dataNasc, String complemento) throws IOException {

        String cadastro1 = "Cadastrar1" + " " + cpf + " " + senha +
                " " + email + " " + telefone + " " + cep +
                " " + uf + " " + numero + " " + dataNasc;
        String cadastroNome = "CadastrarNome" + " " + cpf + " " + nome;
        String cadastroRua = "CadastrarRua" + " " + cpf + " " + rua;
        String cadastroBairro = "CadastrarBairro" + " " + cpf + " " + bairro;
        String cadastroCidade = "CadastrarCidade" + " " + cpf + " " + cidade;
        String cadastroComplemento = "CadastrarComplemento" + " " + cpf + " " + complemento;

        String retorno = primeiroCadastroNoServidor(cadastro1);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            if (retorno.equals("true")) {
                retorno = primeiroCadastroNoServidor(cadastroNome);
                if (retorno.equals("erro")) {
                    return "erro";
                } else {
                    if (retorno.equals("true")) {
                        retorno = primeiroCadastroNoServidor(cadastroRua);
                        if (retorno.equals("erro")) {
                            return "erro";
                        } else {
                            if (retorno.equals("true")) {
                                retorno = primeiroCadastroNoServidor(cadastroBairro);
                                if (retorno.equals("erro")) {
                                    return "erro";
                                } else {
                                    if (retorno.equals("true")) {
                                        retorno = primeiroCadastroNoServidor(cadastroCidade);
                                        if (retorno.equals("erro")) {
                                            return "erro";
                                        } else {
                                            if (retorno.equals("true")) {
                                                retorno = primeiroCadastroNoServidor(cadastroComplemento);
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

    public static String cadastrarComentario(String idComentario, String idOcoreencia, String cpf, String data, String hora,
                                             String apelido, String descricao) throws IOException {
        //Envio de dados
        String CadastrarComentario = "CadastrarComentario" + " " + idComentario + " " + idOcoreencia + " " + cpf + " " + data +
                " " + hora + " " + apelido;

        String ComentarioDescricao = "ComentarioDescricao" + " " + idComentario + " " + descricao;

        String retorno = primeiroCadastroNoServidor(CadastrarComentario);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            if (retorno.equals("true")) {
                retorno = primeiroCadastroNoServidor(ComentarioDescricao);
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
    public static String enviaImg(String IDImg, String ID, String CPF, String nomeImg, byte[] byteImagem) throws IOException {

        String dados = "ImagemOcorrencia " + IDImg + " " + ID + " " + CPF + " " + nomeImg;

        byte[] byteDados = dados.getBytes();
        int tamanhoDados = byteDados.length;

        int tamanhoImagem = byteImagem.length;

        byte[] byteTamanhoDados = toBytes(tamanhoDados);
        byte[] byteTamanhoImagem = toBytes(tamanhoImagem);


        byte[] TamanhoEDados = concat(byteTamanhoDados, byteDados);
        byte[] TamanhoEImagem = concat(byteTamanhoImagem, byteImagem);

        byte[] DadosImagem = concat(TamanhoEDados, TamanhoEImagem);

        int tamanhoPacote = DadosImagem.length;
        byte[] byteTamanho = toBytes(tamanhoPacote);
        byte[] byteFinal = concat(byteTamanho, DadosImagem);

        String retorno = bytes(byteFinal);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            return "true";
        }
    }


    public static String enviaImgPerfil(String CPF, String nomeImg, byte[] byteImagem) throws IOException {

        String dados = "ImagemPerfil " + CPF + " " + nomeImg;

        byte[] byteDados = dados.getBytes();
        int tamanhoDados = byteDados.length;

        int tamanhoImagem = byteImagem.length;

        byte[] byteTamanhoDados = toBytes(tamanhoDados);
        byte[] byteTamanhoImagem = toBytes(tamanhoImagem);


        byte[] TamanhoEDados = concat(byteTamanhoDados, byteDados);
        byte[] TamanhoEImagem = concat(byteTamanhoImagem, byteImagem);

        byte[] DadosImagem = concat(TamanhoEDados, TamanhoEImagem);

        int tamanhoPacote = DadosImagem.length;
        byte[] byteTamanho = toBytes(tamanhoPacote);
        byte[] byteFinal = concat(byteTamanho, DadosImagem);

        String retorno = bytes(byteFinal);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            return "true";
        }
    }
}
