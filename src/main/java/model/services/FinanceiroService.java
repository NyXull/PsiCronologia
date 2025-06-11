package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.FinanceiroDAO;
import model.entities.Financeiro;

public class FinanceiroService {

    private FinanceiroDAO dao = DaoFactory.createFinanceiroDao();

    public void salvarInformacoesPagamento(Financeiro objFinanceiro) {
        dao.salvarInformacoesPagamento(objFinanceiro);
    }

    public Financeiro carregarInformacoesPagamento(Integer idPaciente) {
        return dao.carregarInformacoesPagamento(idPaciente);
    }
}
