package appocorrencias.com.appocorrencias.ListView;

/**
 * Created by PamelaNycoly on 06/05/2017.
 */
public class DadosOcorrencias {

    public String nrOcorrencia;
    public String cpf;
    public String rua;
    public String bairro;
    public String cidade;
    public String uf;
    public String descricao;
    public String data;
    public String tipo;
    public String anonimo;
    public String apelido;


    public DadosOcorrencias(String pNrOcorrencia, String pCpf, String pRua, String pBairro, String pCidade,
                            String pUf, String pDescricao, String pData, String pTipo, String pAnonimo, String pApelido) {

        this.nrOcorrencia = pNrOcorrencia;
        this.cpf = pCpf;
        this.rua = pRua;
        this.bairro = pBairro;
        this.cidade = pCidade;
        this.uf = pUf;
        this.descricao = pDescricao;
        this.data = pData;
        this.tipo = pTipo;
        this.anonimo = pAnonimo;
        this.apelido = pApelido;
    }

    public String getNrOcorrencia() {
        return nrOcorrencia;
    }

    public String getBairro() {
        return bairro;
    }

    public String getData() {
        return data;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getRua() {
        return rua;
    }

    public String getApelido() {
        return apelido;
    }

    public String getAnonimo() {
        return anonimo;
    }

    public String getCPF() {
        return cpf;
    }

    @Override
    public String toString() {
        return "\nusuNumero: " + nrOcorrencia + "\nbairroBuscarOcorrencia: " + bairro + "\ndata: " + data;
    }

    public boolean equals(DadosOcorrencias m) {
        return this.nrOcorrencia == null ? m.nrOcorrencia == null : this.nrOcorrencia.equals(m.nrOcorrencia);
    }
}
