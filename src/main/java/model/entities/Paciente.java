package model.entities;

public class Paciente {
    private final String nome;

    public Paciente(String nome) {
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
