package dao;

import dao.impl.PsicologoDaoJDBC;
import db.DB;

public class DaoFactory {
	
	public static PsicologoDAO createPsicologoDao() {
		return new PsicologoDaoJDBC(DB.getConnection());
	}
}