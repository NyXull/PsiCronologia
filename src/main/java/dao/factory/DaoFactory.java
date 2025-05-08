package dao.factory;

import dao.PsicologoDAO;
import dao.VerificacaoEmailDAO;
import dao.impl.PsicologoDaoJDBC;
import dao.impl.VerificacaoEmailDaoJDBC;
import db.DB;

public class DaoFactory {
	
	public static PsicologoDAO createPsicologoDao() {
		return new PsicologoDaoJDBC(DB.getConnection());
	}
	
	public static VerificacaoEmailDAO createVerificacaoEmailDao() {
		return new VerificacaoEmailDaoJDBC(DB.getConnection());
	}
}