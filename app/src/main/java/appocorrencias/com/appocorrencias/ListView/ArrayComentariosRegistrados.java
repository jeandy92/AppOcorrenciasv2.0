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
            Nr.append(x.nrComentario + "//");
        }
        Nr.append("/-/" + dados.size());
        return Nr.toString();
    }


    ///RETORNANDO DADOS POR NUMERO DE OCORRENCIA
    public static String getIdComentarioNr(String Nr) {
        String NrComentario = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).nrComentario)) {

                NrComentario = dados.get(i).nrComentario;
            }
        }
        return NrComentario;
    }

    public static String getNrOcorrenciaNr(String Nr) {
        String NrOcorrencia = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).nrComentario)) {

                NrOcorrencia = dados.get(i).nrOcorrencia;
            }
        }
        return NrOcorrencia;
    }

    public static String getDataNr(String Nr) {
        String data = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).nrComentario)) {

                data = dados.get(i).data;
            }
        }
        return data;
    }

    public static String getDescricaoNr(String Nr) {
        String descricao = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).nrComentario)) {

                descricao = dados.get(i).descricao;
            }
        }
        return descricao;
    }

    public static String getCpfNr(String Nr) {
        String cpf = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).nrComentario)) {

                cpf = dados.get(i).cpf;
            }
        }
        return cpf;
    }

    public static String getHoraNr(String Nr) {
        String Hora = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).nrComentario)) {

                Hora = dados.get(i).hora;
            }
        }
        return Hora;
    }

    public static String getApelidoNr(String Nr) {
        String apelido = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).nrComentario)) {

                apelido = dados.get(i).apelido;
            }
        }
        return apelido;
    }

    public static boolean buscaExiste(String servico) {
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

    static void mostrarTipo() {
        for (DadosComentarios x : dados) {
            System.out.println(x.data);
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
