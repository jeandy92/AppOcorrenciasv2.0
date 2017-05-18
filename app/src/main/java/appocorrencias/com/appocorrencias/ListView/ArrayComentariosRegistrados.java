package appocorrencias.com.appocorrencias.ListView;

/**
 * Created by PamelaNycoly on 04/05/2017.
 */

import java.io.IOException;
import java.util.ArrayList;

public class ArrayComentariosRegistrados {

    static ArrayList<DadosComentarios> dados = new ArrayList();

    private ArrayComentariosRegistrados() {
    }

    private static ArrayComentariosRegistrados Instance = null;

    static ArrayComentariosRegistrados getInstance() {
        if (Instance == null) {
            Instance = new ArrayComentariosRegistrados();
        }
        return Instance;
    }

    public static void adicionar(DadosComentarios d) throws IOException {
        dados.add(d);
    }

    public static int getTamanho() {
        int n = ArrayComentariosRegistrados.dados.size();
        return n;
    }


    public static String getAllNrOcorrencia() {
        StringBuilder Nr = new StringBuilder();

        for (DadosComentarios x : dados) {
            Nr.append(x.NrComentario + "//");
        }
        Nr.append("/-/" + dados.size());
        return Nr.toString();
    }


    ///RETORNANDO DADOS POR NUMERO DE OCORRENCIA
    public static String getIdComentarioNr(String Nr) {
        String NrComentario = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrComentario)) {

                NrComentario = dados.get(i).NrComentario;
            }
        }
        return NrComentario;
    }

    public static String getNrOcorrenciaNr(String Nr) {
        String NrOcorrencia = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrComentario)) {

                NrOcorrencia = dados.get(i).NrOcorrencia;
            }
        }
        return NrOcorrencia;
    }

    public static String getDataNr(String Nr) {
        String data = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrComentario)) {

                data = dados.get(i).Data;
            }
        }
        return data;
    }

    public static String getDescricaoNr(String Nr) {
        String descricao = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrComentario)) {

                descricao = dados.get(i).Descricao;
            }
        }
        return descricao;
    }

    public static String getCPFNr(String Nr) {
        String cpf = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrComentario)) {

                cpf = dados.get(i).CPF;
            }
        }
        return cpf;
    }

    public static String getHoraNr(String Nr) {
        String Hora = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrComentario)) {

                Hora = dados.get(i).Hora;
            }
        }
        return Hora;
    }


    public static String getApelidoNr(String Nr) {
        String apelido = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrComentario)) {

                apelido = dados.get(i).Apelido;
            }
        }
        return apelido;
    }

    public static boolean BuscaExiste(String servico) {
        boolean stat = false;
        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (servico.toUpperCase().contains("CHAT")) {
                stat = true;
                break;
            }
        }
        return stat;
    }

    static void MostrarTipo() {
        for (DadosComentarios x : dados) {
            System.out.println(x.Data);
        }
    }

    public static void remove(DadosComentarios d) {
        dados.remove(d);
    }

    public static int getQuantidadeComentario(String retorno){
        String VetorOcorrencias[] = retorno.split("///");
        String Ocorrencia1 = VetorOcorrencias[0];
        String OcorrenciaOne[] = Ocorrencia1.split("//");
        String tamanho = OcorrenciaOne[0];
        int qtdOcorrencia = Integer.parseInt(tamanho);
        return qtdOcorrencia;
    }

    public static void deleteAllArrayComentarios() {
        dados.clear();
    }

    public static ArrayList<DadosComentarios> getListaComentarios () {
        {
            return dados;
        }

    }


}
