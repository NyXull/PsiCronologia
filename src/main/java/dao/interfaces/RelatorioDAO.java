package dao.interfaces;

import java.util.List;

import model.entities.Relatorio;

public interface RelatorioDAO {
	
	void salvar(Relatorio relatorio);
	void atualizar(Relatorio relatorio);
	boolean nomeModeloExiste(String nomeModelo, Integer idPsicologo);
	Relatorio buscarPorNomeModelo(Integer idPsicologo, String nomeModelo);
	List<String> listarModelosPorPsicologo(Integer idPsicologo);
	void excluirRelatorio(Integer id);	
}