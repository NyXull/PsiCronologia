package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.BibliotecaDAO;
import model.entities.Biblioteca;

public class BibliotecaService {

    private BibliotecaDAO dao = DaoFactory.createBibliotecaDao();

    public void salvarArquivo(Biblioteca objBiblioteca) {
        dao.salvarArquivo(objBiblioteca);
    }
}
