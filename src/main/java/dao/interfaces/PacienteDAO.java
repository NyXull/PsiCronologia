package dao.interfaces;

import java.util.List;

import model.entities.Paciente;

public interface PacienteDAO {
	
	void cadastrarPaciente(Paciente objPaciente);
	Paciente buscarPorCpf(String cpf);
	boolean relacaoJaExiste(int idPsicologo, int idPaciente);
	void associarPsicologoPaciente(int idPsicologo, int idPaciente);
	List<Paciente> listarPorPsicologo(int idPsicologo);
}