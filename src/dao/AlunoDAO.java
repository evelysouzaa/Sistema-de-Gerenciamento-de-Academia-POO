package dao;

import model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Aluno.
 * Implementa CRUD completo usando JDBC + PreparedStatement.
 */
public class AlunoDAO {

    // ── CREATE ────────────────────────────────────────────────────────────────

    public boolean inserir(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, cpf, idade, telefone, peso, altura, plano, meses_cadastrado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setInt(3, aluno.getIdade());
            stmt.setString(4, aluno.getTelefone());
            stmt.setDouble(5, aluno.getPeso());
            stmt.setDouble(6, aluno.getAltura());
            stmt.setString(7, aluno.getPlano());
            stmt.setInt(8, aluno.getMesesCadastrado());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        aluno.setId(rs.getInt(1));
                    }
                }
                System.out.println("✅ Aluno inserido com sucesso! ID: " + aluno.getId());
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir aluno: " + e.getMessage());
            return false;
        }
    }

    // ── READ: buscar por ID ───────────────────────────────────────────────────

    public Aluno buscarPorId(int id) {
        String sql = "SELECT * FROM aluno WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairAlunoDoResultSet(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar aluno: " + e.getMessage());
        }

        return null;
    }

    // ── READ: listar todos ────────────────────────────────────────────────────

    public List<Aluno> listarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno ORDER BY nome";

        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                alunos.add(extrairAlunoDoResultSet(rs));
            }

            System.out.println("✅ " + alunos.size() + " aluno(s) encontrado(s)");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar alunos: " + e.getMessage());
        }

        return alunos;
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────

    public boolean atualizar(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, cpf = ?, idade = ?, telefone = ?, " +
                     "peso = ?, altura = ?, plano = ?, meses_cadastrado = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setInt(3, aluno.getIdade());
            stmt.setString(4, aluno.getTelefone());
            stmt.setDouble(5, aluno.getPeso());
            stmt.setDouble(6, aluno.getAltura());
            stmt.setString(7, aluno.getPlano());
            stmt.setInt(8, aluno.getMesesCadastrado());
            stmt.setInt(9, aluno.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Aluno atualizado com sucesso!");
                return true;
            } else {
                System.out.println("⚠ Nenhum aluno encontrado com ID: " + aluno.getId());
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar aluno: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────

    public boolean excluir(int id) {
        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Aluno excluído com sucesso!");
                return true;
            } else {
                System.out.println("⚠ Nenhum aluno encontrado com ID: " + id);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir aluno: " + e.getMessage());
            return false;
        }
    }

    // ── Auxiliar ──────────────────────────────────────────────────────────────

    private Aluno extrairAlunoDoResultSet(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getInt("id"));
        aluno.setNome(rs.getString("nome"));
        aluno.setCpf(rs.getString("cpf"));
        aluno.setIdade(rs.getInt("idade"));
        aluno.setTelefone(rs.getString("telefone"));
        aluno.setPeso(rs.getDouble("peso"));
        aluno.setAltura(rs.getDouble("altura"));
        aluno.setPlano(rs.getString("plano"));
        aluno.setMesesCadastrado(rs.getInt("meses_cadastrado"));
        return aluno;
    }
}
