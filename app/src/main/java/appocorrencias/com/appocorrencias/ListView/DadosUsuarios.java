package appocorrencias.com.appocorrencias.ListView;

/**
 * Created by Jeanderson on 28/05/2017.
 */

public class DadosUsuarios {

    String CPFUsu;
    String Senha;
    String Nome;
    String Telefone;
    String Email;
    String RuaUsu;
    String Numero;
    String BairroUsu;
    String CidadeUsu;
    String Cep;
    String UFUsu;
    String Complemento;
    String Nascimento;



    public DadosUsuarios(String CPFUsu, String Senha, String Nome, String Telefone, String Email, String RuaUsu, String Numero,
                         String BairroUsu, String CidadeUsu, String Cep, String UFUsu, String Complemento, String Nascimento) {
        this.CPFUsu = CPFUsu;
        this.Senha = Senha;
        this.Nome = Nome;
        this.Telefone = Telefone;
        this.Email = Email;
        this.RuaUsu = RuaUsu;
        this.Numero = Numero;
        this.BairroUsu = BairroUsu;
        this.CidadeUsu = CidadeUsu;
        this.Cep = Cep;
        this.UFUsu = UFUsu;
        this.Complemento = Complemento;
        this.Nascimento = Nascimento;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public String getCpf() {
        return CPFUsu;
    }

    public void setCpf(String cpf) {
        this.CPFUsu = cpf;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        this.Senha = senha;
    }

    public String getDataNascimento() {
        return Nascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.Nascimento = dataNascimento;
    }

    public String getRua() {
        return RuaUsu;
    }

    public void setRua(String rua) {
        this.RuaUsu = rua;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        this.Telefone = telefone;
    }

    public String getCep() {
        return Cep;
    }

    public void setCep(String cep) {
        this.Cep = cep;
    }

    public String getBairro() {
        return BairroUsu;
    }

    public void setBairro(String bairro) {
        this.BairroUsu = bairro;
    }

    public String getCidade() {
        return CidadeUsu;
    }

    public void setCidade(String cidade) {
        this.CidadeUsu = cidade;
    }

    public String getUf() {
        return UFUsu;
    }

    public void setUf(String uf) {
        this.UFUsu = uf;
    }

    public String getNumeroCasa() {
        return Numero;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.Numero = numeroCasa;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        this.Complemento = complemento;
    }
}