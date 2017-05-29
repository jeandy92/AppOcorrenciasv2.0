package appocorrencias.com.appocorrencias.ClassesSA;

/**
 * Created by Jeanderson on 14/05/2017.
 */

public class Usuario {

    private String cpf_usuario;

    public Usuario(){}

    String senha_usuario;
    String nome_usuario;
    String bairro;
    String token;




//SETS
    public void setCpf_usuario(String cpf_usuario) {
        this.cpf_usuario = cpf_usuario;
    }

    public void setSenha_usuario(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setToken(String token) {
        this.token = token;
    }



//GETS
    public String getSenha_usuario() {
        return senha_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCpf_usuario() {
        return cpf_usuario;
    }

    public String getToken() {
        return token;
    }

    public void  construir_usuarios(){



    }


}
