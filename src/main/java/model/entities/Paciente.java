package model.entities;

import java.util.Date;

public class Paciente {

	private Integer idPaciente;
	private String cpf;
	private String nomePaciente; 
	private String emailPaciente;
	private Date  dataNascimento; 
	private String telefone;
	
	public Paciente() {		
	}

	public Paciente(Integer idPaciente, String cpf, String nomePaciente, String emailPaciente, Date dataNascimento,
			String telefone) {
		super();
		this.idPaciente = idPaciente;
		this.cpf = cpf;
		this.nomePaciente = nomePaciente;
		this.emailPaciente = emailPaciente;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
	}

	public Integer getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public String getEmailPaciente() {
		return emailPaciente;
	}

	public void setEmailPaciente(String emailPaciente) {
		this.emailPaciente = emailPaciente;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}