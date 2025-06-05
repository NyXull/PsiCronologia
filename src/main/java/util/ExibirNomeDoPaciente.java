package util;

import model.entities.Paciente;

public class ExibirNomeDoPaciente {
	
	public static String formatarNomePaciente(Paciente paciente) {
		
		if (paciente == null || paciente.getNomePaciente() == null) {
			return "";
		}
		
		String nomeCompleto = paciente.getNomePaciente().trim();
		String[] partesNome = nomeCompleto.split(" ");
		
		if (partesNome.length == 0) {
			return "";
		}
		
		StringBuilder nomeFormatado = new StringBuilder();
		
		nomeFormatado.append(partesNome[0]);
		
		if (partesNome.length > 1) {
			nomeFormatado.append(" ");
		
			String[] preposicoes = {"de", "da", "do", "das", "dos", "e", "del"};
		
			for (int i = 1; i < partesNome.length -1; i++) {
				String parte = partesNome[i];
				
				boolean ehPreposicao = java.util.Arrays.asList(preposicoes).contains(parte.toLowerCase());
				
				if (ehPreposicao) {
					nomeFormatado.append(parte).append(" ");
				}
				else {
					nomeFormatado.append(parte.charAt(0)).append(". ");
				}
			}
		
			String ultimoNome = partesNome[partesNome.length - 1];
			
			if (!ultimoNome.equalsIgnoreCase(partesNome[0])) {
				nomeFormatado.append(ultimoNome);
			}
		}		
		return nomeFormatado.toString().trim();
	}
}