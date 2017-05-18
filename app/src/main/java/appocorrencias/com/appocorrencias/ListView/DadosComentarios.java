package appocorrencias.com.appocorrencias.ListView;

/**
 * Created by PamelaNycoly on 06/05/2017.
 */
public class DadosComentarios {
    public String NrComentario;
    public String NrOcorrencia;
    public String CPF;
    public String Data;
    public String Hora;
    public String Apelido;
    public String Descricao;


    public DadosComentarios(String NrComentario, String NrOcorrencia, String CPF, String Data, String Hora,
                            String Apelido, String Descricao) {

        this.NrComentario = NrComentario;
        this.NrOcorrencia = NrOcorrencia;
        this.CPF = CPF;
        this.Data = Data;
        this.Hora = Hora;
        this.Apelido = Apelido;
        this.Descricao = Descricao;

    }

    public String getNrComentario() {
        return NrComentario;
    }

    public String getNrOcorrencia() {
        return NrOcorrencia;
    }

    public String getData() {
        return Data;
    }

    public String getHora() {
        return Hora;
    }

    public String getDescricao() {
        return Descricao;
    }


    public String getApelido() {
        return Apelido;
    }


    public boolean equals(DadosComentarios m) {
        return this.NrOcorrencia == null ? m.NrOcorrencia == null : this.NrOcorrencia.equals(m.NrOcorrencia);
    }
}
