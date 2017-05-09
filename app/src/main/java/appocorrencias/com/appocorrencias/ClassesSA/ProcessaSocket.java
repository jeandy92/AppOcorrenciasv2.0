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
    static Socket cliente =  new Socket();
    static OutputStream canalSaida = null;
    static InputStream canalEntrada = null;

    private static  String  ip_conexao =  "10.12.56.32";// "52.34.140.131";

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
            cliente = new Socket(ip_conexao, 63200);
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
        Socket cliente2 =  new Socket();

        int millisecondsTimeOut = 3000;
        InetSocketAddress adress = new InetSocketAddress(ip_conexao, 2222);

        try {
            cliente2.connect(adress, millisecondsTimeOut);
        } catch (Exception e) {
            str= "erro";
            return str;
        }
          try {
              canalSaida = cliente2.getOutputStream();
              canalEntrada = cliente2.getInputStream();
              canalSaida.write(dados.getBytes());
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
                                              String convBairro, String Anonimo) throws IOException {
        //Envio de dados
        String CadastrarOcorrencia = "CadastrarOcorrencia" + " " + ID + " " + CPFCliente + " " + UF + " " + convDataOcorrencia +
                " " + Anonimo;

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
}




