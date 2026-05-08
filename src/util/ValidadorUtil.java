package util;

/**
 * Utilitários de validação para o sistema da academia.
 */
public class ValidadorUtil {

    private ValidadorUtil() {} // classe utilitária — não instanciar

    /**
     * Valida CPF: apenas dígitos, exatamente 11 caracteres.
     */
    public static boolean validarCpf(String cpf) {
        if (cpf == null) return false;
        String soDigitos = cpf.replaceAll("[^0-9]", "");
        return soDigitos.length() == 11;
    }

    /**
     * Verifica se uma string não é nula nem vazia.
     */
    public static boolean naoVazio(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    /**
     * Valida se um valor double é positivo.
     */
    public static boolean positivo(double valor) {
        return valor > 0;
    }

    /**
     * Valida se um valor inteiro está dentro de um intervalo inclusivo.
     */
    public static boolean emIntervalo(int valor, int min, int max) {
        return valor >= min && valor <= max;
    }

    /**
     * Valida formato de data simples dd/mm/aaaa.
     */
    public static boolean validarData(String data) {
        if (data == null) return false;
        return data.matches("\\d{2}/\\d{2}/\\d{4}");
    }

    /**
     * Valida se o plano informado é um dos aceitos pelo sistema.
     */
    public static boolean validarPlano(String plano) {
        if (plano == null) return false;
        String p = plano.trim().toLowerCase();
        return p.equals("mensal") || p.equals("trimestral") || p.equals("anual");
    }
}
