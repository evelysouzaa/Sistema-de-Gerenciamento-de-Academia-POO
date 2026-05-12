package dao;

import model.Instrutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Instrutor.
 */
public class InstrutorDAO {

    // ── CREATE

    public boolean inserir(Instrutor instrutor) {
        String sql = "INSERT INTO instrutor (nome, cpf, idade, telefone, cref, especialidade, salario, alunos_orientados) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, instrutor.getNome());
            stmt.setString(2, instrutor.getCpf());
            stmt.setInt(3, instrutor.getIdade());
            stmt.setString(4, instrutor.getTelefone());
            stmt.setString(5, instrutor.getCref());
            stmt.setString(6, instrutor.getEspecialidade());
            stmt.setDouble(7, instrutor.getSalario());
            stmt.setInt(8, instrutor.getAlunosOrientados());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) instrutor.setId(rs.getInt(1));
                }
                System.out.println("✅ Instrutor inserido com sucesso! ID: " + instrutor.getId());
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir instrutor: " + e.getMessage());
            return false;
        }
    }

    // ── READ: buscar por ID 

    public Instrutor buscarPorId(int id) {
        String sql = "SELECT * FROM instrutor WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return extrairInstrutorDoResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar instrutor: " + e.getMessage());
        }
        return null;
    }

    // ── READ: listar todos

    public List<Instrutor> listarTodos() {
        List<Instrutor> instrutores = new ArrayList<>();
        String sql = "SELECT * FROM instrutor ORDER BY nome";

        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) instrutores.add(extrairInstrutorDoResultSet(rs));
            System.out.println("✅ " + instrutores.size() + " instrutor(es) encontrado(s)");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar instrutores: " + e.getMessage());
        }
        return instrutores;
    }

    // ── UPDATE
    public boolean atualizar(Instrutor instrutor) {
        String sql = "UPDATE instrutor SET nome = ?, cpf = ?, idade = ?, telefone = ?, " +
                     "cref = ?, especialidade = ?, salario = ?, alunos_orientados = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, instrutor.getNome());
            stmt.setString(2, instrutor.getCpf());
            stmt.setInt(3, instrutor.getIdade());
            stmt.setString(4, instrutor.getTelefone());
            stmt.setString(5, instrutor.getCref());
            stmt.setString(6, instrutor.getEspecialidade());
            stmt.setDouble(7, instrutor.getSalario());
            stmt.setInt(8, instrutor.getAlunosOrientados());
            stmt.setInt(9, instrutor.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Instrutor atualizado com sucesso!");
                return true;
            } else {
                System.out.println("⚠ Nenhum instrutor encontrado com ID: " + instrutor.getId());
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar instrutor: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE

    public boolean excluir(int id) {
        String sql = "DELETE FROM instrutor WHERE id = ?";

        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Instrutor excluído com sucesso!");
                return true;
            } else {
                System.out.println("⚠ Nenhum instrutor encontrado com ID: " + id);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir instrutor: " + e.getMessage());
            return false;
        }
    }

    // ── Auxiliar
    private Instrutor extrairInstrutorDoResultSet(ResultSet rs) throws SQLException {
        Instrutor instrutor = new Instrutor();
        instrutor.setId(rs.getInt("id"));
        instrutor.setNome(rs.getString("nome"));
        instrutor.setCpf(rs.getString("cpf"));
        instrutor.setIdade(rs.getInt("idade"));
        instrutor.setTelefone(rs.getString("telefone"));
        instrutor.setCref(rs.getString("cref"));
        instrutor.setEspecialidade(rs.getString("especialidade"));
        instrutor.setSalario(rs.getDouble("salario"));
        instrutor.setAlunosOrientados(rs.getInt("alunos_orientados"));
        return instrutor;
    }
}
