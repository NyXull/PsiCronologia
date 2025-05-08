package model.services;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import dao.VerificacaoEmailDAO;
import dao.factory.DaoFactory;
import model.entities.VerificacaoEmail;

public class VerificacaoEmailService {

	private VerificacaoEmailDAO dao = DaoFactory.createVerificacaoEmailDao();
	
	public String gerarCodigo(String email) {
		String codigo = UUID.randomUUID().toString();
		Instant agora = Instant.now();
		
		VerificacaoEmail verificacao = new VerificacaoEmail(email, codigo, agora);
		
		dao.salvar(verificacao);
		return codigo;
	}
	
	public boolean validarCodigo(String codigo) {
		VerificacaoEmail verificacao = dao.buscarPorCodigo(codigo);
		
		if (verificacao == null) {
			return false;
		}
		
		Instant agora = Instant.now();
		Instant criado = verificacao.getCriadoEm();
		
		boolean valido = criado.plus(Duration.ofMinutes(15)).isAfter(agora);
		
		if (valido) {
			dao.deletarPorCodigo(codigo);
		}
		return valido;
	}
}
