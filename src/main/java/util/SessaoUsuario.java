package util;

import model.entities.Psicologo;

public class SessaoUsuario {
	private static Psicologo psicoLogado;
	
	public static void setPsicologo(Psicologo psicologo) {
		SessaoUsuario.psicoLogado = psicologo;
	}
	
	public static Psicologo getPsicologoLogado() {
		return psicoLogado;
	}
	
	public static Integer getIdPsico() {
		return psicoLogado != null ? psicoLogado.getIdPsico() : null;
	}
	
	public static void encerrarSessao() {
		psicoLogado = null;
	}
}