package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.FinanceiroDAO;
import model.entities.Financeiro;
import util.ExtrairDadosData;

import java.util.List;

public class FinanceiroService {

    private FinanceiroDAO dao = DaoFactory.createFinanceiroDao();

    public void salvarInformacoesPagamento(Financeiro objFinanceiro) {
        if (informacaoPagamentoJaExiste(objFinanceiro.getIdPaciente())) {
            int mes = ExtrairDadosData.getMes(objFinanceiro.getDataVencimento());
            int ano = ExtrairDadosData.getAno(objFinanceiro.getDataVencimento());

            if (existeRegistroMesAno(objFinanceiro.getIdPaciente(), mes, ano)) {
                atualizarInformacoesPagamento(objFinanceiro, mes, ano);
                return;
            }

            dao.salvarInformacoesPagamento(objFinanceiro);
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

    public void atualizarInformacoesPagamento(Financeiro objFinanceiro, int mes, int ano) {
        dao.atualizarInformacoesPagamento(objFinanceiro, mes, ano);
    }

    public boolean existeRegistroMesAno(Integer idPaciente, int mes, int ano) {
        return dao.existeRegistroMesAno(idPaciente, mes, ano);
    }

    public List<Financeiro> carregarInformacoesPagamentoPorAno(Integer idPaciente, int ano) {
        return dao.carregarInformacoesPagamentoPorAno(idPaciente, ano);
    }

    public void atualizarStatusPagamentoMesStatusAno(Financeiro objFinanceiro, int ano) {
        dao.atualizarStatusPagamentoMesStatusAno(objFinanceiro, ano);
    }
}
