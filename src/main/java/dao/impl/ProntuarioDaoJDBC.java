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
            pstm = conn.prepareStatement("insert into prontuario"
                    + "(id_paciente, data_atendimento, descricao, caminho_arquivo, sessao) "
                    + "values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, idPaciente);
            pstm.setDate(2, new java.sql.Date(objProntuario.getDataAtendimento().getTime()));
            pstm.setString(3, objProntuario.getDescricao());
            pstm.setString(4, objProntuario.getCaminhoArquivo());
            pstm.setInt(5, objProntuario.getSessao());

            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                objProntuario.setIdPaciente(id);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pstm);
        }
    }

}
