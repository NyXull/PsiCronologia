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
	public boolean cpfExiste(String cpf) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		try {
			pstm = conn.prepareStatement("SELECT COUNT(*) FROM paciente WHERE cpf = ?");
			pstm.setString(1, cpf);
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
			return false;
		}
		catch (SQLException e) {
	        throw new DbException(e.getMessage());
	    }
	    finally {
	        DB.closeResultSet(rs);
	        DB.closeStatement(pstm);
	    }
	}
}