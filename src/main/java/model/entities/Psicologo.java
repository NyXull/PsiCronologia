package model.entities;

import java.util.Objects;

public class Psicologo {
	
	private Integer idPsico;
	private String nomePsico;
	private String emailPsico;
	private String senhaPsico;
	
	public Psicologo() {		
	}

	public Psicologo(Integer idPsico, String nomePsico, String emailPsico, String senhaPsico) {
		super();
		this.idPsico = idPsico;
		this.nomePsico = nomePsico;
		this.emailPsico = emailPsico;
		this.senhaPsico = senhaPsico;
	}

	public Integer getIdPsico() {
		return idPsico;
	}

	public void setIdPsico(Integer idPsico) {
		this.idPsico = idPsico;
	}

	public String getNomePsico() {
		return nomePsico;
	}

	public void setNomePsico(String nomePsico) {
		this.nomePsico = nomePsico;
	}

	public String getEmailPsico() {
		return emailPsico;
	}

	public void setEmailPsico(String emailPsico) {
		this.emailPsico = emailPsico;
	}

	public String getSenhaPsico() {
		return senhaPsico;
	}

	public void setSenhaPsico(String senhaPsico) {
		this.senhaPsico = senhaPsico;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPsico);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Psicologo other = (Psicologo) obj;
		return Objects.equals(idPsico, other.idPsico);
	}

	@Override
	public String toString() {
		return "Psicologo [idPsico=" + idPsico + ", nomePsico=" + nomePsico + ", emailPsico=" + emailPsico
				+ ", senhaPsico=" + senhaPsico + "]";
	}	
}