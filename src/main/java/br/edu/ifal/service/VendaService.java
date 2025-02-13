package br.edu.ifal.service;

import br.edu.ifal.dao.PedidoDao;
import br.edu.ifal.dao.ProdutoDao;
import br.edu.ifal.dao.ClienteDao;
import br.edu.ifal.dao.FuncionarioDao;
import br.edu.ifal.db.ConnectionHelper;
import br.edu.ifal.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendaService {

    private PedidoDao pedidoDao = new PedidoDao();
    private ProdutoDao produtoDao = new ProdutoDao();
    private ClienteDao clienteDao = new ClienteDao();
    private FuncionarioDao funcionarioDao = new FuncionarioDao();

    public void efetuarVenda() {
        Scanner scanner = new Scanner(System.in);

        // Solicitar CPF do cliente e do vendedor
        System.out.print("Digite o CPF do cliente: ");
        String cpfCliente = scanner.nextLine();
        Cliente cliente = clienteDao.getClienteByCpf(cpfCliente);

        if (cliente == null) {
            System.out.println("Erro: Cliente não encontrado!");
            return;
        }

        System.out.print("Digite o CPF do vendedor: ");
        String cpfVendedor = scanner.nextLine();
        Funcionario funcionario = funcionarioDao.getFuncionarioByCpf(cpfVendedor);

        if (funcionario == null) {
            System.out.println("Erro: Vendedor não encontrado!");
            return;
        }

        // Criar o pedido
        System.out.println("Cliente: " + cliente.getNome() + ", CPF: " + cliente.getCpf());  // Depuração

        double valorTotal = 0.0;
        Pedido pedido = new Pedido( cliente, funcionario, valorTotal);

        // Salvar pedido e capturar o ID gerado
        System.out.println("Pedido criado - Cliente: " + pedido.getCliente().getCpf() + ", Vendedor: " + pedido.getFuncionario().getCpf());

        pedidoDao.savePedido(pedido);

        if (pedido.getId() == 0) {  // Supondo que ID seja 0 quando não salvo corretamente
            System.out.println("Erro ao salvar o pedido.");
            return;
        }

        // Inserir itens no pedido
        System.out.println("Digite os itens do pedido (ProdutoID, Quantidade). Digite 'sair' para finalizar.");
        while (true) {
            System.out.print("Produto ID: ");
            String produtoIdStr = scanner.nextLine();
            if (produtoIdStr.equalsIgnoreCase("sair")) {
                break;
            }

            int produtoId;
            try {
                produtoId = Integer.parseInt(produtoIdStr);
            } catch (NumberFormatException e) {
                System.out.println("ID inválido! Tente novamente.");
                continue;
            }

            System.out.print("Quantidade: ");
            int quantidade;
            try {
                quantidade = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Quantidade inválida! Tente novamente.");
                continue;
            }

            Produto produto = produtoDao.findById(produtoId);

            if (produto != null && produto.getQuantidade() >= quantidade) {
                double valorItem = produto.getValorUnitario() * quantidade;
                valorTotal += valorItem;
                pedidoDao.saveItemPedido(pedido.getId(), produtoId, quantidade, valorItem);
                produto.setQuantidade(produto.getQuantidade() - quantidade);
            } else {
                System.out.println("Produto indisponível ou quantidade insuficiente.");
            }
        }

        // Atualizar o valor total do pedido
        pedidoDao.updateValorTotal(pedido.getId(), valorTotal);
        System.out.println("Venda realizada com sucesso! Total: R$" + valorTotal);
    }

    public List<String> listarVendas() {
        String sql = "SELECT p.ID, c.NOME AS CLIENTE_NOME, f.NOME AS VENDEDOR_NOME, p.VALOR_TOTAL "
                + "FROM PEDIDO p "
                + "JOIN CLIENTE c ON p.CPF_CLIENTE_FK = c.CPF "
                + "JOIN FUNCIONARIO f ON p.CPF_FUNCIONARIO_FK = f.CPF";

        List<String> vendas = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String nomeCliente = rs.getString("CLIENTE_NOME");
                String nomeVendedor = rs.getString("VENDEDOR_NOME");
                double valorTotal = rs.getDouble("VALOR_TOTAL");

                // Aqui, criamos uma string para cada venda com os detalhes necessários
                String venda = String.format("Pedido ID: %d - Cliente: %s - Vendedor: %s - Valor Total: R$%.2f",
                        id, nomeCliente, nomeVendedor, valorTotal);
                vendas.add(venda);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return vendas;
    }


}