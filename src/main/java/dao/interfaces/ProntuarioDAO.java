package dao.interfaces;

import model.entities.Prontuario;

public interface ProntuarioDAO {

    void salvarProntuario(Prontuario objProntuario);
    boolean sessaoJaExiste(Integer sessao);
}
