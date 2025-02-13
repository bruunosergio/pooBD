package br.edu.ifal.domain;

public class ItemPedido {
    private Pedido pedido;
    private Produto produto;
    private int quantidade;
    private double valor;

    public ItemPedido() {
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}