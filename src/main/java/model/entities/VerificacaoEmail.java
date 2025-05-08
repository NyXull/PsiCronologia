package model.entities;

import java.time.Instant;

public class VerificacaoEmail {

	private String email;
	private String uuid;
	private Instant criadoEm;
	
	public VerificacaoEmail() {		
	}

	public VerificacaoEmail(String email, String uuid, Instant criadoEm) {
		super();
		this.email = email;
		this.uuid = uuid;
		this.criadoEm = criadoEm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Instant getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Instant criadoEm) {
		this.criadoEm = criadoEm;
	}	
}