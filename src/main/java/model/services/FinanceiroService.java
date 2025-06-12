package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.FinanceiroDAO;
import model.entities.Financeiro;

public class FinanceiroService {

    private FinanceiroDAO dao = DaoFactory.createFinanceiroDao();

    public void salvarInformacoesPagamento(Financeiro objFinanceiro) {
        if (informacaoPagamentoJaExiste(objFinanceiro.getIdPaciente())) {
            atualizarInformacoesPagamento(objFinanceiro);
            return;
        }

        dao.salvarInformacoesPagamento(objFinanceiro);
    }

    public Financeiro carregarInformacoesPagamento(Integer idPaciente) {
        return dao.carregarInformacoesPagamento(idPaciente);
    }

    public boolean informacaoPagamentoJaExiste(Integer idPaciente) {
        return dao.informacaoPagamentoJaExiste(idPaciente);
    }

    public void atualizarInformacoesPagamento(Financeiro objFinanceiro) {
        dao.atualizarInformacoesPagamento(objFinanceiro);
    }
}
