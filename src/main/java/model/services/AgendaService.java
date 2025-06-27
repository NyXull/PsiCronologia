package model.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dao.factory.DaoFactory;
import dao.interfaces.AgendaDAO;
import model.entities.Agenda;
import model.entities.Paciente;
import model.entities.Psicologo;
import util.enums.TipoRecorrencia;

public class AgendaService {
	
	private AgendaDAO dao = DaoFactory.createAgendaDao();
	
	public void agendar(LocalDate data, LocalTime horario, Psicologo psicologo, 
			Paciente paciente, String recorrencia) {
		
		LocalDateTime dataHora = LocalDateTime.of(data, horario);
		
		Optional<Agenda> existente = dao.buscarPorDataHoraEPsicologo(dataHora, psicologo);
		if (existente.isPresent()) {
			throw new IllegalStateException("Horário já ocupado");
		}
		
		Agenda novaAgenda = new Agenda(null, psicologo, paciente, data, horario, recorrencia);
		dao.inserir(novaAgenda);
	}
	
	public void cancelar(LocalDate data, LocalTime horario, Psicologo psicologo) {
		LocalDateTime dataHora = LocalDateTime.of(data, horario);
		dao.deletarPorDataHoraEPsicologo(dataHora, psicologo.getIdPsico());
	}
	
	public boolean estaAgendado(LocalDate data, LocalTime horario, Psicologo psicologo) {
        LocalDateTime dataHora = LocalDateTime.of(data, horario);
        return dao.buscarPorDataHoraEPsicologo(dataHora, psicologo).isPresent();
    }
	
	public List<Agenda> listarHorariosDoDia(LocalDate dia, Psicologo psicologo){
		return dao.buscarPorDiaEPsicologo(dia, psicologo.getIdPsico());
	}
	
	public List<LocalTime> buscarHorariosOcupados(LocalDate data, Psicologo psicologo) {
		return dao.buscarHorariosPorData(data, psicologo);
	}
	
	public boolean estaHorarioOcupado(LocalDate data, LocalTime hora, Psicologo psicologo) {
		return buscarHorariosOcupados(data, psicologo).contains(hora);
	}
	
	public Optional<Agenda> buscarAgendamento(LocalDate data, LocalTime hora, Psicologo psicologo) {
	    LocalDateTime dataHora = LocalDateTime.of(data, hora);
	    return dao.buscarPorDataHoraEPsicologo(dataHora, psicologo);
	}
	
	public void agendarRecorrente(LocalDate dataInicial, LocalTime horario, Psicologo psicologo, 
			Paciente paciente, TipoRecorrencia tipoRecorrencia, int repeticoes) {
		
		List<Agenda> agendamentos = new ArrayList<>();		
		LocalDate dataAtual = dataInicial;
		
		for (int i = 0; i < repeticoes; i++) {
			LocalDateTime dataHora = LocalDateTime.of(dataAtual, horario);
			Optional<Agenda> existente = dao.buscarPorDataHoraEPsicologo(dataHora, psicologo);
			
			if (existente.isPresent()) {
				throw new IllegalStateException("Já existe agendamento para: " + dataHora);
			}
			String recorrenciaFormatada = tipoRecorrencia.name().toLowerCase();
			agendamentos.add(new Agenda(null, psicologo, paciente, dataAtual, horario, recorrenciaFormatada));
			dataAtual = tipoRecorrencia.proximaData(dataAtual);			
		}
		dao.inserirMultiplos(agendamentos);		
	}
	
	public void cancelarRecorrente(LocalTime horario, Psicologo psicologo, Optional<String> tipoRecorrencia) {
		dao.deletarRecorrente(horario, psicologo.getIdPsico(), tipoRecorrencia);
	}
}