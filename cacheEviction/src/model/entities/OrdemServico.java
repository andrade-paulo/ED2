package model.entities;

import java.io.Serializable;
import java.util.Date;

public class OrdemServico implements Serializable{
    private static int contador = 0;
    private int codigo;
    private String titulo;
    private String descricao;
    private Cliente cliente;
    private Date hora;

    private static final long serialVersionUID = 2L;

    public OrdemServico(String titulo, String descricao, Cliente cliente) {
        this.codigo = ++contador;
        this.titulo = titulo;
        this.descricao = descricao;
        this.hora = new Date();
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

    public Date getHora() {
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

    public static void setContador(int contador) {
        OrdemServico.contador = contador;
    }

    public int getContador() {
        return contador;
    }

    public String toString() {
        return "Codigo: " + Integer.toString(codigo) + "\nTitulo: " + titulo + "\nCliente: " + cliente.getNome() + "\nDescricao: " + descricao + "\nHora: " + hora.toString();
    }

    public boolean equals(OrdemServico ordemServico) {
        return this.codigo == ordemServico.getCodigo();
    }
}
