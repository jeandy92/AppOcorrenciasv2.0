package appocorrencias.com.appocorrencias.ListView;

/**
 * Created by Jeanderson on 28/05/2017.
 */

public class DadosUsuarios {

    String usuCpf;
    String usuSenha;
    String usuNome;
    String usuTelefone;
    String usuEmail;
    String usuRua;
    String usuNumero;
    String usuBairro;
    String usuCidade;
    String usuCep;
    String usuUf;
    String usuComplemento;
    String usuNascimento;



    public DadosUsuarios(String pCpf, String pSenha, String pNome, String pTelefone, String pEmail, String pRua, String pNumero,
                         String pBairro, String pCidade, String pCep, String pUf, String pComplemento, String pNascimento) {
        this.usuCpf = pCpf;
        this.usuSenha = pSenha;
        this.usuNome = pNome;
        this.usuTelefone = pTelefone;
        this.usuEmail = pEmail;
        this.usuRua = pRua;
        this.usuNumero = pNumero;
        this.usuBairro = pBairro;
        this.usuCidade = pCidade;
        this.usuCep = pCep;
        this.usuUf = pUf;
        this.usuComplemento = pComplemento;
        this.usuNascimento = pNascimento;
    }

    public String getUsuNome() {
        return usuNome;
    }

    public void setUsuNome(String usuNome) {
        this.usuNome = usuNome;
    }

    public String getCpf() {
        return usuCpf;
    }

    public void setCpf(String cpf) {
        this.usuCpf = cpf;
    }

    public String getUsuSenha() {
        return usuSenha;
    }

    public void setUsuSenha(String usuSenha) {
        this.usuSenha = usuSenha;
    }

    public String getDataNascimento() {
        return usuNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.usuNascimento = dataNascimento;
    }

    public String getRua() {
        return usuRua;
    }

    public void setRua(String rua) {
        this.usuRua = rua;
    }

    public String getUsuTelefone() {
        return usuTelefone;
    }

    public void setUsuTelefone(String usuTelefone) {
        this.usuTelefone = usuTelefone;
    }

    public String getUsuCep() {
        return usuCep;
    }

    public void setUsuCep(String usuCep) {
        this.usuCep = usuCep;
    }

    public String getBairro() {
        return usuBairro;
    }

    public void setBairro(String bairro) {
        this.usuBairro = bairro;
    }

    public String getCidade() {
        return usuCidade;
    }

    public void setCidade(String cidade) {
        this.usuCidade = cidade;
    }

    public String getUf() {
        return usuUf;
    }

    public void setUf(String uf) {
        this.usuUf = uf;
    }

    public String getNumeroCasa() {
        return usuNumero;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.usuNumero = numeroCasa;
    }

    public String getUsuEmail() {
        return usuEmail;
    }

    public void setUsuEmail(String usuEmail) {
        this.usuEmail = usuEmail;
    }

    public String getUsuComplemento() {
        return usuComplemento;
    }

    public void setUsuComplemento(String usuComplemento) {
        this.usuComplemento = usuComplemento;
    }
}