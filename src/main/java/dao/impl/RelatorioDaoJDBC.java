package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.RelatorioDAO;
import db.DB;
import db.DbException;
import model.entities.Relatorio;

public class RelatorioDaoJDBC implements RelatorioDAO {

	private final Connection conn;

	public RelatorioDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void salvar(Relatorio relatorio) {
		if (nomeModeloExiste(relatorio.getNomeModelo(), relatorio.getIdPsicologo())) {
			atualizar(relatorio);
			return;
		}

		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			pstm = conn.prepareStatement(
					"INSERT INTO relatorio (id_psicologo, descricao, nome_modelo) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pstm.setInt(1, relatorio.getIdPsicologo());
			pstm.setString(2, relatorio.getDescricao());
			pstm.setString(3, relatorio.getNomeModelo());

			pstm.executeUpdate();

			rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				relatorio.setId(rs.getInt(1));
			}
			DB.closeResultSet(rs);
		} catch (SQLException e) {
			throw new DbException("Erro ao salvar o modelo de relatório: " + e.getMessage());
		} finally {
			DB.closeStatement(pstm);
		}
	}

	@Override
	public void atualizar(Relatorio relatorio) {
		PreparedStatement pstm = null;

		try {
			pstm = conn
					.prepareStatement("UPDATE relatorio SET descricao = ? WHERE id_psicologo = ? AND nome_modelo = ?");

			pstm.setString(1, relatorio.getDescricao());
			pstm.setInt(2, relatorio.getIdPsicologo());
			pstm.setString(3, relatorio.getNomeModelo());

			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DbException("Erro ao atualizar o modelo de relatório: " + e.getMessage());
		} finally {
			DB.closeStatement(pstm);
		}
	}

	@Override
	public boolean nomeModeloExiste(String nomeModelo, Integer idPsicologo) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			pstm = conn.prepareStatement("SELECT 1 FROM relatorio WHERE nome_modelo = ? AND id_psicologo = ?");
			pstm.setString(1, nomeModelo);
			pstm.setInt(2, idPsicologo);
			rs = pstm.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new DbException("Erro ao verificar duplicidade de nome de modelo: " + e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstm);
		}
	}

	@Override
	public Relatorio buscarPorNomeModelo(Integer idPsicologo, String nomeModelo) {
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			pstm = conn.prepareStatement("SELECT * FROM relatorio WHERE nome_modelo = ? AND id_psicologo = ?");
			pstm.setString(1, nomeModelo);
			pstm.setInt(2, idPsicologo);

			rs = pstm.executeQuery();

			if (rs.next()) {
				Relatorio rel = new Relatorio();
				rel.setId(rs.getInt("id"));
				rel.setIdPsicologo(rs.getInt("id_psicologo"));
				rel.setDescricao(rs.getString("descricao"));
				rel.setNomeModelo(rs.getString("nome_modelo"));
				return rel;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException("Erro ao buscar relatório: " + e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstm);
		}
	}

	@Override
	public List<String> listarModelosPorPsicologo(Integer idPsicologo) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<String> modelos = new ArrayList<>();

		try {
			pstm = conn.prepareStatement(
					"SELECT nome_modelo FROM relatorio WHERE id_psicologo = ? ORDER BY nome_modelo ASC");
			pstm.setInt(1, idPsicologo);

			rs = pstm.executeQuery();

			while (rs.next()) {
				modelos.add(rs.getString("nome_modelo"));
			}
			return modelos;
		} catch (SQLException e) {
			throw new DbException("Erro ao listar modelos: " + e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pstm);
		}
	}

	@Override
	public void excluirRelatorio(Integer id) {
		PreparedStatement pstm = null;

		try {
			pstm = conn.prepareStatement("DELETE FROM relatorio WHERE id = ?");
			pstm.setInt(1, id);

			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DbException("Erro ao excluir relatório: " + e.getMessage());
		} finally {
			DB.closeStatement(pstm);
		}
	}
}