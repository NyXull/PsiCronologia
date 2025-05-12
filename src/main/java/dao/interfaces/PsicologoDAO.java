package dao.interfaces;

import model.entities.Psicologo;

public interface PsicologoDAO {

	Psicologo autenticarPsico(String email, String senha);
	void cadastrarPsicologo(Psicologo objPsicologo);
	boolean emailExiste(String email);
}