package model.services;

import java.sql.ResultSet;

import dao.factory.DaoFactory;
import dao.interfaces.PsicologoDAO;
import model.entities.Psicologo;

public class PsicologoService {
	
	private PsicologoDAO dao = DaoFactory.createPsicologoDao();
	
	public ResultSet autenticacao(Psicologo objPsicologo) {
		return dao.autenticacaoPsico(objPsicologo);
	}
	
	public void cadastrarPsicologo(Psicologo objPsicologo) {
		dao.cadastrarPsicologo(objPsicologo);
	}
	
	public boolean emailJaCadastrado(String email) {
	    return dao.emailExiste(email);
	}
}