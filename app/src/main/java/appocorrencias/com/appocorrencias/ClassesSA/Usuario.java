package appocorrencias.com.appocorrencias.ClassesSA;

/**
 * Created by Jeanderson on 14/05/2017.
 */

public class Usuario {



    public Usuario(){}

    private String cpfUsuario;
    private String senhaUsuario;
    private String nomeUsuario;
    private String bairro;
    private String token;




    //SETS
    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setToken(String token) {
        this.token = token;
    }



    //GETS
    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public String getToken() {
        return token;
    }

    public void  construir_usuarios(){



    }


}
