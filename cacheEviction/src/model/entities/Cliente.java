package model.entities;

import java.io.Serializable;

public class Cliente implements Serializable {
    private String nome;
    private String cpf;
    private int codigo;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.codigo = Integer.parseInt(cpf);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public int getCodigo() {
        return codigo;
    }

    public String toString() {
        return "Nome: " + nome + "\nCPF: " + cpf;
    }

    public boolean equals(Cliente cliente) {
        return this.cpf.equals(cliente.getCpf());
    }
}
