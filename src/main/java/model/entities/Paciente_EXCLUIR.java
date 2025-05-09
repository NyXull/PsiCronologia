package model.entities;

public class Paciente_EXCLUIR {
    private final String nome;

    public Paciente_EXCLUIR(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
