package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.ProntuarioDAO;
import model.entities.Prontuario;

public class ProntuarioService {

    private ProntuarioDAO dao = DaoFactory.createProntuarioDao();

    public void salvarProntuario(Prontuario objProntuario) {
        dao.salvarProntuario(objProntuario);
    }
}
