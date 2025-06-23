package dao.factory;

import dao.impl.*;
import dao.interfaces.*;
import db.DB;

public class DaoFactory {

    public static PsicologoDAO createPsicologoDao() {
        return new PsicologoDaoJDBC(DB.getConnection());
    }

    public static VerificacaoEmailDAO createVerificacaoEmailDao() {
        return new VerificacaoEmailDaoJDBC(DB.getConnection());
    }

    public static PacienteDAO createPacienteDao() {
        return new PacienteDaoJDBC(DB.getConnection());
    }

    public static ProntuarioDAO createProntuarioDao() {
        return new ProntuarioDaoJDBC(DB.getConnection());
    }

    public static AgendaDAO createAgendaDao() {
        return new AgendaDaoJDBC(DB.getConnection());
    }

    public static FinanceiroDAO createFinanceiroDao() {
        return new FinanceiroDaoJDBC(DB.getConnection());
    }

    public static BibliotecaDAO createBibliotecaDao() {
        return new BibliotecaDaoJDBC(DB.getConnection());
    }
    
    public static RelatorioDAO createRelatorioDao() {
    	return new RelatorioDaoJDBC(DB.getConnection());
    }
}