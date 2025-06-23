package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.RelatorioDAO;
import model.entities.Relatorio;

public class RelatorioService {

    private RelatorioDAO dao = DaoFactory.createRelatorioDao();

    public void salvarRelatorio(Relatorio objRelatorio) {
        dao.salvarRelatorio(objRelatorio);
    }
}
