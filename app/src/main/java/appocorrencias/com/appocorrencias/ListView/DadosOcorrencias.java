package appocorrencias.com.appocorrencias.ListView;

/**
 * Created by PamelaNycoly on 06/05/2017.
 */
public class DadosOcorrencias {
    public String NrOcorrencia;
    public String Cpf;
    public String Rua;
    public String Bairro;
    public String Cidade;
    public String UF;
    public String Descricao;
    public String Data;
    public String Tipo;
    public String Anonimo;
    public String Apelido;


    public DadosOcorrencias(String NrOcorrencia, String Cpf, String Rua, String Bairro, String Cidade,
                            String UF, String Descricao, String Data, String Tipo, String Anonimo, String Apelido) {

        this.NrOcorrencia = NrOcorrencia;
        this.Cpf = Cpf;
        this.Rua = Rua;
        this.Bairro = Bairro;
        this.Cidade = Cidade;
        this.UF = UF;
        this.Descricao = Descricao;
        this.Data = Data;
        this.Tipo = Tipo;
        this.Anonimo = Anonimo;
        this.Apelido = Apelido;
    }

    public String getNrOcorrencia() {
        return NrOcorrencia;
    }

    public String getBairro() {
        return Bairro;
    }

    public String getData() {
        return Data;
    }

    public String getTipo() {
        return Tipo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public String getRua() {
        return Rua;
    }

    public String getApelido() {
        return Apelido;
    }

    public String getAnonimo() {
        return Anonimo;
    }

    public String getCPF() {
        return Cpf;
    }

    @Override
    public String toString() {
        return "\nNumero: " + NrOcorrencia + "\nBairro: " + Bairro + "\nData: " + Data;
    }

    public boolean equals(DadosOcorrencias m) {
        return this.NrOcorrencia == null ? m.NrOcorrencia == null : this.NrOcorrencia.equals(m.NrOcorrencia);
    }
}
