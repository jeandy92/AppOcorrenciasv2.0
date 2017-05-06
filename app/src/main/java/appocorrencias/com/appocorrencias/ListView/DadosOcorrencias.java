package appocorrencias.com.appocorrencias.ListView;

/**
 * Created by PamelaNycoly on 06/05/2017.
 */
public class DadosOcorrencias {
    public String NrOcorrencia;
    public String CPF;
    public String Rua;
    public String Bairro;
    public String Cidade;
    public String UF;
    public String Descricao;
    public String Data;
    public String Tipo;
    public String Anonimo;


    public DadosOcorrencias(String NrOcorrencia, String CPF, String Rua, String Bairro, String Cidade,
                           String UF, String Descricao, String Data, String Tipo, String Anonimo) {

        this.NrOcorrencia = NrOcorrencia;
        this.CPF = CPF;
        this.Rua = Rua;
        this.Bairro = Bairro;
        this.Cidade = Cidade;
        this.UF = UF;
        this.Descricao = Descricao;
        this.Data = Data;
        this.Tipo = Tipo;
        this.Anonimo = Anonimo;
    }

    public String getNrOcorrencia(String NrOcorrencia) {
        return NrOcorrencia;
    }

    public String getBairro(String Bairro) {
        return Bairro;
    }

    public String getData(String Data) {
        return Data;
    }

    public String getTipo() {
        return Tipo;
    }

    public String getDescricao() {
        return Descricao;
    }

    @Override
    public String toString() {
        return "\nNumero: " + NrOcorrencia + "\nBairro: " + Bairro + "\nData: " + Data;
    }

    public boolean equals(DadosOcorrencias m) {
        return this.NrOcorrencia == null ? m.NrOcorrencia == null : this.NrOcorrencia.equals(m.NrOcorrencia);
    }
}
