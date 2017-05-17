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
            bairro.append(x.Bairro + "/");
        }
        return bairro.toString();
    }

   public static String getAllData() {
        StringBuilder data = new StringBuilder();
        for (DadosOcorrencias x : dados) {
            data.append(x.Data + "/");
        }
        return data.toString();
    }

    public static String getAllTipo() {
        StringBuilder tipo = new StringBuilder();
        for (DadosOcorrencias x : dados) {
            tipo.append(x.Tipo + "/");
        }
        return tipo.toString();
    }

    public static String getAllNrOcorrencia() {
        StringBuilder Nr = new StringBuilder();

        for (DadosOcorrencias x : dados) {
            Nr.append(x.NrOcorrencia + "//");
        }
        Nr.append("/-/" + dados.size());
        return Nr.toString();
    }

    public static String getAllDescricao() {
        StringBuilder descricao = new StringBuilder();
        for (DadosOcorrencias x : dados) {
            descricao.append(x.Descricao + "/");
        }
        return descricao.toString();
    }

    ///RETORNANDO DADOS POR NUMERO DE OCORRENCIA
    public static String getBairroNr(String Nr) {
        String bairro = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrOcorrencia)) {

                bairro = dados.get(i).Bairro;
            }
        }
        return bairro;
    }

    public static String getTipoNr(String Nr) {
        String tipo = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrOcorrencia)) {

                tipo = dados.get(i).Tipo;
            }
        }
        return tipo;
    }

    public static String getDataNr(String Nr) {
        String data = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrOcorrencia)) {

                data = dados.get(i).Data;
            }
        }
        return data;
    }

    public static String getDescricaoNr(String Nr) {
        String descricao = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrOcorrencia)) {

                descricao = dados.get(i).Descricao;
            }
        }
        return descricao;
    }

    public static String getCPFNr(String Nr) {
        String cpf = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrOcorrencia)) {

                cpf = dados.get(i).CPF;
            }
        }
        return cpf;
    }

    public static String getRuaNr(String Nr) {
        String rua = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrOcorrencia)) {

                rua = dados.get(i).Rua;
            }
        }
        return rua;
    }

    public static String getUFNr(String Nr) {
        String uf = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrOcorrencia)) {

                uf = dados.get(i).UF;
            }
        }
        return uf;
    }

    public static String getCidadeNr(String Nr) {
        String cidade = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrOcorrencia)) {

                cidade = dados.get(i).Cidade;
            }
        }
        return cidade;
    }

    public static String getAnonimoNr(String Nr) {
        String anonimo = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrOcorrencia)) {

                anonimo = dados.get(i).Anonimo;
            }
        }
        return anonimo;
    }

    public static String getApelidoNr(String Nr) {
        String apelido = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (Nr.equals(dados.get(i).NrOcorrencia)) {

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
        for (DadosOcorrencias x : dados) {
            System.out.println(x.Tipo);
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
