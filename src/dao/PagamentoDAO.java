package dao;

import model.Aluno;
import model.Pagamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Pagamento.
 */
public class PagamentoDAO {

    private final AlunoDAO alunoDAO = new AlunoDAO();

    // ── CREATE

    public boolean inserir(Pagamento pagamento) {
        String sql = "INSERT INTO pagamento (aluno_id, valor_mensalidade, desconto, data_vencimento, data_pagamento, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pagamento.getAluno().getId());
            stmt.setDouble(2, pagamento.getValorMensalidade());
            stmt.setDouble(3, pagamento.getDesconto());
            stmt.setString(4, pagamento.getDataVencimento());
            stmt.setString(5, pagamento.getDataPagamento());
            stmt.setString(6, pagamento.getStatus());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) pagamento.setId(rs.getInt(1));
                }
                System.out.println("✅ Pagamento inserido com sucesso! ID: " + pagamento.getId());
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir pagamento: " + e.getMessage());
            return false;
        }
    }

    // ── READ: buscar por ID 

    public Pagamento buscarPorId(int id) {
        String sql = "SELECT * FROM pagamento WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return extrairPagamentoDoResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar pagamento: " + e.getMessage());
        }
        return null;
    }

    // ── READ: listar todos

    public List<Pagamento> listarTodos() {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM pagamento ORDER BY id";

        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) pagamentos.add(extrairPagamentoDoResultSet(rs));
            System.out.println("✅ " + pagamentos.size() + " pagamento(s) encontrado(s)");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar pagamentos: " + e.getMessage());
        }
        return pagamentos;
    }

    // ── UPDATE 

    public boolean atualizar(Pagamento pagamento) {
        String sql = "UPDATE pagamento SET aluno_id = ?, valor_mensalidade = ?, desconto = ?, " +
                     "data_vencimento = ?, data_pagamento = ?, status = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pagamento.getAluno().getId());
            stmt.setDouble(2, pagamento.getValorMensalidade());
            stmt.setDouble(3, pagamento.getDesconto());
            stmt.setString(4, pagamento.getDataVencimento());
            stmt.setString(5, pagamento.getDataPagamento());
            stmt.setString(6, pagamento.getStatus());
            stmt.setInt(7, pagamento.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Pagamento atualizado com sucesso!");
                return true;
            } else {
                System.out.println("⚠ Nenhum pagamento encontrado com ID: " + pagamento.getId());
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar pagamento: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE 

    public boolean excluir(int id) {
        String sql = "DELETE FROM pagamento WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Pagamento excluído com sucesso!");
                return true;
            } else {
                System.out.println("⚠ Nenhum pagamento encontrado com ID: " + id);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir pagamento: " + e.getMessage());
            return false;
        }
    }

    // ── Auxiliar 

    private Pagamento extrairPagamentoDoResultSet(ResultSet rs) throws SQLException {
        Pagamento p = new Pagamento();
        p.setId(rs.getInt("id"));
        p.setValorMensalidade(rs.getDouble("valor_mensalidade"));
        p.setDesconto(rs.getDouble("desconto"));
        p.setDataVencimento(rs.getString("data_vencimento"));
        p.setDataPagamento(rs.getString("data_pagamento"));
        p.setStatus(rs.getString("status"));

        // Carrega o aluno associado
        Aluno aluno = alunoDAO.buscarPorId(rs.getInt("aluno_id"));
        p.setAluno(aluno);

        return p;
    }
}
