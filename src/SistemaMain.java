import java.util.Scanner;
import java.util.ArrayList;

public class SistemaMain {

    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Aluno> alunos = new ArrayList<>();
    private static ArrayList<Instrutor> instrutores = new ArrayList<>();
    private static ArrayList<FichaTreino> fichas = new ArrayList<>();
    private static ArrayList<AvaliacaoFisica> avaliacoes = new ArrayList<>();
    private static ArrayList<Pagamento> pagamentos = new ArrayList<>();

    private static int proximoIdAluno = 1;
    private static int proximoIdInstrutor = 1;
    private static int proximoIdFicha = 1;
    private static int proximoIdAvaliacao = 1;
    private static int proximoIdPagamento = 1;

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GERENCIAMENTO ACADEMIA  ║");
        System.out.println("╚══════════════════════════════════════╝");

        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1: menuAlunos(); break;
                case 2: menuInstrutores(); break;
                case 3: menuFichasTreino(); break;
                case 4: menuAvaliacoes(); break;
                case 5: menuPagamentos(); break;
                case 0: System.out.println("\nEncerrando sistema. Até logo!"); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }

    // ── Menus ──────────────────────────────────────────────────────────────────

    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gerenciar Alunos");
        System.out.println("2. Gerenciar Instrutores");
        System.out.println("3. Fichas de Treino");
        System.out.println("4. Avaliações Físicas");
        System.out.println("5. Pagamentos");
        System.out.println("0. Sair");
    }

    private static void menuAlunos() {
        int op;
        do {
            System.out.println("\n--- ALUNOS ---");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Listar Alunos");
            System.out.println("0. Voltar");
            op = lerInteiro("Opção: ");
            switch (op) {
                case 1: cadastrarAluno(); break;
                case 2: listarAlunos(); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void menuInstrutores() {
        int op;
        do {
            System.out.println("\n--- INSTRUTORES ---");
            System.out.println("1. Cadastrar Instrutor");
            System.out.println("2. Listar Instrutores");
            System.out.println("0. Voltar");
            op = lerInteiro("Opção: ");
            switch (op) {
                case 1: cadastrarInstrutor(); break;
                case 2: listarInstrutores(); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void menuFichasTreino() {
        int op;
        do {
            System.out.println("\n--- FICHAS DE TREINO ---");
            System.out.println("1. Criar Ficha de Treino");
            System.out.println("2. Adicionar Exercício à Ficha");
            System.out.println("3. Listar Fichas");
            System.out.println("0. Voltar");
            op = lerInteiro("Opção: ");
            switch (op) {
                case 1: criarFichaTreino(); break;
                case 2: adicionarExercicio(); break;
                case 3: listarFichas(); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void menuAvaliacoes() {
        int op;
        do {
            System.out.println("\n--- AVALIAÇÕES FÍSICAS ---");
            System.out.println("1. Registrar Avaliação");
            System.out.println("2. Listar Avaliações");
            System.out.println("0. Voltar");
            op = lerInteiro("Opção: ");
            switch (op) {
                case 1: registrarAvaliacao(); break;
                case 2: listarAvaliacoes(); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void menuPagamentos() {
        int op;
        do {
            System.out.println("\n--- PAGAMENTOS ---");
            System.out.println("1. Registrar Mensalidade");
            System.out.println("2. Confirmar Pagamento");
            System.out.println("3. Listar Pagamentos");
            System.out.println("0. Voltar");
            op = lerInteiro("Opção: ");
            switch (op) {
                case 1: registrarMensalidade(); break;
                case 2: confirmarPagamento(); break;
                case 3: listarPagamentos(); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    // ── Operações de Aluno ─────────────────────────────────────────────────────

    private static void cadastrarAluno() {
        System.out.println("\n--- CADASTRAR ALUNO ---");
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            int idade = lerInteiro("Idade: ");
            double peso = lerDouble("Peso (kg): ");
            double altura = lerDouble("Altura (m): ");
            System.out.print("Plano (Mensal/Trimestral/Anual): ");
            String plano = scanner.nextLine();
            int meses = lerInteiro("Meses cadastrado: ");

            Aluno aluno = new Aluno(proximoIdAluno++, nome, idade, peso, altura, plano, meses);
            alunos.add(aluno);
            System.out.println("Aluno cadastrado com sucesso! " + aluno);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarAlunos() {
        System.out.println("\n--- LISTA DE ALUNOS ---");
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            for (Aluno a : alunos) System.out.println(a);
        }
    }

    // ── Operações de Instrutor ─────────────────────────────────────────────────

    private static void cadastrarInstrutor() {
        System.out.println("\n--- CADASTRAR INSTRUTOR ---");
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CREF: ");
            String cref = scanner.nextLine();
            System.out.print("Especialidade: ");
            String especialidade = scanner.nextLine();
            double salario = lerDouble("Salário (R$): ");

            Instrutor instrutor = new Instrutor(proximoIdInstrutor++, nome, cref, especialidade, salario);
            instrutores.add(instrutor);
            System.out.println("Instrutor cadastrado! " + instrutor);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarInstrutores() {
        System.out.println("\n--- LISTA DE INSTRUTORES ---");
        if (instrutores.isEmpty()) {
            System.out.println("Nenhum instrutor cadastrado.");
        } else {
            for (Instrutor i : instrutores) System.out.println(i);
        }
    }

    // ── Operações de Ficha de Treino ───────────────────────────────────────────

    private static void criarFichaTreino() {
        System.out.println("\n--- CRIAR FICHA DE TREINO ---");
        if (alunos.isEmpty() || instrutores.isEmpty()) {
            System.out.println("Cadastre ao menos um aluno e um instrutor primeiro.");
            return;
        }
        listarAlunos();
        int idAluno = lerInteiro("ID do Aluno: ");
        Aluno aluno = buscarAluno(idAluno);
        if (aluno == null) { System.out.println("Aluno não encontrado."); return; }

        listarInstrutores();
        int idInstrutor = lerInteiro("ID do Instrutor: ");
        Instrutor instrutor = buscarInstrutor(idInstrutor);
        if (instrutor == null) { System.out.println("Instrutor não encontrado."); return; }

        System.out.print("Objetivo (Hipertrofia/Emagrecimento/Condicionamento): ");
        String objetivo = scanner.nextLine();
        System.out.print("Data de Início (dd/mm/aaaa): ");
        String data = scanner.nextLine();

        try {
            FichaTreino ficha = new FichaTreino(proximoIdFicha++, aluno, instrutor, objetivo, data);
            fichas.add(ficha);
            System.out.println("Ficha criada! " + ficha);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void adicionarExercicio() {
        listarFichas();
        if (fichas.isEmpty()) return;
        int idFicha = lerInteiro("ID da Ficha: ");
        FichaTreino ficha = buscarFicha(idFicha);
        if (ficha == null) { System.out.println("Ficha não encontrada."); return; }

        System.out.print("Exercício (ex: Supino 4x12): ");
        String exercicio = scanner.nextLine();
        try {
            ficha.adicionarExercicio(exercicio);
            System.out.println("Exercício adicionado! " + ficha);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarFichas() {
        System.out.println("\n--- FICHAS DE TREINO ---");
        if (fichas.isEmpty()) {
            System.out.println("Nenhuma ficha criada.");
        } else {
            for (FichaTreino f : fichas) System.out.println(f);
        }
    }

    // ── Operações de Avaliação Física ──────────────────────────────────────────

    private static void registrarAvaliacao() {
        System.out.println("\n--- REGISTRAR AVALIAÇÃO FÍSICA ---");
        if (alunos.isEmpty()) { System.out.println("Cadastre um aluno primeiro."); return; }
        listarAlunos();
        int idAluno = lerInteiro("ID do Aluno: ");
        Aluno aluno = buscarAluno(idAluno);
        if (aluno == null) { System.out.println("Aluno não encontrado."); return; }

        double peso = lerDouble("Peso atual (kg): ");
        double altura = lerDouble("Altura atual (m): ");
        double gordura = lerDouble("Percentual de gordura (%): ");
        System.out.print("Data da avaliação (dd/mm/aaaa): ");
        String data = scanner.nextLine();

        try {
            AvaliacaoFisica av = new AvaliacaoFisica(proximoIdAvaliacao++, aluno, peso, altura, gordura, data);
            avaliacoes.add(av);
            System.out.println("Avaliação registrada!");
            System.out.printf("IMC: %.2f → %s%n", av.calcularImc(), av.classificarImc());
            System.out.printf("Desconto na mensalidade: %.0f%%%n", av.calcularDescontoMensalidade());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarAvaliacoes() {
        System.out.println("\n--- AVALIAÇÕES FÍSICAS ---");
        if (avaliacoes.isEmpty()) {
            System.out.println("Nenhuma avaliação registrada.");
        } else {
            for (AvaliacaoFisica a : avaliacoes) System.out.println(a);
        }
    }

    // ── Operações de Pagamento ─────────────────────────────────────────────────

    private static void registrarMensalidade() {
        System.out.println("\n--- REGISTRAR MENSALIDADE ---");
        if (alunos.isEmpty()) { System.out.println("Cadastre um aluno primeiro."); return; }
        listarAlunos();
        int idAluno = lerInteiro("ID do Aluno: ");
        Aluno aluno = buscarAluno(idAluno);
        if (aluno == null) { System.out.println("Aluno não encontrado."); return; }

        double valor = lerDouble("Valor da mensalidade (R$): ");
        double desconto = lerDouble("Desconto (%): ");
        System.out.print("Data de vencimento (dd/mm/aaaa): ");
        String vencimento = scanner.nextLine();

        try {
            Pagamento pag = new Pagamento(proximoIdPagamento++, aluno, valor, desconto, vencimento);
            pagamentos.add(pag);
            System.out.printf("Mensalidade registrada! Valor final: R$%.2f%n", pag.getValorFinal());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void confirmarPagamento() {
        listarPagamentos();
        if (pagamentos.isEmpty()) return;
        int idPag = lerInteiro("ID do Pagamento: ");
        for (Pagamento p : pagamentos) {
            if (p.getId() == idPag) {
                System.out.print("Data do pagamento (dd/mm/aaaa): ");
                String data = scanner.nextLine();
                p.registrarPagamento(data);
                System.out.println("Pagamento confirmado! " + p);
                return;
            }
        }
        System.out.println("Pagamento não encontrado.");
    }

    private static void listarPagamentos() {
        System.out.println("\n--- PAGAMENTOS ---");
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
        } else {
            for (Pagamento p : pagamentos) System.out.println(p);
        }
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private static Aluno buscarAluno(int id) {
        for (Aluno a : alunos) if (a.getId() == id) return a;
        return null;
    }

    private static Instrutor buscarInstrutor(int id) {
        for (Instrutor i : instrutores) if (i.getId() == id) return i;
        return null;
    }

    private static FichaTreino buscarFicha(int id) {
        for (FichaTreino f : fichas) if (f.getId() == id) return f;
        return null;
    }

    private static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private static double lerDouble(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextDouble()) {
            System.out.print("Digite um número válido: ");
            scanner.next();
        }
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }
}
