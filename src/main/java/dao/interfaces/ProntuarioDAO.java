package dao.interfaces;

import model.entities.Prontuario;

import java.util.List;

public interface ProntuarioDAO {

    void salvarProntuario(Prontuario objProntuario);

    boolean sessaoJaExiste(Integer idSessao);

    int getProximoIdOrdem(int idSessao);

    List<Prontuario> listarPorPaciente(Integer idPaciente);

    void atualizarProntuario(Prontuario objProntuario);

    Integer getIdOrdem(Integer idSessao);

}
