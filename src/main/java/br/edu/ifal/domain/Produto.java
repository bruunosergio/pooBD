package br.edu.ifal.domain;

public class Produto {
    private int id;
    private String nome;
    private double valorUnitario;
    private int quantidade;

    public Produto(String nome, double valorUnitario, int quantidade) {
        this.nome = nome;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }

    public Produto(int id, String nome, double valorUnitario, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
