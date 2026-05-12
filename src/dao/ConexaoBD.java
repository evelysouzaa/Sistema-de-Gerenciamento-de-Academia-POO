package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gerencia a conexão JDBC com o PostgreSQL
 */
public class ConexaoBD {

    // ── Configurações — altere conforme seu ambiente 
    private static final String URL      = "jdbc:postgresql://localhost:5433/academia";
    private static final String USUARIO  = "postgres";
    private static final String SENHA    = "1234";


    /**
     * Retorna uma nova conexão com o banco de dados.
     */
    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL não encontrado. Adicione o JAR ao classpath.", e);
        }
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    /**
     * testa a conexão com o banco
     */
    public static boolean testarConexao() {
        try (Connection conn = getConexao()) {
            System.out.println("✅ Conexão com o banco estabelecida com sucesso!");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Falha na conexão: " + e.getMessage());
            System.err.println("   Verifique: URL=" + URL + ", Usuário=" + USUARIO);
            return false;
        }
    }
}
