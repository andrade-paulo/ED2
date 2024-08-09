package model.entities;

public class Cliente {
    private String nome;
    private String cpf;
    private int codigo;
    private static int contador = 0;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.codigo = contador++;
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

    public int getContador() {
        return contador;
    }

    public String toString() {
        return "Nome: " + nome + "\nCPF: " + cpf;
    }

    public boolean equals(Cliente cliente) {
        return this.cpf.equals(cliente.getCpf());
    }
}
