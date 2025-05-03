package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;
import db.DbException;
import model.Psicologo;

public class PsicologoDAO {

	Connection conn;
	
	public ResultSet autenticacaoPsico(Psicologo objPsicologo) {
		conn = new DB().getConnection();
		
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
}