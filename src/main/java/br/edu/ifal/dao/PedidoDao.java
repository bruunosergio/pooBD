package br.edu.ifal.dao;

import br.edu.ifal.db.ConnectionHelper;
import br.edu.ifal.domain.ItemPedido;
import br.edu.ifal.domain.Pedido;

import java.sql.*;

import br.edu.ifal.db.ConnectionHelper;
import br.edu.ifal.domain.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
public class PedidoDao {

    public void savePedido(Pedido pedido) {
        String sql = "INSERT INTO PEDIDO (CPF_CLIENTE_FK, CPF_FUNCIONARIO_FK, VALOR_TOTAL) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, pedido.getCliente().getCpf());  // Garantindo que o CPF do cliente seja usado
            pst.setString(2, pedido.getFuncionario().getCpf());  // Garantindo que o CPF do funcionário seja usado
            pst.setDouble(3, pedido.getValorTotal());

            pst.executeUpdate();  // Executa o INSERT

            // Recupera o ID gerado automaticamente
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getInt(1));  // Atribui o ID gerado ao pedido
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void saveItemPedido (ItemPedido itemPedido) {
                String sql = "INSERT INTO ITEM_PEDIDO (ID_PEDIDO_FK, ID_PRODUTO_FK, QUANTIDADE, VALOR) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setInt(1, itemPedido.getPedido().getId());
            pst.setInt(2, itemPedido.getProduto().getId());
            pst.setInt(3, itemPedido.getQuantidade());
            pst.setDouble(4, itemPedido.getValor());

            pst.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }

    public void updateValorTotal(int pedidoId, double valorTotal) {
        String sql = "UPDATE PEDIDO SET VALOR_TOTAL = ? WHERE ID = ?";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setDouble(1, valorTotal);
            pst.setInt(2, pedidoId);

            pst.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveItemPedido(int id, int produtoId, int quantidade, double valorItem) {
    }


}