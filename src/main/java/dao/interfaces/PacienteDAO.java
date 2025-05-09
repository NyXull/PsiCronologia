package dao.interfaces;

import model.entities.Paciente;

public interface PacienteDAO {
	
	void cadastrarPaciente(Paciente objPaciente);
	boolean cpfExiste(String cpf);
}