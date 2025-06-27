package model.entities;

import java.util.Date;

public class Prontuario {

    private Integer idProntuario;
    private Integer idPaciente;
    private Date dataAtendimento;
    private String descricao;
    private String caminhoArquivo;
    private Integer idSessao;
    private Integer idOrdem;

    public Prontuario() {
    }

    public Prontuario(Integer idProntuario, Integer idPaciente, Date dataAtendimento, String descricao,
                      String caminhoArquivo, Integer idSessao, Integer idOrdem) {
        this.idProntuario = idProntuario;
        this.idPaciente = idPaciente;
        this.dataAtendimento = dataAtendimento;
        this.descricao = descricao;
        this.caminhoArquivo = caminhoArquivo;
        this.idSessao = idSessao;
        this.idOrdem = idOrdem;
    }

    public Integer getIdProntuario() {
        return idProntuario;
    }

    public void setIdProntuario(Integer idProntuario) {
        this.idProntuario = idProntuario;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Date getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(Date dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public Integer getIdSessao() {
        return idSessao;
    }

    public void setIdSessao(Integer idSessao) {
        this.idSessao = idSessao;
    }

    public Integer getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(Integer idOrdem) {
        this.idOrdem = idOrdem;
    }
}
