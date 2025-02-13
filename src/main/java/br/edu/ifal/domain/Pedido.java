package br.edu.ifal.domain;


public class Pedido {
    private int id;
    private Cliente cliente;
    private Funcionario funcionario;
    private double valorTotal;



    public Pedido( Cliente cliente, Funcionario funcionario, double valorTotal) {
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.valorTotal = valorTotal;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}