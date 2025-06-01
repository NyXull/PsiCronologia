package dao.impl;

import dao.interfaces.ProntuarioDAO;
import db.DB;
import db.DbException;
import model.entities.Prontuario;
import util.SessaoPaciente;

import java.sql.*;

public class ProntuarioDaoJDBC implements ProntuarioDAO {

    private Connection conn;

    int idPaciente = SessaoPaciente.getPaciente().getIdPaciente();

    public ProntuarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void salvarProntuario(Prontuario objProntuario) {
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement("INSERT INTO prontuario (id_paciente, data_atendimento, descricao, " +
                            "caminho_arquivo, id_sessao, id_ordem) " +
                            "SELECT ?, ?, ?, ?, ?, COALESCE(MAX(id_ordem), 0) + 1 FROM prontuario WHERE id_sessao = ?",
                    Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, idPaciente);
            pstm.setDate(2, new java.sql.Date(objProntuario.getDataAtendimento().getTime()));
            pstm.setString(3, objProntuario.getDescricao());
            pstm.setString(4, objProntuario.getCaminhoArquivo());
            pstm.setInt(5, objProntuario.getIdSessao());
            pstm.setInt(6, objProntuario.getIdSessao());

            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                objProntuario.setIdProntuario(id);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pstm);
        }
    }

    @Override
    public boolean sessaoJaExiste(Integer sessao) {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement("select 1 from prontuario where id_paciente = ? and id_sessao = ?");

            pstm.setInt(1, idPaciente);
            pstm.setInt(2, sessao);

            rs = pstm.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pstm);
        }
    }

    @Override
    public int getProximoIdOrdem(int idSessao) {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement("SELECT COALESCE(MAX(id_ordem), 0) + 1 AS proximo_id_ordem FROM prontuario " +
                    "WHERE id_sessao = ?");

            pstm.setInt(1, idSessao);
            rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getInt("proximo_id_ordem");
            } else {
                return 1;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pstm);
        }
    }
}
