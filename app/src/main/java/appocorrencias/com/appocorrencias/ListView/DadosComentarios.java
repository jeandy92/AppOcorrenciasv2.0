package appocorrencias.com.appocorrencias.ListView;

import android.support.annotation.NonNull;

/**
 * Created by PamelaNycoly on 06/05/2017.
 */
public class DadosComentarios implements Comparable<DadosComentarios> {
    public String nrComentario;
    public String nrOcorrencia;
    public String cpf;
    public String data;
    public String hora;
    public String apelido;
    public String descricao;


    public DadosComentarios(String pNrComentario, String pNrOcorrencia, String pCpf, String pData, String pHora,
                            String pApelido, String pDescricao) {

        this.nrComentario = pNrComentario;
        this.nrOcorrencia = pNrOcorrencia;
        this.cpf = pCpf;
        this.data = pData;
        this.hora = pHora;
        this.apelido = pApelido;
        this.descricao = pDescricao;

    }

    public String getNrComentario() {
        return nrComentario;
    }

    public String getNrOcorrencia() {
        return nrOcorrencia;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCpf() {
        return cpf;
    }


    public String getApelido() {
        return apelido;
    }


    public boolean equals(DadosComentarios m) {
        return this.nrOcorrencia == null ? m.nrOcorrencia == null : this.nrOcorrencia.equals(m.nrOcorrencia);
    }

    @Override
    public int compareTo(@NonNull DadosComentarios pDados) {
        int NrAtual = Integer.parseInt(this.nrComentario);
        int Nrdado = Integer.parseInt(pDados.nrComentario);
        if(NrAtual < Nrdado){
            return -1;
        }
        else if(NrAtual > Nrdado){
            return 1;
        }
        return this.getNrComentario().compareToIgnoreCase(pDados.getNrComentario());
    }

}
