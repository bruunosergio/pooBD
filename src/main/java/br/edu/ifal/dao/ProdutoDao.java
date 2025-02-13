package br.edu.ifal.dao;

import br.edu.ifal.db.ConnectionHelper;
import br.edu.ifal.domain.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {

    public void save(Produto produto) {
        String sql = "INSERT INTO PRODUTO (NOME, VALOR_UNIT, QUANTIDADE) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, produto.getNome());
            pst.setDouble(2, produto.getValorUnitario());
            pst.setInt(3, produto.getQuantidade());

            pst.executeUpdate(); // Melhor que `execute()` para inserção

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Produto findById(int id) {
        String sql = "SELECT * FROM PRODUTO WHERE ID = ?";
        Produto produto = null;

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                produto = new Produto(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("VALOR_UNIT"),
                        rs.getInt("QUANTIDADE")
                );
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return produto;
    }
    public List<Produto> findAllAvailable() {
        String sql = "SELECT * FROM PRODUTO WHERE QUANTIDADE > 0";
        List<Produto> produtos = new ArrayList<>();

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("VALOR_UNIT"),
                        rs.getInt("QUANTIDADE")
                );
                produtos.add(produto);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produtos;
    }

    public void atualizarEstoque(int produtoId, int quantidadeVendida) {
        String sql = "UPDATE PRODUTO SET QUANTIDADE = QUANTIDADE - ? WHERE ID = ?";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setInt(1, quantidadeVendida);
            pst.setInt(2, produtoId);

            pst.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

