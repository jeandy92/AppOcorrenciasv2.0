package appocorrencias.com.appocorrencias.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jeanderson on 28/05/2017.
 */

public class ArrayUsuariosEncontrados {

    static ArrayList<DadosUsuarios> dados = new ArrayList();

    private ArrayUsuariosEncontrados() {
    }

    private static ArrayUsuariosEncontrados Instance = null;

    static ArrayUsuariosEncontrados getInstance() {
        if (Instance == null) {
            Instance = new ArrayUsuariosEncontrados();
        }
        return Instance;
    }

    public static void adicionar(DadosUsuarios d) throws IOException {
        dados.add(d);
    }

    public static int getTamanho() {
        int n = ArrayUsuariosEncontrados.dados.size();
        return n;
    }

    ///RETORNANDO DADOS POR NUMERO DE OCORRENCIA
    public static String getNomeCPF(String pCpf) {
        String nome = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pCpf.equals(dados.get(i).usuCpf)) {

                nome = dados.get(i).usuNome;
            }
        }
        return nome;
    }

    public static String getNascimentoCPF(String pCpf) {
        String nascimento = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pCpf.equals(dados.get(i).usuCpf)) {

                nascimento = dados.get(i).usuNascimento;
            }
        }
        return nascimento;
    }

    public static String getRuaCPF(String pCpf) {
        String rua = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pCpf.equals(dados.get(i).usuCpf)) {

                rua = dados.get(i).usuRua;
            }
        }
        return rua;
    }

    public static String getTelefoneCPF(String pCpf) {
        String telefone = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pCpf.equals(dados.get(i).usuCpf)) {

                telefone = dados.get(i).usuTelefone;
            }
        }
        return telefone;
    }

    public static String getCepCPF(String pCpf) {
        String cep = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pCpf.equals(dados.get(i).usuCpf)) {

                cep = dados.get(i).usuCep;
            }
        }
        return cep;
    }

    public static String getBairroCPF(String pCpf) {
        String bairro = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pCpf.equals(dados.get(i).usuCpf)) {

                bairro = dados.get(i).usuBairro;
            }
        }
        return bairro;
    }

    public static String getUFCPF(String pCpf) {
        String uf = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pCpf.equals(dados.get(i).usuCpf)) {

                uf = dados.get(i).usuUf;
            }
        }
        return uf;
    }

    public static String getCidadeCPF(String pCpf) {
        String cidade = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pCpf.equals(dados.get(i).usuCpf)) {

                cidade = dados.get(i).usuCidade;
            }
        }
        return cidade;
    }

    public static String getNrCasaCPF(String pCpf) {
        String casa = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pCpf.equals(dados.get(i).usuCpf)) {

                casa = dados.get(i).usuNumero;
            }
        }
        return casa;
    }

    public static String getEmailCPF(String pCpf) {
        String email = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pCpf.equals(dados.get(i).usuCpf)) {

                email = dados.get(i).usuEmail;
            }
        }
        return email;
    }

    public static String getComplementoCPF(String pCpf) {
        String complemento = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (pCpf.equals(dados.get(i).usuCpf)) {

                complemento = dados.get(i).usuComplemento;
            }
        }
        return complemento;
    }

    public static void remove(DadosOcorrencias d) {
        dados.remove(d);
    }

    public static int getQuantidadeUsuarios(String retorno){
        String VetorUsuarios[] = retorno.split("///");
        String Usuario1 = VetorUsuarios[0];
        String UsuarioOne[] = Usuario1.split("//");
        String tamanho = UsuarioOne[0];
        int qtdUsuario = Integer.parseInt(tamanho);
        return qtdUsuario;
    }

    public static void deleteAllArrayUsuarios() {
        dados.clear();
    }

    public static ArrayList<DadosUsuarios> getListaUsuarios () {
        {
            return dados;
        }

    }

}
