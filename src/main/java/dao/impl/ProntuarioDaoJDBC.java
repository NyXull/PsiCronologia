package dao.impl;

import dao.interfaces.ProntuarioDAO;
import db.DB;
import db.DbException;
import model.entities.Prontuario;
import util.SessaoPaciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                            "id_sessao, id_ordem) " +
                            "SELECT ?, ?, ?, ?, COALESCE(MAX(id_ordem), 0) + 1 FROM prontuario WHERE id_sessao = ?",
                    Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, idPaciente);
            pstm.setDate(2, new java.sql.Date(objProntuario.getDataAtendimento().getTime()));
            pstm.setString(3, objProntuario.getDescricao());
            
            pstm.setInt(4, objProntuario.getIdSessao());
            pstm.setInt(5, objProntuario.getIdSessao());

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
    public boolean sessaoJaExiste(Integer idSessao) {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement("select 1 from prontuario where id_paciente = ? and id_sessao = ?");

            pstm.setInt(1, idPaciente);
            pstm.setInt(2, idSessao);

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

    @Override
    public List<Prontuario> listarPorPaciente(Integer idPaciente) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Prontuario> listaProntuario = new ArrayList<>();

        try {
            pstm = conn.prepareStatement(
                    "SELECT id, id_paciente, data_atendimento, descricao, id_sessao, id_ordem " +
                            "FROM prontuario WHERE id_paciente = ? ORDER BY id_ordem DESC"
            );

            pstm.setInt(1, idPaciente);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Prontuario prontuario = new Prontuario();
                prontuario.setIdProntuario(rs.getInt("id"));
                prontuario.setIdPaciente(rs.getInt("id_paciente"));
                prontuario.setDataAtendimento(rs.getDate("data_atendimento"));
                prontuario.setDescricao(rs.getString("descricao"));
                prontuario.setIdSessao(rs.getInt("id_sessao"));
                prontuario.setIdOrdem(rs.getInt("id_ordem"));
                listaProntuario.add(prontuario);
            }
            return listaProntuario;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar prontuários do paciente: " + e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pstm);
        }
    }

    @Override
    public void atualizarProntuario(Prontuario objProntuario) {
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(
                    "UPDATE prontuario SET descricao = ? WHERE id_ordem = ?"
            );

            pstm.setString(1, objProntuario.getDescricao());
            pstm.setInt(2, objProntuario.getIdOrdem());

            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pstm);
        }
    }

    @Override
    public Integer getIdOrdem(Integer idOrdem) {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement("select prontuario.id_ordem from prontuario where id_ordem = ?");

            pstm.setInt(1, idOrdem);

            rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_ordem");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pstm);
        }
    }
}
