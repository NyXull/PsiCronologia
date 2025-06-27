package model.entities;

public class Relatorio {
	
	private Integer id;
	private Integer idPsicologo;
	private String descricao;
	private String nomeModelo;
	
	public Relatorio() {		
	}

	public Relatorio(Integer id, Integer idPsicologo, String descricao, String nomeModelo) {
		super();
		this.id = id;
		this.idPsicologo = idPsicologo;
		this.descricao = descricao;
		this.nomeModelo = nomeModelo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdPsicologo() {
		return idPsicologo;
	}

	public void setIdPsicologo(Integer idPsicologo) {
		this.idPsicologo = idPsicologo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNomeModelo() {
		return nomeModelo;
	}

	public void setNomeModelo(String nomeModelo) {
		this.nomeModelo = nomeModelo;
	}	
}