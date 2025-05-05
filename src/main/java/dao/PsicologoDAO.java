package dao;

import java.sql.ResultSet;

import model.Psicologo;

public interface PsicologoDAO {

	ResultSet autenticacaoPsico(Psicologo objPsicologo);
	void cadastrarPsicologo(Psicologo objPsicologo);	
}