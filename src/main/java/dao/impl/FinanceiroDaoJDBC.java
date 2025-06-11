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
}
