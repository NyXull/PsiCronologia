package model.entities;

public class Biblioteca {

    private Integer idBiblioteca;
    private Integer idPsicologo;
    private String caminhoArquivo;
    private String nomeArquivo;

    public Biblioteca() {
    }

    public Biblioteca(Integer idBiblioteca, Integer idPsicologo, String caminhoArquivo, String nomeArquivo) {
        this.idBiblioteca = idBiblioteca;
        this.idPsicologo = idPsicologo;
        this.caminhoArquivo = caminhoArquivo;
        this.nomeArquivo = nomeArquivo;
    }

    public Integer getIdBiblioteca() {
        return this.idBiblioteca;
    }

    public void setIdBiblioteca(Integer idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public Integer getIdPsicologo() {
        return this.idPsicologo;
    }

    public void setIdPsicologo(Integer idPsicologo) {
        this.idPsicologo = idPsicologo;
    }

    public String getCaminhoArquivo() {
        return this.caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public String getNomeArquivo() {
        return this.nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}
