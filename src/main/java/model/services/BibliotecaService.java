package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.BibliotecaDAO;
import model.entities.Biblioteca;

import java.util.List;

public class BibliotecaService {

    private BibliotecaDAO dao = DaoFactory.createBibliotecaDao();

    public void salvarArquivo(Biblioteca objBiblioteca) {
        dao.salvarArquivo(objBiblioteca);
    }

    public boolean psicologoTemArquivosSalvos(Integer idPsicologo) {
        return dao.psicologoTemArquivosSalvos(idPsicologo);
    }

    public List<Biblioteca> carregarArquivos(Integer idPsicologo) {
        return dao.carregarArquivos(idPsicologo);
    }

    public boolean arquivoExiste(Integer idPsicologo, String nomeArquivo, String caminhoArquivo) {
        return dao.arquivoExiste(idPsicologo, nomeArquivo, caminhoArquivo);
    }

    public void excluirArquivo(Integer idPsicologo, String nomeArquivo, String caminhoArquivo) {
        dao.excluirArquivo(idPsicologo, nomeArquivo, caminhoArquivo);
    }
}
