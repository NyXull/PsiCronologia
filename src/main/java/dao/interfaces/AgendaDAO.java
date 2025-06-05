package dao.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import model.entities.Agenda;
import model.entities.Psicologo;

public interface AgendaDAO {
	
	void inserir(Agenda agenda);
	void inserirMultiplos(List<Agenda> agendamento);
	void deletarPorDataHoraEPsicologo(LocalDateTime dataHora, Integer idPsicologo);
	Optional<Agenda> buscarPorDataHoraEPsicologo(LocalDateTime dataHora, Psicologo psicologo);
	List<Agenda> buscarPorDiaEPsicologo(LocalDate dia, Integer idPsicologo);
	List<LocalTime> buscarHorariosPorData(LocalDate data, Psicologo psicologo);
	public void deletarRecorrente(LocalTime horario, Integer idPsicologo, Optional<String> tipoRecorrencia);
}