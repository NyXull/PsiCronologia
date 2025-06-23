package model.entities;

public class Relatorio {

    private Integer idRelatorio;
    private Integer idPsicologo;
    private String descricao;
    private String caminhoArquivo;
    private String nomeModelo;

    public Relatorio() {
    }

    public Relatorio(Integer idRelatorio, Integer idPsicologo, String descricao, String caminhoArquivo,
                     String nomeModelo) {
        this.idRelatorio = idRelatorio;
        this.idPsicologo = idPsicologo;
        this.descricao = descricao;
        this.caminhoArquivo = caminhoArquivo;
        this.nomeModelo = nomeModelo;
    }

    public Integer getIdRelatorio() {
        return this.idRelatorio;
    }

    public void setIdRelatorio(Integer idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public Integer getIdPsicologo() {
        return this.idPsicologo;
    }

    public void setIdPsicologo(Integer idPsicologo) {
        this.idPsicologo = idPsicologo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminhoArquivo() {
        return this.caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public String getNomeModelo() {
        return this.nomeModelo;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }
}
