package dao;

import model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Funcionario.
 */
public class FuncionarioDAO {

    // ── CREATE ────────────────────────────────────────────────────────────────

    public boolean inserir(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (nome, cpf, idade, telefone, cargo, setor, salario, horas_semanais) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setInt(3, funcionario.getIdade());
            stmt.setString(4, funcionario.getTelefone());
            stmt.setString(5, funcionario.getCargo());
            stmt.setString(6, funcionario.getSetor());
            stmt.setDouble(7, funcionario.getSalario());
            stmt.setInt(8, funcionario.getHorasSemanais());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) funcionario.setId(rs.getInt(1));
                }
                System.out.println("✅ Funcionário inserido com sucesso! ID: " + funcionario.getId());
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir funcionário: " + e.getMessage());
            return false;
        }
    }

    // ── READ: buscar por ID ───────────────────────────────────────────────────

    public Funcionario buscarPorId(int id) {
        String sql = "SELECT * FROM funcionario WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return extrairFuncionarioDoResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar funcionário: " + e.getMessage());
        }
        return null;
    }

    // ── READ: listar todos ────────────────────────────────────────────────────

    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionario ORDER BY nome";

        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) funcionarios.add(extrairFuncionarioDoResultSet(rs));
            System.out.println("✅ " + funcionarios.size() + " funcionário(s) encontrado(s)");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar funcionários: " + e.getMessage());
        }
        return funcionarios;
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────

    public boolean atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome = ?, cpf = ?, idade = ?, telefone = ?, " +
                     "cargo = ?, setor = ?, salario = ?, horas_semanais = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setInt(3, funcionario.getIdade());
            stmt.setString(4, funcionario.getTelefone());
            stmt.setString(5, funcionario.getCargo());
            stmt.setString(6, funcionario.getSetor());
            stmt.setDouble(7, funcionario.getSalario());
            stmt.setInt(8, funcionario.getHorasSemanais());
            stmt.setInt(9, funcionario.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Funcionário atualizado com sucesso!");
                return true;
            } else {
                System.out.println("⚠ Nenhum funcionário encontrado com ID: " + funcionario.getId());
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    public boolean excluir(int id) {
        String sql = "DELETE FROM funcionario WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Funcionário excluído com sucesso!");
                return true;
            } else {
                System.out.println("⚠ Nenhum funcionário encontrado com ID: " + id);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir funcionário: " + e.getMessage());
            return false;
        }
    }

    // ── Auxiliar ──────────────────────────────────────────────────────────────

    private Funcionario extrairFuncionarioDoResultSet(ResultSet rs) throws SQLException {
        Funcionario f = new Funcionario();
        f.setId(rs.getInt("id"));
        f.setNome(rs.getString("nome"));
        f.setCpf(rs.getString("cpf"));
        f.setIdade(rs.getInt("idade"));
        f.setTelefone(rs.getString("telefone"));
        f.setCargo(rs.getString("cargo"));
        f.setSetor(rs.getString("setor"));
        f.setSalario(rs.getDouble("salario"));
        f.setHorasSemanais(rs.getInt("horas_semanais"));
        return f;
    }
}
