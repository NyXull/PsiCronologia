package dao.interfaces;

import model.entities.Financeiro;

public interface FinanceiroDAO {

    void salvarInformacoesPagamento(Financeiro objFinanceiro);

    Financeiro carregarInformacoesPagamento(Integer idPaciente);
}
