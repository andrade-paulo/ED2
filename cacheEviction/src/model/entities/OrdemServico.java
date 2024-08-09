package model.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrdemServico {
    private static int contador = 0;
    private int codigo;
    private String titulo;
    private String descricao;
    private Cliente cliente;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    LocalDateTime hora;

    public OrdemServico(String titulo, String descricao, Cliente cliente) {
        this.codigo = contador++;
        this.titulo = titulo;
        this.descricao = descricao;
        this.hora = LocalDateTime.now();
        this.cliente = cliente;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getHora() {
        return hora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getContador() {
        return contador;
    }

    public String toString() {
        return "Titulo: " + titulo + "\nCliente: " + cliente.getNome() + "\nDescricao: " + descricao + "\nHora: " + hora.format(dateTimeFormatter);
    }

    public boolean equals(OrdemServico ordemServico) {
        return this.codigo == ordemServico.getCodigo();
    }
}
