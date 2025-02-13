package br.edu.ifal.dao;

import br.edu.ifal.db.ConnectionHelper;
import br.edu.ifal.domain.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao {

    public List<Funcionario> findAll() {
        String sql = "SELECT * FROM FUNCIONARIO;";
        List<Funcionario> lista = new ArrayList<>();
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String cpf = rs.getString("CPF");
                String nome = rs.getString("NOME");
                String endereco = rs.getString("ENDERECO");
                String telefone = rs.getString("TELEFONE");

                Funcionario funcionario = new Funcionario(cpf, nome, endereco, telefone);
                lista.add(funcionario);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
    public Funcionario getFuncionarioByCpf(String cpf) {
        String sql = "SELECT * FROM FUNCIONARIO WHERE CPF = ?;";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, cpf);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                            rs.getString("CPF"),
                            rs.getString("NOME"),
                            rs.getString("ENDERECO"),
                            rs.getString("TELEFONE")
                    );
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return null; // Retorna null caso o funcionário não seja encontrado
    }
}
