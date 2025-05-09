package model.services;

import dao.factory.DaoFactory;
import dao.interfaces.PacienteDAO;
import model.entities.Paciente;

public class PacienteService {
	
	private PacienteDAO dao = DaoFactory.createPacienteDao();
	
	public void cadastrarPaciente(Paciente objPaciente) {
		dao.cadastrarPaciente(objPaciente);
	}
	
	public boolean cpfJaCadastrado(String cpf) {
		return dao.cpfExiste(cpf);
	}

}
