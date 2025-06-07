package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.ProntuarioDAO;
import model.entities.Prontuario;

import java.util.List;

public class ProntuarioService {

    private ProntuarioDAO dao = DaoFactory.createProntuarioDao();

    public void salvarProntuario(Prontuario objProntuario) {
        if (sessaoJaExiste(objProntuario.getIdSessao())) {
            if (getIdOrdem(objProntuario.getIdOrdem()) != 0) {
                atualizarProntuario(objProntuario);
                return;
            }
        }
        dao.salvarProntuario(objProntuario);
    }

    public boolean sessaoJaExiste(Integer idSessao) {
        return dao.sessaoJaExiste(idSessao);
    }

    public int getProximoIdOrdem(int idSessao) {
        return dao.getProximoIdOrdem(idSessao);
    }

    public List<Prontuario> listarPorPaciente(Integer idPaciente) {
        return dao.listarPorPaciente(idPaciente);
    }

    public void atualizarProntuario(Prontuario objProntuario) {
        dao.atualizarProntuario(objProntuario);
    }

    public Integer getIdOrdem(Integer idSessao) {
        return dao.getIdOrdem(idSessao);
    }
}
