import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    // Lista polimórfica — armazena Aluno, Instrutor e Funcionario
    private static ArrayList<Pessoa> pessoas = new ArrayList<>();

    private static ArrayList<FichaTreino> fichas = new ArrayList<>();
    private static ArrayList<AvaliacaoFisica> avaliacoes = new ArrayList<>();
    private static ArrayList<Pagamento> pagamentos = new ArrayList<>();

    private static int proximoId = 1;
    private static int proximoIdFicha = 1;
    private static int proximoIdAv = 1;
    private static int proximoIdPag = 1;

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GERENCIAMENTO ACADEMIA  ║");
        System.out.println("║              Checkpoint 2            ║");
        System.out.println("╚══════════════════════════════════════╝");

        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Opção: ");
            switch (opcao) {
                case 1: menuCadastro(); break;
                case 2: listarTodos(); break;
                case 3: listarPorTipo(); break;
                case 4: menuFichas(); break;
                case 5: menuAvaliacoes(); break;
                case 6: menuPagamentos(); break;
                case 0: System.out.println("\nEncerrando... Até logo!"); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }

    // ── Menus ──────────────────────────────────────────────────────────────────

    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Cadastrar Pessoa (Aluno / Instrutor / Funcionário)");
        System.out.println("2. Listar Todas as Pessoas (Polimorfismo)");
        System.out.println("3. Listar por Tipo");
        System.out.println("4. Fichas de Treino");
        System.out.println("5. Avaliações Físicas");
        System.out.println("6. Pagamentos");
        System.out.println("0. Sair");
    }

    private static void menuCadastro() {
        System.out.println("\n--- CADASTRAR ---");
        System.out.println("1. Aluno");
        System.out.println("2. Instrutor");
        System.out.println("3. Funcionário");
        int op = lerInt("Tipo: ");
        switch (op) {
            case 1: cadastrarAluno(); break;
            case 2: cadastrarInstrutor(); break;
            case 3: cadastrarFuncionario(); break;
            default: System.out.println("Tipo inválido.");
        }
    }

    private static void menuFichas() {
        int op;
        do {
            System.out.println("\n--- FICHAS DE TREINO ---");
            System.out.println("1. Criar Ficha");
            System.out.println("2. Adicionar Exercício");
            System.out.println("3. Listar Fichas");
            System.out.println("0. Voltar");
            op = lerInt("Opção: ");
            switch (op) {
                case 1: criarFicha(); break;
                case 2: adicionarExercicio(); break;
                case 3: listarFichas(); break;
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
            op = lerInt("Opção: ");
            switch (op) {
                case 1: registrarAvaliacao(); break;
                case 2: listarAvaliacoes(); break;
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
            op = lerInt("Opção: ");
            switch (op) {
                case 1: registrarMensalidade(); break;
                case 2: confirmarPagamento(); break;
                case 3: listarPagamentos(); break;
            }
        } while (op != 0);
    }

    // ── Cadastros ─────────────────────────────────────────────────────────────

    private static String[] lerDadosBase(String tipo) {
        System.out.println("\n--- CADASTRAR " + tipo.toUpperCase() + " ---");
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("CPF (11 dígitos): "); String cpf = scanner.nextLine();
        System.out.print("Telefone: "); String tel = scanner.nextLine();
        return new String[]{nome, cpf, tel};
    }

    private static void cadastrarAluno() {
        try {
            String[] base = lerDadosBase("Aluno");
            int idade = lerInt("Idade: ");
            double peso = lerDouble("Peso (kg): ");
            double altura = lerDouble("Altura (m): ");
            System.out.print("Plano (Mensal/Trimestral/Anual): "); String plano = scanner.nextLine();
            int meses = lerInt("Meses cadastrado: ");

            Aluno a = new Aluno(proximoId++, base[0], base[1], idade, base[2], peso, altura, plano, meses);
            pessoas.add(a);
            System.out.println("Cadastrado! " + a);
        } catch (IllegalArgumentException e) { System.out.println("Erro: " + e.getMessage()); }
    }

    private static void cadastrarInstrutor() {
        try {
            String[] base = lerDadosBase("Instrutor");
            int idade = lerInt("Idade: ");
            System.out.print("CREF: "); String cref = scanner.nextLine();
            System.out.print("Especialidade: "); String espec = scanner.nextLine();
            double salario = lerDouble("Salário (R$): ");

            Instrutor i = new Instrutor(proximoId++, base[0], base[1], idade, base[2], cref, espec, salario);
            pessoas.add(i);
            System.out.println("Cadastrado! " + i);
        } catch (IllegalArgumentException e) { System.out.println("Erro: " + e.getMessage()); }
    }

    private static void cadastrarFuncionario() {
        try {
            String[] base = lerDadosBase("Funcionário");
            int idade = lerInt("Idade: ");
            System.out.print("Cargo: "); String cargo = scanner.nextLine();
            System.out.print("Setor: "); String setor = scanner.nextLine();
            double salario = lerDouble("Salário (R$): ");
            int horas = lerInt("Horas semanais (max 44): ");

            Funcionario f = new Funcionario(proximoId++, base[0], base[1], idade, base[2], cargo, setor, salario, horas);
            pessoas.add(f);
            System.out.println("Cadastrado! " + f);
        } catch (IllegalArgumentException e) { System.out.println("Erro: " + e.getMessage()); }
    }

    // ── Listagens Polimórficas ─────────────────────────────────────────────────

    private static void listarTodos() {
        System.out.println("\n=== TODAS AS PESSOAS (Polimorfismo) ===");
        if (pessoas.isEmpty()) { System.out.println("Nenhuma pessoa cadastrada."); return; }
        for (Pessoa p : pessoas) {
            // Polimorfismo: chama getTipo() e exibirInfo() da subclasse correta
            System.out.printf("[%s] ", p.getTipo());
            p.exibirInfo();
        }
    }

    private static void listarPorTipo() {
        System.out.println("\n1. Alunos  2. Instrutores  3. Funcionários");
        int op = lerInt("Tipo: ");
        System.out.println();
        boolean encontrou = false;
        for (Pessoa p : pessoas) {
            if (op == 1 && p instanceof Aluno)       { p.exibirInfo(true); System.out.println(); encontrou = true; }
            if (op == 2 && p instanceof Instrutor)   { p.exibirInfo(true); System.out.println(); encontrou = true; }
            if (op == 3 && p instanceof Funcionario) { p.exibirInfo(true); System.out.println(); encontrou = true; }
        }
        if (!encontrou) System.out.println("Nenhum registro encontrado.");
    }

    // ── Fichas ────────────────────────────────────────────────────────────────

    private static void criarFicha() {
        ArrayList<Aluno> alunos = getAlunos();
        ArrayList<Instrutor> instrutores = getInstrutores();
        if (alunos.isEmpty() || instrutores.isEmpty()) {
            System.out.println("Cadastre ao menos um aluno e um instrutor."); return;
        }
        System.out.println("Alunos:"); for (Aluno a : alunos) System.out.println(a);
        int idA = lerInt("ID do Aluno: ");
        Aluno aluno = (Aluno) buscarPorId(idA);
        if (aluno == null) { System.out.println("Aluno não encontrado."); return; }

        System.out.println("Instrutores:"); for (Instrutor i : instrutores) System.out.println(i);
        int idI = lerInt("ID do Instrutor: ");
        Instrutor instrutor = (Instrutor) buscarPorId(idI);
        if (instrutor == null) { System.out.println("Instrutor não encontrado."); return; }

        System.out.print("Objetivo: "); String obj = scanner.nextLine();
        System.out.print("Data de início (dd/mm/aaaa): "); String data = scanner.nextLine();
        try {
            FichaTreino f = new FichaTreino(proximoIdFicha++, aluno, instrutor, obj, data);
            fichas.add(f);
            System.out.println("Ficha criada! " + f);
        } catch (IllegalArgumentException e) { System.out.println("Erro: " + e.getMessage()); }
    }

    private static void adicionarExercicio() {
        listarFichas();
        if (fichas.isEmpty()) return;
        int id = lerInt("ID da Ficha: ");
        FichaTreino ficha = null;
        for (FichaTreino f : fichas) if (f.getId() == id) { ficha = f; break; }
        if (ficha == null) { System.out.println("Ficha não encontrada."); return; }
        System.out.print("Exercício (ex: Supino 4x12): "); String ex = scanner.nextLine();
        try { ficha.adicionarExercicio(ex); System.out.println("Adicionado! " + ficha); }
        catch (IllegalArgumentException e) { System.out.println("Erro: " + e.getMessage()); }
    }

    private static void listarFichas() {
        System.out.println("\n--- FICHAS ---");
        if (fichas.isEmpty()) { System.out.println("Nenhuma ficha."); return; }
        for (FichaTreino f : fichas) System.out.println(f);
    }

    // ── Avaliações ────────────────────────────────────────────────────────────

    private static void registrarAvaliacao() {
        ArrayList<Aluno> alunos = getAlunos();
        if (alunos.isEmpty()) { System.out.println("Cadastre um aluno primeiro."); return; }
        for (Aluno a : alunos) System.out.println(a);
        int id = lerInt("ID do Aluno: ");
        Aluno aluno = (Aluno) buscarPorId(id);
        if (aluno == null) { System.out.println("Aluno não encontrado."); return; }
        double peso = lerDouble("Peso atual (kg): ");
        double altura = lerDouble("Altura atual (m): ");
        double gordura = lerDouble("% Gordura: ");
        System.out.print("Data (dd/mm/aaaa): "); String data = scanner.nextLine();
        try {
            AvaliacaoFisica av = new AvaliacaoFisica(proximoIdAv++, aluno, peso, altura, gordura, data);
            avaliacoes.add(av);
            System.out.printf("Registrado! IMC=%.2f (%s)%n", av.calcularImc(), av.classificarImc());
        } catch (IllegalArgumentException e) { System.out.println("Erro: " + e.getMessage()); }
    }

    private static void listarAvaliacoes() {
        System.out.println("\n--- AVALIAÇÕES ---");
        if (avaliacoes.isEmpty()) { System.out.println("Nenhuma avaliação."); return; }
        for (AvaliacaoFisica a : avaliacoes) System.out.println(a);
    }

    // ── Pagamentos ────────────────────────────────────────────────────────────

    private static void registrarMensalidade() {
        ArrayList<Aluno> alunos = getAlunos();
        if (alunos.isEmpty()) { System.out.println("Cadastre um aluno primeiro."); return; }
        for (Aluno a : alunos) System.out.println(a);
        int id = lerInt("ID do Aluno: ");
        Aluno aluno = (Aluno) buscarPorId(id);
        if (aluno == null) { System.out.println("Aluno não encontrado."); return; }
        double valor = lerDouble("Valor (R$): ");
        double desc = lerDouble("Desconto (%): ");
        System.out.print("Vencimento (dd/mm/aaaa): "); String venc = scanner.nextLine();
        try {
            Pagamento p = new Pagamento(proximoIdPag++, aluno, valor, desc, venc);
            pagamentos.add(p);
            System.out.printf("Registrado! Valor final: R$%.2f%n", p.getValorFinal());
        } catch (IllegalArgumentException e) { System.out.println("Erro: " + e.getMessage()); }
    }

    private static void confirmarPagamento() {
        listarPagamentos();
        if (pagamentos.isEmpty()) return;
        int id = lerInt("ID do Pagamento: ");
        for (Pagamento p : pagamentos) {
            if (p.getId() == id) {
                System.out.print("Data do pagamento (dd/mm/aaaa): "); String data = scanner.nextLine();
                p.registrarPagamento(data);
                System.out.println("Confirmado! " + p); return;
            }
        }
        System.out.println("Pagamento não encontrado.");
    }

    private static void listarPagamentos() {
        System.out.println("\n--- PAGAMENTOS ---");
        if (pagamentos.isEmpty()) { System.out.println("Nenhum pagamento."); return; }
        for (Pagamento p : pagamentos) System.out.println(p);
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private static ArrayList<Aluno> getAlunos() {
        ArrayList<Aluno> lista = new ArrayList<>();
        for (Pessoa p : pessoas) if (p instanceof Aluno) lista.add((Aluno) p);
        return lista;
    }

    private static ArrayList<Instrutor> getInstrutores() {
        ArrayList<Instrutor> lista = new ArrayList<>();
        for (Pessoa p : pessoas) if (p instanceof Instrutor) lista.add((Instrutor) p);
        return lista;
    }

    private static Pessoa buscarPorId(int id) {
        for (Pessoa p : pessoas) if (p.getId() == id) return p;
        return null;
    }

    private static int lerInt(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) { System.out.print("Número inválido. " + msg); scanner.next(); }
        int v = scanner.nextInt(); scanner.nextLine(); return v;
    }

    private static double lerDouble(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextDouble()) { System.out.print("Número inválido. " + msg); scanner.next(); }
        double v = scanner.nextDouble(); scanner.nextLine(); return v;
    }
}
