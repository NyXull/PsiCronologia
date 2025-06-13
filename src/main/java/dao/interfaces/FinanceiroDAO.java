package dao.interfaces;

import model.entities.Financeiro;

import java.util.List;

public interface FinanceiroDAO {

    void salvarInformacoesPagamento(Financeiro objFinanceiro);

    Financeiro carregarInformacoesPagamento(Integer idPaciente);

    boolean informacaoPagamentoJaExiste(Integer idPaciente);

    void atualizarInformacoesPagamento(Financeiro objFinanceiro, int mes, int ano);

    boolean existeRegistroMesAno(Integer idPaciente, int mes, int ano);

    List<Financeiro> carregarInformacoesPagamentoPorAno(Integer idPaciente, int ano);
}
