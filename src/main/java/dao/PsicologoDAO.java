package dao;

import java.sql.ResultSet;

import model.entities.Psicologo;

public interface PsicologoDAO {

	ResultSet autenticacaoPsico(Psicologo objPsicologo);
	void cadastrarPsicologo(Psicologo objPsicologo);	
}