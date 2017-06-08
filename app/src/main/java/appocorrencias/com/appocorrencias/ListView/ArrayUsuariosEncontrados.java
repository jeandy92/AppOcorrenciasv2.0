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
    public static String getNomeCPF(String CPF) {
        String nome = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (CPF.equals(dados.get(i).CPFUsu)) {

                nome = dados.get(i).Nome;
            }
        }
        return nome;
    }

    public static String getNascimentoCPF(String CPF) {
        String nascimento = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (CPF.equals(dados.get(i).CPFUsu)) {

                nascimento = dados.get(i).Nascimento;
            }
        }
        return nascimento;
    }

    public static String getRuaCPF(String CPF) {
        String rua = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (CPF.equals(dados.get(i).CPFUsu)) {

                rua = dados.get(i).RuaUsu;
            }
        }
        return rua;
    }

    public static String getTelefoneCPF(String CPF) {
        String telefone = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (CPF.equals(dados.get(i).CPFUsu)) {

                telefone = dados.get(i).Telefone;
            }
        }
        return telefone;
    }

    public static String getCepCPF(String CPF) {
        String cep = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (CPF.equals(dados.get(i).CPFUsu)) {

                cep = dados.get(i).Cep;
            }
        }
        return cep;
    }

    public static String getBairroCPF(String CPF) {
        String bairro = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (CPF.equals(dados.get(i).CPFUsu)) {

                bairro = dados.get(i).BairroUsu;
            }
        }
        return bairro;
    }

    public static String getUFCPF(String CPF) {
        String uf = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (CPF.equals(dados.get(i).CPFUsu)) {

                uf = dados.get(i).UFUsu;
            }
        }
        return uf;
    }

    public static String getCidadeCPF(String CPF) {
        String cidade = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (CPF.equals(dados.get(i).CPFUsu)) {

                cidade = dados.get(i).CidadeUsu;
            }
        }
        return cidade;
    }

    public static String getNrCasaCPF(String CPF) {
        String casa = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (CPF.equals(dados.get(i).CPFUsu)) {

                casa = dados.get(i).Numero;
            }
        }
        return casa;
    }

    public static String getEmailCPF(String CPF) {
        String email = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (CPF.equals(dados.get(i).CPFUsu)) {

                email = dados.get(i).Email;
            }
        }
        return email;
    }

    public static String getComplementoCPF(String CPF) {
        String complemento = null;

        int n = dados.size();
        for (int i = 0; i < n; i++) {
            if (CPF.equals(dados.get(i).CPFUsu)) {

                complemento = dados.get(i).Complemento;
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
