package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.PsicologoDAO;
import model.entities.Psicologo;

public class PsicologoService {
	
	private PsicologoDAO dao = DaoFactory.createPsicologoDao();
	
	public Psicologo autenticarPsico(String email, String senha) {
		return dao.autenticarPsico(email, senha);
	}
	
	public void cadastrarPsicologo(Psicologo objPsicologo) {
		dao.cadastrarPsicologo(objPsicologo);
	}
	
	public boolean emailJaCadastrado(String email) {
	    return dao.emailExiste(email);
	}
}