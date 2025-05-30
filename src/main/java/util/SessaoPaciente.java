package util;

import model.entities.Paciente;

public class SessaoPaciente {
    private static Paciente paciente;

    public static void setPaciente(Paciente p) {
        paciente = p;
    }

    public static Paciente getPaciente() {
        return paciente;
    }

    public static void limpar() {
        paciente = null;
    }

    public static Integer getIdPaciente() {
        return paciente != null ? paciente.getIdPaciente() : null;
    }
}