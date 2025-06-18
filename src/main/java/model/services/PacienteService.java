package model.services;

import java.util.List;

import dao.factory.DaoFactory;
import dao.interfaces.PacienteDAO;
import model.entities.Paciente;
import util.SessaoPaciente;

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
	
	public List<Paciente> listarPorPsicologo(int idPsicologo){
		return dao.listarPorPsicologo(idPsicologo);
	}
	
	public void deletarPacienteAtual(Paciente paciente) {
				
		if (paciente != null) {
			dao.deletarPorId(paciente);
		}
		else {
			throw new IllegalStateException("Nenhum paciente selecionado na sessão.");
		}
	}
	
	public void atualizarPacienteAtual(Paciente pacienteAtualizado) {
		Paciente paciente = SessaoPaciente.getPaciente();
		
		if (paciente != null) {
			pacienteAtualizado.setIdPaciente(paciente.getIdPaciente());
			dao.atualizarPaciente(pacienteAtualizado);
		}
		else {
			throw new IllegalStateException("Nenhum paciente selecionado na sessão.");
		}
	}
}