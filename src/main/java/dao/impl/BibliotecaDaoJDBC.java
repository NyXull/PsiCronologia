package dao.impl;

import dao.interfaces.BibliotecaDAO;
import db.DB;
import db.DbException;
import model.entities.Biblioteca;
import util.SessaoUsuario;

import java.sql.*;

public class BibliotecaDaoJDBC implements BibliotecaDAO {

    private Connection conn;

    int idPsicologo = SessaoUsuario.getPsicologoLogado().getIdPsico();

    public BibliotecaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void salvarArquivo(Biblioteca objBiblioteca) {
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement("INSERT INTO biblioteca(id_psicologo, caminho_arquivo, nome_arquivo) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, idPsicologo);
            pstm.setString(2, objBiblioteca.getCaminhoArquivo());
            pstm.setString(3, objBiblioteca.getNomeArquivo());

            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                objBiblioteca.setIdBiblioteca(id);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pstm);
        }
    }
}
