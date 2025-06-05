package model.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agenda {
	
	private Integer id;
	private Psicologo psicologo;
	private Paciente paciente;
	private LocalDate data;
	private LocalTime horario;
	private String recorrencia;
	
	public Agenda() {		
	}

	public Agenda(Integer id, Psicologo psicologo, Paciente paciente, LocalDate data, LocalTime horario,
			String recorrencia) {
		super();
		this.id = id;
		this.psicologo = psicologo;
		this.paciente = paciente;
		this.data = data;
		this.horario = horario;
		this.recorrencia = recorrencia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Psicologo getPsicologo() {
		return psicologo;
	}

	public void setPsicologo(Psicologo psicologo) {
		this.psicologo = psicologo;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	public String getRecorrencia() {
		return recorrencia;
	}

	public void setRecorrencia(String recorrencia) {
		this.recorrencia = recorrencia;
	}		
}