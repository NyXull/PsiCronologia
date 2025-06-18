package dao.impl;

import dao.interfaces.BibliotecaDAO;
import db.DB;
import db.DbException;
import model.entities.Biblioteca;
import util.SessaoUsuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaDaoJDBC implements BibliotecaDAO {

    private Connection conn;

    int idPsicologo = SessaoUsuario.getPsicologoLogado().getIdPsico();

    public BibliotecaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void salvarArquivo(Biblioteca objBiblioteca) {
        if (arquivoExiste(objBiblioteca.getIdPsicologo(), objBiblioteca.getNomeArquivo(), objBiblioteca.getCaminhoArquivo())) {
            return;
        }

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

    @Override
    public boolean psicologoTemArquivosSalvos(Integer idPsicologo) {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement("SELECT id FROM biblioteca WHERE id_psicologo = ?");

            pstm.setInt(1, idPsicologo);

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
    public List<Biblioteca> carregarArquivos(Integer idPsicologo) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Biblioteca> listaBiblioteca = new ArrayList<>();

        try {
            pstm = conn.prepareStatement("SELECT id, id_psicologo, caminho_arquivo, nome_arquivo FROM biblioteca WHERE id_psicologo = ?");

            pstm.setInt(1, idPsicologo);

            rs = pstm.executeQuery();

            while (rs.next()) {
                Biblioteca biblioteca = new Biblioteca();
                biblioteca.setIdBiblioteca(rs.getInt("id"));
                biblioteca.setIdPsicologo(rs.getInt("id_psicologo"));
                biblioteca.setCaminhoArquivo(rs.getString("caminho_arquivo"));
                biblioteca.setNomeArquivo(rs.getString("nome_arquivo"));
                listaBiblioteca.add(biblioteca);
            }

            return listaBiblioteca;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pstm);
        }
    }

    @Override
    public boolean arquivoExiste(Integer idPsicologo, String nomeArquivo, String caminhoArquivo) {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement(
                    "SELECT COUNT(*) FROM biblioteca WHERE id_psicologo = ? AND nome_arquivo = ? AND caminho_arquivo = ?"
            );
            pstm.setInt(1, idPsicologo);
            pstm.setString(2, nomeArquivo);
            pstm.setString(3, caminhoArquivo);

            rs = pstm.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pstm);
        }
    }

    @Override
    public void excluirArquivo(Integer idPsicologo, String nomeArquivo, String caminhoArquivo) {
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(
                    "DELETE FROM biblioteca WHERE id_psicologo = ? AND nome_arquivo = ? AND caminho_arquivo = ?"
            );

            pstm.setInt(1, idPsicologo);
            pstm.setString(2, nomeArquivo);
            pstm.setString(3, caminhoArquivo);

            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(pstm);
        }
    }
}
