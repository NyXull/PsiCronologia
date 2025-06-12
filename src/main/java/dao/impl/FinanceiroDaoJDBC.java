package dao.impl;

import dao.interfaces.FinanceiroDAO;
import db.DB;
import db.DbException;
import model.entities.Financeiro;
import util.SessaoPaciente;

import java.sql.*;

public class FinanceiroDaoJDBC implements FinanceiroDAO {

    private Connection conn;

    int idPaciente = SessaoPaciente.getPaciente().getIdPaciente();

    public FinanceiroDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void salvarInformacoesPagamento(Financeiro objFinanceiro) {
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement("INSERT INTO financeiro(id_paciente, valor_sessao, qtd_sessao, data_vencimento, status, mes_status) VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, idPaciente);
            pstm.setBigDecimal(2, objFinanceiro.getValorSessao());
            pstm.setInt(3, objFinanceiro.getQuantidadeSessao());
            pstm.setDate(4, new java.sql.Date(objFinanceiro.getDataVencimento().getTime()));
            pstm.setString(5, objFinanceiro.getStatusPagamento());
            pstm.setString(6, objFinanceiro.getMesStatusPagamento());

            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                objFinanceiro.setIdFinanceiro(id);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pstm);
        }
    }

    @Override
    public Financeiro carregarInformacoesPagamento(Integer idPaciente) {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement("SELECT id, id_paciente, valor_sessao, qtd_sessao, data_vencimento, status, mes_status FROM financeiro WHERE id_paciente = ? ORDER BY data_vencimento DESC LIMIT 1");

            pstm.setInt(1, idPaciente);
            rs = pstm.executeQuery();

            if (rs.next()) {
                Financeiro financeiro = new Financeiro();
                financeiro.setIdFinanceiro(rs.getInt("id"));
                financeiro.setIdPaciente(rs.getInt("id_paciente"));
                financeiro.setValorSessao(rs.getBigDecimal("valor_sessao"));
                financeiro.setQuantidadeSessao(rs.getInt("qtd_sessao"));
                financeiro.setDataVencimento(rs.getDate("data_vencimento"));
                financeiro.setStatusPagamento(rs.getString("status"));
                financeiro.setMesStatusPagamento(rs.getString("mes_status"));
                return financeiro;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar informações financeiras do paciente: " + e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pstm);
        }
    }

    @Override
    public boolean informacaoPagamentoJaExiste(Integer idPaciente) {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement("SELECT id FROM financeiro WHERE id_paciente = ?");

            pstm.setInt(1, idPaciente);

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
    public void atualizarInformacoesPagamento(Financeiro objFinanceiro) {
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(
                    "UPDATE financeiro SET valor_sessao = ?, qtd_sessao = ?, data_vencimento = ?, status = ?, mes_status = ?  WHERE id_paciente = ?"
            );

            pstm.setBigDecimal(1, objFinanceiro.getValorSessao());
            pstm.setInt(2, objFinanceiro.getQuantidadeSessao());
            pstm.setDate(3, new java.sql.Date(objFinanceiro.getDataVencimento().getTime()));
            pstm.setString(4, objFinanceiro.getStatusPagamento());
            pstm.setString(5, objFinanceiro.getMesStatusPagamento());
            pstm.setInt(6, objFinanceiro.getIdPaciente());

            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pstm);
        }
    }
}
