package br.edu.ifal;

import br.edu.ifal.dao.ClienteDao;
import br.edu.ifal.dao.PedidoDao;
import br.edu.ifal.dao.ProdutoDao;
import br.edu.ifal.db.ConnectionHelper;
import br.edu.ifal.domain.*;
import br.edu.ifal.service.VendaService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClienteDao clienteDao = new ClienteDao();
        ProdutoDao produtoDao = new ProdutoDao();
        PedidoDao pedidoDao = new PedidoDao();
        VendaService vendaService = new VendaService();  // Instanciando o serviço de vendas

        Scanner scanner = new Scanner(System.in);
        int opc;

        do {
            System.out.println("Digite a opcao desejada: \n" +
                    "1. Cadastrar produto \n" +
                    "2. Cadastrar cliente \n" +
                    "3. Buscar Produto (procurar produto pelo id) \n" +
                    "4.Listar todos os produtos disponíveis (Quantidade em estoque e Valor unitário) \n" +
                    "5.Efetuar venda (Deve ser informado a lista de produtos e suas quantidades, o vendedor e o cliente que está realizando a compra) \n" +
                    "6.Listar vendas realizadas ");

            opc = scanner.nextInt();
            scanner.nextLine();
            switch (opc) {
                case 1:
                    Scanner scanner1 = new Scanner(System.in);
                    // ProdutoDao produtoDao = new ProdutoDao();

                    System.out.println("=== Cadastro de Produto ===");

                    System.out.print("Nome do produto: ");
                    String nomeProduto = scanner1.nextLine();

                    System.out.print("Valor unitário: ");
                    double valor = scanner1.nextDouble();

                    System.out.print("Quantidade em estoque: ");
                    int quantidade = scanner1.nextInt();

                    Produto produto = new Produto(nomeProduto, valor, quantidade);
                    produtoDao.save(produto);

                    System.out.println("Produto cadastrado com sucesso!");

                    break;
                case 2:

                    Cliente cliente = new Cliente();

                    System.out.println("Digite o cpf");
                    String cpf = scanner.nextLine();
                    System.out.println("Digite o nome");
                    String nome = scanner.nextLine();
                    System.out.println("Digite o endereco:");
                    String endereco = scanner.nextLine();
                    System.out.println("Digite o telefone");
                    String telefone = scanner.nextLine();

                    cliente = new Cliente(cpf, nome, endereco, telefone);
                    new ClienteDao().save(cliente);

                    break;
                case 3:

                    System.out.print("Digite o ID do produto a ser buscado: ");
                    int id = scanner.nextInt();

                    Produto produtoFind = produtoDao.findById(id);

                    if (produtoFind != null) {
                        System.out.println("\n=== Produto Encontrado ===");
                        System.out.println("ID: " + produtoFind.getId());
                        System.out.println("Nome: " + produtoFind.getNome());
                        System.out.println("Valor Unitário: " + produtoFind.getValorUnitario());
                        System.out.println("Quantidade em Estoque: " + produtoFind.getQuantidade());
                    } else {
                        System.out.println("Produto não encontrado!");
                    }

                    break;
                case 4:
                    System.out.println("=== Produtos Disponíveis ===");
                    List<Produto> produtos = produtoDao.findAllAvailable();

                    if (produtos.isEmpty()) {
                        System.out.println("Nenhum produto disponível no estoque.");
                    } else {
                        for (Produto produtoView : produtos) {
                            System.out.println("ID: " + produtoView.getId() + " | Nome: " + produtoView.getNome() +
                                    " | Valor Unitário: R$" + produtoView.getValorUnitario() +
                                    " | Quantidade: " + produtoView.getQuantidade());
                        }
                    }
                    break;

                case 5:
                    vendaService.efetuarVenda();
                    break;

                case 6 :
                    List<String> vendas = vendaService.listarVendas();

                    System.out.println("Vendas realizadas:");
                    for (String venda : vendas) {
                        System.out.println(venda);
                    }            }

        } while(opc !=0);


        try {
            List<Cliente> listaClientes = clienteDao.findAll();
            for (Cliente c : listaClientes) {
                System.out.println(c);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}




