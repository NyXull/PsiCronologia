package model.services;

import java.util.List;

import dao.factory.DaoFactory;
import dao.interfaces.RelatorioDAO;
import model.entities.Relatorio;

public class RelatorioService {

	private final RelatorioDAO dao = DaoFactory.createRelatorioDao();
	
	public void salvarOuAtualizarRelatorio(Relatorio relatorio) {
		dao.salvar(relatorio);
	}
	
	public Relatorio buscarPorNomeModelo(String nomeModelo, Integer idPsicologo) {
		return dao.buscarPorNomeModelo(idPsicologo, nomeModelo);
	}
	
	public List<String> listarNomesDeModelo(Integer idPsicologo) {
		return dao.listarModelosPorPsicologo(idPsicologo);
	}
	
	public void excluirRelatorio(Integer id) {
		dao.excluirRelatorio(id);
	}
}