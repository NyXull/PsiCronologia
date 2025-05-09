package dao.interfaces;

import model.entities.VerificacaoEmail;

public interface VerificacaoEmailDAO {
	
	void salvar(VerificacaoEmail verificacao);
	VerificacaoEmail buscarPorCodigo(String codigo);
	void deletarPorCodigo(String codigo);
}