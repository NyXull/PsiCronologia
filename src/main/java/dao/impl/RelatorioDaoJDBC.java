package dao.impl;

import dao.interfaces.RelatorioDAO;
import db.DB;
import db.DbException;
import model.entities.Relatorio;
import util.SessaoUsuario;

import java.sql.*;

public class RelatorioDaoJDBC implements RelatorioDAO {

    private Connection conn;

    int idPsicologo = SessaoUsuario.getPsicologoLogado().getIdPsico();

    public RelatorioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void salvarRelatorio(Relatorio objRelatorio) {
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement("INSERT INTO relatorio(id_psicologo, descricao, caminho_arquivo, " +
                    "nome_modelo) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, idPsicologo);
            pstm.setString(2, objRelatorio.getDescricao());
            pstm.setString(3, objRelatorio.getCaminhoArquivo());
            pstm.setString(4, objRelatorio.getNomeModelo());

            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                objRelatorio.setIdRelatorio(id);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pstm);
        }
    }
}
