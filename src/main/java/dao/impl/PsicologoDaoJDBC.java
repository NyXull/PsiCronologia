package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.PsicologoDAO;
import db.DB;
import db.DbException;
import model.entities.Psicologo;

public class PsicologoDaoJDBC implements PsicologoDAO{
	
private Connection conn;
	
	public PsicologoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public ResultSet autenticacaoPsico(Psicologo objPsicologo) {
		try {
			String sql = "select * from psicologo where email = ? and senha = ?";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, objPsicologo.getEmailPsico());
			pstm.setString(2, objPsicologo.getSenhaPsico());
			
			ResultSet rs = pstm.executeQuery();
			return rs;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public void cadastrarPsicologo(Psicologo objPsicologo) {
		PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement(
					"insert into psicologo"
					+ "(nome, email, senha) "
					+ "values (?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS); 			
			
			pstm.setString(1, objPsicologo.getNomePsico());
			pstm.setString(2, objPsicologo.getEmailPsico());
			pstm.setString(3, objPsicologo.getSenhaPsico());						
			
			pstm.executeUpdate();
			
			ResultSet rs = pstm.getGeneratedKeys();
	        if (rs.next()) {
	            int id = rs.getInt(1);
	            objPsicologo.setIdPsico(id);
	        }
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(pstm);		
		}
	}

	@Override
	public boolean emailExiste(String email) {
		PreparedStatement pstm = null;
	    ResultSet rs = null;
	   
	    try {
	        pstm = conn.prepareStatement("SELECT COUNT(*) FROM psicologo WHERE email = ?");
	        pstm.setString(1, email);
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