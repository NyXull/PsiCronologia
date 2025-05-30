package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.ProntuarioDAO;
import model.entities.Prontuario;

public class ProntuarioService {

    private ProntuarioDAO dao = DaoFactory.createProntuarioDao();

    public void salvarProntuario(Prontuario objProntuario) {
        if (sessaoJaExiste((objProntuario.getSessao()))) {
            throw new IllegalArgumentException("Sessão já existe para este paciente.");
        }
        dao.salvarProntuario(objProntuario);
    }

    public boolean sessaoJaExiste(Integer sessao) {
        return dao.sessaoJaExiste(sessao);
    }
}
