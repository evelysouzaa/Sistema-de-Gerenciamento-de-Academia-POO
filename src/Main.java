import dao.*;
import model.*;
import util.ValidadorUtil;

import java.util.List;
import java.util.Scanner;

/**
 * Ponto de entrada do Sistema de Gerenciamento de Academia.
 * Checkpoint 3 — Classe Abstrata + Padrão DAO + PostgreSQL via JDBC
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // DAOs
    private static final AlunoDAO      alunoDAO      = new AlunoDAO();
    private static final InstrutorDAO  instrutorDAO  = new InstrutorDAO();
    private static final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private static final PagamentoDAO  pagamentoDAO  = new PagamentoDAO();

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GERENCIAMENTO ACADEMIA  ║");
        System.out.println("║                                      ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Testa conexão ao iniciar
        if (!ConexaoBD.testarConexao()) {
            System.out.println("⚠ Sistema iniciado sem banco de dados.");
            System.out.println("  Configure ConexaoBD.java e garanta que o PostgreSQL está ativo.");
        }

        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Opção: ");
            switch (opcao) {
                case 1: menuAlunos();       break;
                case 2: menuInstrutores();  break;
                case 3: menuFuncionarios(); break;
                case 4: menuPagamentos();   break;
                case 0: System.out.println("\nEncerrando... Até logo!"); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }

    // ── Menu principal ────────────────────────────────────────────────────────

    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gerenciar Alunos");
        System.out.println("2. Gerenciar Instrutores");
        System.out.println("3. Gerenciar Funcionários");
        System.out.println("4. Gerenciar Pagamentos");
        System.out.println("0. Sair");
    }

    // ── Submenus CRUD ─────────────────────────────────────────────────────────

    private static void menuAlunos() {
        int op;
        do {
            System.out.println("\n--- ALUNOS ---");
            System.out.println("1. Cadastrar  2. Buscar por ID  3. Listar todos  4. Atualizar  5. Excluir  0. Voltar");
            op = lerInt("Opção: ");
            switch (op) {
                case 1: cadastrarAluno();          break;
                case 2: buscarAlunoPorId();        break;
                case 3: listarAlunos();            break;
                case 4: atualizarAluno();          break;
                case 5: excluirAluno();            break;
            }
        } while (op != 0);
    }

    private static void menuInstrutores() {
        int op;
        do {
            System.out.println("\n--- INSTRUTORES ---");
            System.out.println("1. Cadastrar  2. Buscar por ID  3. Listar todos  4. Atualizar  5. Excluir  0. Voltar");
            op = lerInt("Opção: ");
            switch (op) {
                case 1: cadastrarInstrutor();       break;
                case 2: buscarInstrutorPorId();     break;
                case 3: listarInstrutores();        break;
                case 4: atualizarInstrutor();       break;
                case 5: excluirInstrutor();         break;
            }
        } while (op != 0);
    }

    private static void menuFuncionarios() {
        int op;
        do {
            System.out.println("\n--- FUNCIONÁRIOS ---");
            System.out.println("1. Cadastrar  2. Buscar por ID  3. Listar todos  4. Atualizar  5. Excluir  0. Voltar");
            op = lerInt("Opção: ");
            switch (op) {
                case 1: cadastrarFuncionario();     break;
                case 2: buscarFuncionarioPorId();   break;
                case 3: listarFuncionarios();       break;
                case 4: atualizarFuncionario();     break;
                case 5: excluirFuncionario();       break;
            }
        } while (op != 0);
    }

    private static void menuPagamentos() {
        int op;
        do {
            System.out.println("\n--- PAGAMENTOS ---");
            System.out.println("1. Registrar  2. Buscar por ID  3. Listar todos  4. Confirmar pagamento  5. Excluir  0. Voltar");
            op = lerInt("Opção: ");
            switch (op) {
                case 1: registrarPagamento();       break;
                case 2: buscarPagamentoPorId();     break;
                case 3: listarPagamentos();         break;
                case 4: confirmarPagamento();       break;
                case 5: excluirPagamento();         break;
            }
        } while (op != 0);
    }

    // ── Operações: Aluno ──────────────────────────────────────────────────────

    private static void cadastrarAluno() {
        try {
            System.out.println("\n--- CADASTRAR ALUNO ---");
            System.out.print("Nome: ");      String nome = scanner.nextLine();
            System.out.print("CPF: ");       String cpf  = scanner.nextLine();
            System.out.print("Telefone: ");  String tel  = scanner.nextLine();
            int    idade  = lerInt("Idade: ");
            double peso   = lerDouble("Peso (kg): ");
            double altura = lerDouble("Altura (m): ");
            System.out.print("Plano (Mensal/Trimestral/Anual): "); String plano = scanner.nextLine();
            int meses = lerInt("Meses cadastrado: ");

            // Usa ID 1 temporariamente; o banco irá sobrescrever com o real
            Aluno aluno = new Aluno(1, nome, cpf, idade, tel, peso, altura, plano, meses);
            alunoDAO.inserir(aluno);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        }
    }

    private static void buscarAlunoPorId() {
        int id = lerInt("ID do aluno: ");
        Aluno aluno = alunoDAO.buscarPorId(id);
        if (aluno != null) aluno.exibirInfo(true);
        else System.out.println("Aluno não encontrado.");
    }

    private static void listarAlunos() {
        List<Aluno> alunos = alunoDAO.listarTodos();
        if (alunos.isEmpty()) { System.out.println("Nenhum aluno cadastrado."); return; }
        System.out.println("\n=== ALUNOS ===");
        // Polimorfismo: chama getTipo() e exibirInfo() da subclasse Aluno
        for (Aluno a : alunos) {
            System.out.printf("[%s] ", a.getTipo());
            a.exibirInfo();
        }
    }

    private static void atualizarAluno() {
        int id = lerInt("ID do aluno a atualizar: ");
        Aluno aluno = alunoDAO.buscarPorId(id);
        if (aluno == null) { System.out.println("Aluno não encontrado."); return; }

        System.out.println("Dados atuais: " + aluno);
        try {
            System.out.print("Novo nome (Enter para manter): "); String nome = scanner.nextLine();
            if (!nome.trim().isEmpty()) aluno.setNome(nome);

            System.out.print("Novo plano (Enter para manter): "); String plano = scanner.nextLine();
            if (!plano.trim().isEmpty()) aluno.setPlano(plano);

            double peso = lerDouble("Novo peso (0 para manter): ");
            if (peso > 0) aluno.setPeso(peso);

            alunoDAO.atualizar(aluno);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        }
    }

    private static void excluirAluno() {
        int id = lerInt("ID do aluno a excluir: ");
        alunoDAO.excluir(id);
    }

    // ── Operações: Instrutor ──────────────────────────────────────────────────

    private static void cadastrarInstrutor() {
        try {
            System.out.println("\n--- CADASTRAR INSTRUTOR ---");
            System.out.print("Nome: ");         String nome  = scanner.nextLine();
            System.out.print("CPF: ");          String cpf   = scanner.nextLine();
            System.out.print("Telefone: ");     String tel   = scanner.nextLine();
            int    idade  = lerInt("Idade: ");
            System.out.print("CREF: ");         String cref  = scanner.nextLine();
            System.out.print("Especialidade: ");String espec = scanner.nextLine();
            double salario = lerDouble("Salário (R$): ");

            Instrutor instrutor = new Instrutor(1, nome, cpf, idade, tel, cref, espec, salario);
            instrutorDAO.inserir(instrutor);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        }
    }

    private static void buscarInstrutorPorId() {
        int id = lerInt("ID do instrutor: ");
        Instrutor instrutor = instrutorDAO.buscarPorId(id);
        if (instrutor != null) instrutor.exibirInfo(true);
        else System.out.println("Instrutor não encontrado.");
    }

    private static void listarInstrutores() {
        List<Instrutor> instrutores = instrutorDAO.listarTodos();
        if (instrutores.isEmpty()) { System.out.println("Nenhum instrutor cadastrado."); return; }
        System.out.println("\n=== INSTRUTORES ===");
        for (Instrutor i : instrutores) {
            System.out.printf("[%s] ", i.getTipo());
            i.exibirInfo();
        }
    }

    private static void atualizarInstrutor() {
        int id = lerInt("ID do instrutor a atualizar: ");
        Instrutor instrutor = instrutorDAO.buscarPorId(id);
        if (instrutor == null) { System.out.println("Instrutor não encontrado."); return; }

        System.out.println("Dados atuais: " + instrutor);
        try {
            System.out.print("Nova especialidade (Enter para manter): "); String espec = scanner.nextLine();
            if (!espec.trim().isEmpty()) instrutor.setEspecialidade(espec);

            double salario = lerDouble("Novo salário (0 para manter): ");
            if (salario > 0) instrutor.setSalario(salario);

            instrutorDAO.atualizar(instrutor);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        }
    }

    private static void excluirInstrutor() {
        int id = lerInt("ID do instrutor a excluir: ");
        instrutorDAO.excluir(id);
    }

    // ── Operações: Funcionario ────────────────────────────────────────────────

    private static void cadastrarFuncionario() {
        try {
            System.out.println("\n--- CADASTRAR FUNCIONÁRIO ---");
            System.out.print("Nome: ");    String nome  = scanner.nextLine();
            System.out.print("CPF: ");     String cpf   = scanner.nextLine();
            System.out.print("Telefone: ");String tel   = scanner.nextLine();
            int    idade   = lerInt("Idade: ");
            System.out.print("Cargo: ");   String cargo = scanner.nextLine();
            System.out.print("Setor: ");   String setor = scanner.nextLine();
            double salario = lerDouble("Salário (R$): ");
            int    horas   = lerInt("Horas semanais (max 44): ");

            Funcionario funcionario = new Funcionario(1, nome, cpf, idade, tel, cargo, setor, salario, horas);
            funcionarioDAO.inserir(funcionario);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        }
    }

    private static void buscarFuncionarioPorId() {
        int id = lerInt("ID do funcionário: ");
        Funcionario f = funcionarioDAO.buscarPorId(id);
        if (f != null) f.exibirInfo(true);
        else System.out.println("Funcionário não encontrado.");
    }

    private static void listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();
        if (funcionarios.isEmpty()) { System.out.println("Nenhum funcionário cadastrado."); return; }
        System.out.println("\n=== FUNCIONÁRIOS ===");
        for (Funcionario f : funcionarios) {
            System.out.printf("[%s] ", f.getTipo());
            f.exibirInfo();
        }
    }

    private static void atualizarFuncionario() {
        int id = lerInt("ID do funcionário a atualizar: ");
        Funcionario f = funcionarioDAO.buscarPorId(id);
        if (f == null) { System.out.println("Funcionário não encontrado."); return; }

        System.out.println("Dados atuais: " + f);
        try {
            System.out.print("Novo cargo (Enter para manter): "); String cargo = scanner.nextLine();
            if (!cargo.trim().isEmpty()) f.setCargo(cargo);

            double salario = lerDouble("Novo salário (0 para manter): ");
            if (salario > 0) f.setSalario(salario);

            funcionarioDAO.atualizar(f);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        }
    }

    private static void excluirFuncionario() {
        int id = lerInt("ID do funcionário a excluir: ");
        funcionarioDAO.excluir(id);
    }

    // ── Operações: Pagamento ──────────────────────────────────────────────────

    private static void registrarPagamento() {
        List<Aluno> alunos = alunoDAO.listarTodos();
        if (alunos.isEmpty()) { System.out.println("Cadastre um aluno primeiro."); return; }

        System.out.println("Alunos disponíveis:");
        for (Aluno a : alunos) System.out.println("  " + a);

        int idAluno = lerInt("ID do Aluno: ");
        Aluno aluno = alunoDAO.buscarPorId(idAluno);
        if (aluno == null) { System.out.println("Aluno não encontrado."); return; }

        try {
            double valor   = lerDouble("Valor mensalidade (R$): ");
            double desconto = lerDouble("Desconto (%): ");
            System.out.print("Data de vencimento (dd/mm/aaaa): "); String venc = scanner.nextLine();

            Pagamento p = new Pagamento(1, aluno, valor, desconto, venc);
            pagamentoDAO.inserir(p);
            System.out.printf("Valor final: R$%.2f%n", p.getValorFinal());

        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        }
    }

    private static void buscarPagamentoPorId() {
        int id = lerInt("ID do pagamento: ");
        Pagamento p = pagamentoDAO.buscarPorId(id);
        if (p != null) System.out.println(p);
        else System.out.println("Pagamento não encontrado.");
    }

    private static void listarPagamentos() {
        List<Pagamento> pagamentos = pagamentoDAO.listarTodos();
        if (pagamentos.isEmpty()) { System.out.println("Nenhum pagamento registrado."); return; }
        System.out.println("\n=== PAGAMENTOS ===");
        for (Pagamento p : pagamentos) System.out.println(p);
    }

    private static void confirmarPagamento() {
        int id = lerInt("ID do pagamento: ");
        Pagamento p = pagamentoDAO.buscarPorId(id);
        if (p == null) { System.out.println("Pagamento não encontrado."); return; }

        System.out.print("Data do pagamento (dd/mm/aaaa): "); String data = scanner.nextLine();
        p.registrarPagamento(data);
        pagamentoDAO.atualizar(p);
    }

    private static void excluirPagamento() {
        int id = lerInt("ID do pagamento a excluir: ");
        pagamentoDAO.excluir(id);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static int lerInt(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) {
            System.out.print("Número inválido. " + msg);
            scanner.next();
        }
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }

    private static double lerDouble(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextDouble()) {
            System.out.print("Número inválido. " + msg);
            scanner.next();
        }
        double v = scanner.nextDouble();
        scanner.nextLine();
        return v;
    }
}
