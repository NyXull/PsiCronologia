package dao.interfaces;

import model.entities.Financeiro;

public interface FinanceiroDAO {

    void salvarInformacoesPagamento(Financeiro objFinanceiro);

    Financeiro carregarInformacoesPagamento(Integer idPaciente);

    boolean informacaoPagamentoJaExiste(Integer idPaciente);

    void atualizarInformacoesPagamento(Financeiro objFinanceiro, int mes, int ano);

    boolean existeRegistroMesAno(Integer idPaciente, int mes, int ano);
}
