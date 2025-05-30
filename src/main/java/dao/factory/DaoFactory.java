package dao.factory;

import dao.impl.PacienteDaoJDBC;
import dao.impl.ProntuarioDaoJDBC;
import dao.impl.PsicologoDaoJDBC;
import dao.impl.VerificacaoEmailDaoJDBC;
import dao.interfaces.PacienteDAO;
import dao.interfaces.ProntuarioDAO;
import dao.interfaces.PsicologoDAO;
import dao.interfaces.VerificacaoEmailDAO;
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
}