package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.PacienteDAO;
import model.entities.Paciente;

public class PacienteService {
	
	private PacienteDAO dao = DaoFactory.createPacienteDao();
	
	public void cadastrarPaciente(Paciente objPaciente) {
		dao.cadastrarPaciente(objPaciente);
	}
	
	public void cadastrarOuAssociarPaciente(Paciente paciente, int idPsicologo) {
		Paciente pacienteExistente = dao.buscarPorCpf(paciente.getCpf());
		
		if (pacienteExistente == null) {
			dao.cadastrarPaciente(paciente);
			dao.associarPsicologoPaciente(idPsicologo, paciente.getIdPaciente());
		}
		else {
			if (dao.relacaoJaExiste(idPsicologo, pacienteExistente.getIdPaciente())) {
				throw new IllegalStateException("Paciente já cadastrado por este psicólogo.");
			}
			dao.associarPsicologoPaciente(idPsicologo, pacienteExistente.getIdPaciente());
		}
	}
}