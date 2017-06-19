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


    private static String ip_conexao = "192.168.1.17";// "52.34.140.131";
    //private static String ip_conexao = "52.34.140.131";
    private static int porta_conexao = 2222;//63200;

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

        return str;

    }


    public static int valor_to_int(byte[] valor) {
        return valor[3] << 24 |
                (valor[2] & 0xFF) << 16 |
                (valor[1] & 0xFF) << 8 |
                (valor[0] & 0xFF);
    }


    public static void receber_imagem(InputStream in) throws IOException {

        // ler
        byte[] buff = new byte[4];
        int k = 0, lidos = 0, lidos_total = 0;

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
        int k = 0, lidos = 0, lidos_total = 0;

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
            if (lidos > 0) {
                System.arraycopy(tmp, 0, pacote, lidos_total, lidos);
                lidos_total += lidos;
            } else {

                break;
            }
        }

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


    public static String receber_imagem_perfil_comentarios(InputStream in) throws IOException {

        // ler
        byte[] buff = new byte[4];
        int k = 0, lidos = 0, lidos_total = 0;


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

            int ttotal = valor_to_int(buff);

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
            int qtd_imagens = valor_to_int(pacote2);
            byte[][] imagens = new byte[qtd_imagens][];
            byte[][] cpf = new byte[qtd_imagens][];

            Log.i("IMAGENS Processa", "------------------------" + String.valueOf(qtd_imagens));

            int deslocamento = 4; //ignora 4 primeiros bytes da qtd de imagens

            for (int i = 0; i < qtd_imagens; i++) {
                // copia tamanho do cpfBuscarOcorrencia
                System.arraycopy(pacote2, deslocamento, buff, 0, 4);
                deslocamento += 4;

                // copia cpf
                cpf[i] = new byte[valor_to_int(buff)];
                System.arraycopy(pacote2, deslocamento, cpf[i], 0, valor_to_int(buff));

                String cpfDecode2 = new String(cpf[i], "UTF-8");

                Log.i("Valor cpfBusca Processa", "------------------------" + cpfDecode2);

                deslocamento += cpf[i].length;//valor_to_int(buff);

                // copia tamanho da imagem
                System.arraycopy(pacote2, deslocamento, buff, 0, 4);
                deslocamento += 4;

                // copia imagem
                imagens[i] = new byte[valor_to_int(buff)];
                System.arraycopy(pacote2, deslocamento, imagens[i], 0, valor_to_int(buff));

                imagem_convertida = BitmapFactory.decodeByteArray(imagens[i], 0, valor_to_int(buff));

                String cpfDecode = new String(cpf[i], "UTF-8");

                Log.i("Valor cpf Processa", "------------------------" + cpfDecode);

                DadosImagensComentarios dados = new DadosImagensComentarios(cpfDecode, imagem_convertida);

                ArrayImagensPerfilComentarios.adicionarImg(dados);

                // desloca imagem e o seu tamanho (4 bytes)

                deslocamento += imagens[i].length;//valor_to_int(buff);

            }
        }
        return "true";

    }

    //Metodo que envia as informações para o Servidor (Socket)
    public static String Bytes(byte[] bites) {
        String str = null;

        try {

            Socket cliente2 = new Socket();

            int millisecondsTimeOut = 5000;
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


        byte[] byteDados = dados.getBytes();
        int tamanhoDados = byteDados.length;

        byte[] byteTamanhoDados = toBytes(tamanhoDados);
        byte[] TamanhoEDados = concat(byteTamanhoDados, byteDados);

        int tamanhoPacote = TamanhoEDados.length;
        byte[] byteTamanhoPct = toBytes(tamanhoPacote);
        byte[] byteFinal = concat(byteTamanhoPct, TamanhoEDados);


        int millisecondsTimeOut = 5000;
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


    public static String buscar_dados_imagens_server(String dados) throws IOException {
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

            str = receber_imagem_perfil_comentarios(canalEntrada);

            if (str.equals("false")) {
                return "false";
            } else {
                str = recebe_dados(canalEntrada);
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
    public static void adicionandoUsuarioNotificacao(String token_usuario, String bairro_usu) {

        try {

            socket = new Socket(ip_conexao, 63300);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF("ADICIONAR_AO_GRUPO");

            if (in.readBoolean()) {

                //out.writeUTF("eH27cLSAdao:APA91bFd74mH1n8mSOKoolLpYlb2m8-DFLu3GTNY4hMi3ZEOdnDiSgy1cmPg0n5uyx1O2J5nezpBGCci8kFpf0vmpUAQDr93H4bZTZwoFrGwaSoMwhqf8ElvzJHoHpa97hsJ1ZsN1tzb");
                //out.writeUTF("APA91bFnTL6jR5rgFwnXz63t47TAXVIpfuiZIjx0gggLkjAmgLwiTmTUxhG7kaXESIo2al3wEcbqB3heUq7wP66AvBLbIcFv9JyNCGz1U7vFJQmBrxtj5vAk8F23JVY97gn0Ji0cHNb7");

                System.out.println(token_usuario);
                out.writeUTF(token_usuario);

                System.out.println(bairro_usu.trim());
                out.writeUTF(bairro_usu);
                //out.writeUTF("APA91bFnTL6jR5rgFwnXz63t47TAXVIpfuiZIjx0gggLkjAmgLwiTmTUxhG7kaXESIo2al3wEcbqB3heUq7wP66AvBLbIcFv9JyNCGz1U7vFJQmBrxtj5vAk8F23JVY97gn0Ji0cHNb7");


                Boolean resultado_servidor = in.readBoolean();

                if (resultado_servidor) {

                    System.out.println("USUÁRIO ADICIONADO AO GRUPO DE NOTIFICAÇÕES DO  BAIRRO " + bairro_usu);

                } else {
                    System.out.println("USUÁRIO NÃO ADICIONADO AO GRUPO DE NOTIFICAÇÕES DO  BAIRRO " + bairro_usu);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();

        }

    }


    public static void criandoGrupoNotificacao(String token_usuario, String nomeGrupoNotificacao, String bairroUsuario) {

        try {

            socket = new Socket(ip_conexao, 63300);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());


            out.writeUTF("CRIAR_GRUPO_NOTIFICACAO");

            if (in.readBoolean()) {

                out.writeUTF(token_usuario);
                out.writeUTF(nomeGrupoNotificacao);
                out.writeUTF(bairroUsuario);


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

    public static void enviandoNotificacaoGrupo(String tokenUsuario, String bairroUsu) {
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


    public static String cadastrar_Ocorrencia(String ID, String CPFCliente, String tipo_crime, String convDataOcorrencia,
                                              String UF, String convDescricao, String convEndereco, String convCidade,
                                              String convBairro, String Anonimo, String PriNome) throws IOException {
        //Envio de dados
        String CadastrarOcorrencia = "CadastrarOcorrencia" + " " + ID + " " + CPFCliente + " " + UF + " " + convDataOcorrencia +
                " " + Anonimo + " " + PriNome;

        byte[] byteDados1 = CadastrarOcorrencia.getBytes();

        byte[] byteRua = convEndereco.getBytes();
        byte[] byteBairro = convBairro.getBytes();
        byte[] byteCidade = convCidade.getBytes();
        byte[] byteDescricao = convDescricao.getBytes();
        byte[] byteTipoCrime = tipo_crime.getBytes();

        int tamanhoDado1, tamanhoRua, tamanhoBairro, tamanhoCidade, tamanhoDescricao, tamanhoTipoCrime;

        tamanhoDado1 = byteDados1.length;
        tamanhoRua = byteRua.length;
        tamanhoBairro = byteBairro.length;
        tamanhoCidade = byteCidade.length;
        tamanhoDescricao = byteDescricao.length;
        tamanhoTipoCrime = byteTipoCrime.length;

        byte[] byteTamanhoDado1 = toBytes(tamanhoDado1);
        byte[] byteTamanhoRua = toBytes(tamanhoRua);
        byte[] byteTamanhoBairro = toBytes(tamanhoBairro);
        byte[] byteTamanhoCidade = toBytes(tamanhoCidade);
        byte[] byteTamanhoDescricao = toBytes(tamanhoDescricao);
        byte[] byteTamanhoTipoCrime = toBytes(tamanhoTipoCrime);

        byte[] TamanhoEDado1 = concat(byteTamanhoDado1, byteDados1);
        byte[] TamanhoERua = concat(byteTamanhoRua, byteRua);
        byte[] TamanhoEBairro = concat(byteTamanhoBairro, byteBairro);
        byte[] TamanhoECidade = concat(byteTamanhoCidade, byteCidade);
        byte[] TamanhoEDescricao = concat(byteTamanhoDescricao, byteDescricao);
        byte[] TamanhoETipo = concat(byteTamanhoTipoCrime, byteTipoCrime);

        byte[] Dado1ERua = concat(TamanhoEDado1, TamanhoERua);
        byte[] BairroECidade = concat(TamanhoEBairro, TamanhoECidade);
        byte[] DescricaoETipo = concat(TamanhoEDescricao, TamanhoETipo);
        byte[] DadoRuaBaiCid = concat(Dado1ERua, BairroECidade);

        byte[] DadoRuaBairCidDescTip = concat(DadoRuaBaiCid, DescricaoETipo);

        int tamanhoPacote = DadoRuaBairCidDescTip.length;
        byte[] byteTamanhoPct = toBytes(tamanhoPacote);
        byte[] byteFinal = concat(byteTamanhoPct, DadoRuaBairCidDescTip);

        String retorno = Bytes(byteFinal);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            if (retorno.equals("true")) {
                return "true";
            }
            return "false";
        }
    }

    public static String cadastrarUsuario(String convCpf, String senha, String email, String convTelefone,
                                          String convCep, String uf, String numero, String rua, String bairro,
                                          String cidade, String nome, String dataNasc, String complemento) throws IOException {

        String cadastro1 = "Cadastrar1" + " " + convCpf + " " + senha + " " + email + " " + convTelefone + " " + convCep +
                " " + uf + " " + numero + " " + dataNasc;

        byte[] byteDados1 = cadastro1.getBytes();

        byte[] byteRua = rua.getBytes();
        byte[] byteBairro = bairro.getBytes();
        byte[] byteCidade = cidade.getBytes();
        byte[] byteNome = nome.getBytes();
        byte[] byteComplemento = complemento.getBytes();

        int tamanhoDado1, tamanhoRua, tamanhoBairro, tamanhoCidade, tamanhoNome, tamanhoComplemento;

        tamanhoDado1 = byteDados1.length;
        tamanhoRua = byteRua.length;
        tamanhoBairro = byteBairro.length;
        tamanhoCidade = byteCidade.length;
        tamanhoNome = byteNome.length;
        tamanhoComplemento = byteComplemento.length;

        byte[] byteTamanhoDado1 = toBytes(tamanhoDado1);
        byte[] byteTamanhoRua = toBytes(tamanhoRua);
        byte[] byteTamanhoBairro = toBytes(tamanhoBairro);
        byte[] byteTamanhoCidade = toBytes(tamanhoCidade);
        byte[] byteTamanhoNome = toBytes(tamanhoNome);
        byte[] byteTamanhoComplemento = toBytes(tamanhoComplemento);

        byte[] TamanhoEDado1 = concat(byteTamanhoDado1, byteDados1);
        byte[] TamanhoERua = concat(byteTamanhoRua, byteRua);
        byte[] TamanhoEBairro = concat(byteTamanhoBairro, byteBairro);
        byte[] TamanhoECidade = concat(byteTamanhoCidade, byteCidade);
        byte[] TamanhoENome = concat(byteTamanhoNome, byteNome);
        byte[] TamanhoEComplemento = concat(byteTamanhoComplemento, byteComplemento);

        byte[] Dado1ERua = concat(TamanhoEDado1, TamanhoERua);
        byte[] BairroECidade = concat(TamanhoEBairro, TamanhoECidade);
        byte[] NomeEComplemento = concat(TamanhoENome, TamanhoEComplemento);
        byte[] DadoRuaCidBair = concat(Dado1ERua, BairroECidade);

        byte[] DadoRuaBairCidNomComp = concat(DadoRuaCidBair, NomeEComplemento);

        int tamanhoPacote = DadoRuaBairCidNomComp.length;
        byte[] byteTamanhoPct = toBytes(tamanhoPacote);
        byte[] byteFinal = concat(byteTamanhoPct, DadoRuaBairCidNomComp);

        String retorno = Bytes(byteFinal);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            if (retorno.equals("true")) {
                return "true";
            }
            return "false";
        }
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
    public static String envia_Img(String IDImg, String ID, String CPF, String nomeImg, byte[] byteImagem) throws IOException {

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

        String retorno = Bytes(byteFinal);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            return "true";
        }
    }


    public static String envia_Img_Perfil(String CPF, String nomeImg, byte[] byteImagem) throws IOException {

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

        String retorno = Bytes(byteFinal);

        if (retorno.equals("erro")) {
            return "erro";
        } else {
            return "true";
        }
    }
}
