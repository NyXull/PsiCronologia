package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import dao.VerificacaoEmailDAO;
import db.DbException;
import model.entities.VerificacaoEmail;

public class VerificacaoEmailDaoJDBC implements VerificacaoEmailDAO{

private Connection conn;
	
	public VerificacaoEmailDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void salvar(VerificacaoEmail verificacao) {
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement(
					"INSERT INTO verificacao_email "
					+ "(email, uuid, criado_em) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			pstm.setString(1, verificacao.getEmail());
			pstm.setString(2, verificacao.getUuid());
			pstm.setTimestamp(3, Timestamp.from(verificacao.getCriadoEm()));
			pstm.executeUpdate();			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}		
	}

	@Override
	public VerificacaoEmail buscarPorCodigo(String codigo) {
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement(
					"SELECT * FROM verificacao_email WHERE uuid = ?");
			
			pstm.setString(1, codigo);
			
			ResultSet rs = pstm.executeQuery();
			
			 if (rs.next()) {
	                return new VerificacaoEmail(
	                    rs.getString("email"),
	                    rs.getString("uuid"),
	                    rs.getTimestamp("criado_em").toInstant()
	                );
			}
			 return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}	
	}

	@Override
	public void deletarPorCodigo(String codigo) {
		PreparedStatement pstm = null;
		
		try {
			pstm = conn.prepareStatement(
					"DELETE FROM verificacao_email WHERE uuid = ?");
			
			pstm.setString(1, codigo);
			pstm.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}	
	}
}
