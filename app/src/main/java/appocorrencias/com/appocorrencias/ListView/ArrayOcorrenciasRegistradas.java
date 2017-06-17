package appocorrencias.com.appocorrencias.ListView;

/**
 * Created by PamelaNycoly on 04/05/2017.
 */

import java.io.IOException;
import java.util.ArrayList;

public class ArrayOcorrenciasRegistradas {

    static ArrayList<DadosOcorrencias> dados = new ArrayList();

    private ArrayOcorrenciasRegistradas() {
    }

    private static ArrayOcorrenciasRegistradas Instance = null;

    static ArrayOcorrenciasRegistradas getInstance() {
        if (Instance == null) {
            Instance = new ArrayOcorrenciasRegistradas();
        }
        return Instance;
    }

    public static void adicionar(DadosOcorrencias d) throws IOException {
        dados.add(d);
    }

    public static int getTamanho() {
        int n = ArrayOcorrenciasRegistradas.dados.size();
        return n;
    }

    public static String getAllBairro() {
        StringBuilder bairro = new StringBuilder();
        for (DadosOcorrencias x : dados) {
            bairro.append(x.bairro + "/");
        }
        return bairro.toString();
    }

   public static String getAllData() {
        StringBuilder data = new StringBuilder();
        for (DadosOcorrencias x : dados) {
            data.append(x.data + "/");
        }
        return data.toString();
    }

    public static String getAllTipo() {
        StringBuilder tipo = new StringBuilder();
        for (DadosOcorrencias x : dados) {
            tipo.append(x.tipo + "/");
        }
        return tipo.toString();
    }

    public static String getAllNrOcorrencia() {
        StringBuilder Nr = new StringBuilder();

        for (DadosOcorrencias x : dados) {
            Nr.append(x.nrOcorrencia + "//");
        }
        Nr.append("/-/" + dados.size());
        return Nr.toString();
    }

    public static String getAllDescricao() {
        StringBuilder descricao = new StringBuilder();
        for (DadosOcorrencias x : dados) {
            descricao.append(x.descricao + "/");
        }
        return descricao.toString();
    }

    ///RETORNANDO DADOS POR NUMERO DE OCORRENCIA
    public static String getBairroNr(String pNr) {
        String bairro = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pNr.equals(dados.get(i).nrOcorrencia)) {

                bairro = dados.get(i).bairro;
            }
        }
        return bairro;
    }

    public static String getTipoNr(String pNr) {
        String tipo = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pNr.equals(dados.get(i).nrOcorrencia)) {

                tipo = dados.get(i).tipo;
            }
        }
        return tipo;
    }

    public static String getDataNr(String pNr) {
        String data = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pNr.equals(dados.get(i).nrOcorrencia)) {

                data = dados.get(i).data;
            }
        }
        return data;
    }

    public static String getDescricaoNr(String pNr) {
        String descricao = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pNr.equals(dados.get(i).nrOcorrencia)) {

                descricao = dados.get(i).descricao;
            }
        }
        return descricao;
    }

    public static String getCPFNr(String pNr) {
        String cpf = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pNr.equals(dados.get(i).nrOcorrencia)) {

                cpf = dados.get(i).cpf;
            }
        }
        return cpf;
    }

    public static String getRuaNr(String pNr) {
        String rua = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pNr.equals(dados.get(i).nrOcorrencia)) {

                rua = dados.get(i).rua;
            }
        }
        return rua;
    }

    public static String getUFNr(String pNr) {
        String uf = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pNr.equals(dados.get(i).nrOcorrencia)) {

                uf = dados.get(i).uf;
            }
        }
        return uf;
    }

    public static String getCidadeNr(String pNr) {
        String cidade = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pNr.equals(dados.get(i).nrOcorrencia)) {

                cidade = dados.get(i).cidade;
            }
        }
        return cidade;
    }

    public static String getAnonimoNr(String pNr) {
        String anonimo = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pNr.equals(dados.get(i).nrOcorrencia)) {

                anonimo = dados.get(i).anonimo;
            }
        }
        return anonimo;
    }

    public static String getApelidoNr(String pNr) {
        String apelido = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pNr.equals(dados.get(i).nrOcorrencia)) {

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

    static void MostrarTipo() {
        for (DadosOcorrencias x : dados) {
            System.out.println(x.tipo);
        }
    }

    public static void remove(DadosOcorrencias d) {
        dados.remove(d);
    }

    public static int getQuantidadeOcorrencia(String retorno){
        String VetorOcorrencias[] = retorno.split("///");
        String Ocorrencia1 = VetorOcorrencias[0];
        String OcorrenciaOne[] = Ocorrencia1.split("//");
        String tamanho = OcorrenciaOne[0];
        int qtdOcorrencia = Integer.parseInt(tamanho);
        return qtdOcorrencia;
    }

    public static void deleteAllArray() {
        dados.clear();
    }

    public static ArrayList<DadosOcorrencias> getListaOcorrencia () {
        {
            return dados;
        }

    }


}
