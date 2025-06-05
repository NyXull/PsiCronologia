package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dao.interfaces.AgendaDAO;
import db.DB;
import db.DbException;
import model.entities.Agenda;
import model.entities.Paciente;
import model.entities.Psicologo;

public class AgendaDaoJDBC implements AgendaDAO {

	private Connection conn;

	public AgendaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Agenda agenda) {
		PreparedStatement pstm = null;

		try {
			pstm = conn.prepareStatement("""
					INSERT INTO agenda (id_psicologo, id_paciente, 
					data, horario, recorrencia) VALUES (?, ?, ?, ?, ?)
					""",
					Statement.RETURN_GENERATED_KEYS);
			
			pstm.setInt(1, agenda.getPsicologo().getIdPsico());
			pstm.setInt(2, agenda.getPaciente().getIdPaciente());
			pstm.setDate(3, Date.valueOf(agenda.getData()));
			pstm.setTime(4, Time.valueOf(agenda.getHorario()));
			pstm.setString(5,  agenda.getRecorrencia());
			
			pstm.executeUpdate();
		} 
		catch (SQLException e) {
			 throw new DbException("Erro ao inserir agenda" + e.getMessage());
		} 
		finally {
			DB.closeStatement(pstm);
		}
	}

	@Override
	public void deletarPorDataHoraEPsicologo(LocalDateTime dataHora, Integer idPsicologo) {
		PreparedStatement pstm = null;

		try {
			pstm = conn.prepareStatement("DELETE FROM agenda WHERE data = ? AND horario = ? AND id_psicologo = ?");
		
			pstm.setDate(1, Date.valueOf(dataHora.toLocalDate()));
			pstm.setTime(2, Time.valueOf(dataHora.toLocalTime()));
			pstm.setInt(3, idPsicologo);
			
			pstm.executeUpdate();
		} 
		catch (SQLException e) {
			 throw new DbException("Erro ao deletar agendamento" + e.getMessage());
		} 
		finally {
			DB.closeStatement(pstm);
		}
	}
	
	@Override	
	public Optional<Agenda> buscarPorDataHoraEPsicologo(LocalDateTime dataHora, Psicologo psicologo) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			pstm = conn.prepareStatement("""
					SELECT a.*, p.nome AS nomePaciente
					FROM agenda a
					INNER JOIN paciente p ON a.id_paciente = p.id
					WHERE a.data = ? AND a.horario = ? AND a.id_psicologo = ?
				""");
			
			pstm.setDate(1, Date.valueOf(dataHora.toLocalDate()));
			pstm.setTime(2, Time.valueOf(dataHora.toLocalTime()));
			pstm.setInt(3, psicologo.getIdPsico());
			
			rs = pstm.executeQuery();

			if (rs.next()) {
				Agenda ag = instanciarAgenda(rs);
				
				return Optional.of(ag);			
			}
			
			return Optional.empty();
		}
		catch (SQLException e) {
			throw new DbException("Erro ao buscar agendamento" + e.getMessage());
		}
		finally {
			DB.closeStatement(pstm);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Agenda> buscarPorDiaEPsicologo(LocalDate dia, Integer idPsicologo) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Agenda> listaAgenda = new ArrayList<>();

		try {
			pstm = conn.prepareStatement("SELECT * FROM agenda WHERE data = ? AND id_psicologo = ?");
			pstm.setDate(1, Date.valueOf(dia));
			pstm.setInt(2, idPsicologo);
			
			rs = pstm.executeQuery();

			while (rs.next()) {				
				listaAgenda.add(instanciarAgenda(rs));
			}
			
			return listaAgenda;
		} 
		catch (SQLException e) {
			throw new DbException("Erro ao buscar agenda do dia" + e.getMessage());
		} 
		finally {
			DB.closeStatement(pstm);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<LocalTime> buscarHorariosPorData(LocalDate data, Psicologo psicologo) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<LocalTime> horarios = new ArrayList<>();
		
		try {
			pstm = conn.prepareStatement("SELECT horario FROM agenda WHERE data = ? AND id_psicologo = ?");
			pstm.setDate(1, Date.valueOf(data));
			pstm.setInt(2, psicologo.getIdPsico());			
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				horarios.add(rs.getTime("horario").toLocalTime());
			}
			
			return horarios;
		}
		catch (SQLException e) {
			throw new DbException("Erro ao buscar horários ocupados: " + e.getMessage());			
		} 
		finally {
			DB.closeStatement(pstm);
			DB.closeResultSet(rs);
		}
	}
	
	private Agenda instanciarAgenda(ResultSet rs) throws SQLException {
		Agenda ag = new Agenda();
		
		ag.setId(rs.getInt("id"));		
		ag.setData(rs.getDate("data").toLocalDate());
		ag.setHorario(rs.getTime("horario").toLocalTime());
		ag.setRecorrencia(rs.getString("recorrencia"));
		
		Psicologo psicologo = new Psicologo(rs.getInt("id_psicologo"));
		ag.setPsicologo(psicologo);
		
		Paciente paciente = new Paciente();
		paciente.setIdPaciente(rs.getInt("id_paciente"));
		paciente.setNomePaciente(rs.getString("nomePaciente"));
		ag.setPaciente(paciente);
		
		return ag;
	}

	@Override
	public void inserirMultiplos(List<Agenda> agendamento) {
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement("""
					INSERT INTO agenda (id_psicologo, id_paciente, data, horario, recorrencia) VALUES (?, ?, ?, ?, ?)
					""");
			
			for (Agenda ag : agendamento) {
				pstm.setInt(1, ag.getPsicologo().getIdPsico());
				pstm.setInt(2, ag.getPaciente().getIdPaciente());
				pstm.setDate(3, Date.valueOf(ag.getData()));
				pstm.setTime(4, Time.valueOf(ag.getHorario()));
				pstm.setString(5, ag.getRecorrencia());
				pstm.addBatch();
			}
			
			pstm.executeBatch();
		}
		catch (SQLException e) {
			throw new DbException("Erro ao inserir múltiplos agendamentos: " + e.getMessage());
		} 
		finally {
			DB.closeStatement(pstm);
		}		
	}
	
	@Override
	public void deletarRecorrente(LocalTime horario, Integer idPsicologo, Optional<String> tipoRecorrencia) {
		PreparedStatement pstm = null;
		
		try {			
			if(tipoRecorrencia.isEmpty()) {
				pstm = conn.prepareStatement("DELETE FROM agenda WHERE horario = ? AND id_psicologo = ? AND recorrencia IS NOT NULL");
				pstm.setTime(1, Time.valueOf(horario));
				pstm.setInt(2, idPsicologo);
			}
			else {
				pstm = conn.prepareStatement("DELETE FROM agenda WHERE horario = ? AND id_psicologo = ? AND recorrencia = ?");
				pstm.setTime(1, Time.valueOf(horario));
				pstm.setInt(2,  idPsicologo);
				pstm.setString(3, tipoRecorrencia.get());
			}			
			
			pstm.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException("Erro ao deletar agendamentos recorrentes: " + e.getMessage());
		} 
		finally {
			DB.closeStatement(pstm);
		}		
	}
}