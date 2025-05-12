package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.interfaces.PacienteDAO;
import db.DB;
import db.DbException;
import model.entities.Paciente;

public class PacienteDaoJDBC implements PacienteDAO{

	private Connection conn;
	
	public PacienteDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void cadastrarPaciente(Paciente objPaciente) {
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement(
					"insert into paciente"
					+ "(cpf, nome, email, data_nascimento, telefone) "
					+ "values (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			pstm.setString(1, objPaciente.getCpf());
			pstm.setString(2, objPaciente.getNomePaciente());
			pstm.setString(3, objPaciente.getEmailPaciente());
			pstm.setDate(4, new java.sql.Date(objPaciente.getDataNascimento().getTime()));
			pstm.setString(5,  objPaciente.getTelefone());
			
			pstm.executeUpdate();
			
			ResultSet rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				objPaciente.setIdPaciente(id);
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(pstm);
		}		
	}

	@Override
	public Paciente buscarPorCpf(String cpf) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = conn.prepareStatement("SELECT * FROM paciente WHERE cpf = ?");
			pstm.setString(1, cpf);
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				Paciente paciente = instanciacaoPaciente(rs);
				return paciente;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstm);
		}
	}

	private Paciente instanciacaoPaciente(ResultSet rs) throws SQLException {
		Paciente paciente = new Paciente();
		paciente.setIdPaciente(rs.getInt("id"));
		paciente.setCpf(rs.getString("cpf"));
		paciente.setNomePaciente(rs.getString("nome"));
		paciente.setDataNascimento(rs.getDate("data_nascimento"));
		paciente.setTelefone(rs.getString("telefone"));
		return paciente;
	}

	@Override
	public boolean relacaoJaExiste(int idPsicologo, int idPaciente) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = conn.prepareStatement(
					"SELECT COUNT(*) FROM psicologo_paciente WHERE id_psicologo = ? AND id_paciente = ?");
			pstm.setInt(1, idPsicologo);
			pstm.setInt(2, idPaciente);
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
			return false;
		}
		catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeResultSet(rs);
	        DB.closeStatement(pstm);
	    }
	}

	@Override
	public void associarPsicologoPaciente(int idPsicologo, int idPaciente) {
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement(
					"INSERT INTO psicologo_paciente (id_psicologo, id_paciente) VALUES (?, ?)");
			pstm.setInt(1, idPsicologo);
			pstm.setInt(2, idPaciente);
			pstm.executeUpdate();
			
		}
		catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    } finally {
	        DB.closeStatement(pstm);
	    }
	}
}