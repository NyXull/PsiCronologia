package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.ProntuarioDAO;
import model.entities.Prontuario;

import java.util.List;

public class ProntuarioService {

    private ProntuarioDAO dao = DaoFactory.createProntuarioDao();

    public void salvarProntuario(Prontuario objProntuario) {
        dao.salvarProntuario(objProntuario);
    }

    public boolean sessaoJaExiste(Integer sessao) {
        return dao.sessaoJaExiste(sessao);
    }

    public int getProximoIdOrdem(int idSessao) {
        return dao.getProximoIdOrdem(idSessao);
    }

    public List<Prontuario> listarPorPaciente(Integer idPaciente) {
        return dao.listarPorPaciente(idPaciente);
    }
}
