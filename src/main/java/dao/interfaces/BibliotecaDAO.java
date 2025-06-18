package dao.interfaces;

import model.entities.Biblioteca;

import java.util.List;

public interface BibliotecaDAO {

    void salvarArquivo(Biblioteca objBiblioteca);

    boolean psicologoTemArquivosSalvos(Integer idPsicologo);

    List<Biblioteca> carregarArquivos(Integer idPsicologo);
}
