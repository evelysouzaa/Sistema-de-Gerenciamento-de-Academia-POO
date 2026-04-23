public class Pessoa {

    protected int id;
    protected String nome;
    protected String cpf;
    protected int idade;
    protected String telefone;

    public Pessoa(int id, String nome, String cpf, int idade, String telefone) {
        setId(id);
        setNome(nome);
        setCpf(cpf);
        setIdade(idade);
        setTelefone(telefone);
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public int getIdade() { return idade; }
    public String getTelefone() { return telefone; }

    // Setters com validação
    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID deve ser positivo.");
        this.id = id;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().length() < 11)
            throw new IllegalArgumentException("CPF inválido.");
        this.cpf = cpf;
    }

    public void setIdade(int idade) {
        if (idade < 14 || idade > 100)
            throw new IllegalArgumentException("Idade deve ser entre 14 e 100 anos.");
        this.idade = idade;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty())
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        this.telefone = telefone;
    }

    // Método polimórfico — cada subclasse terá sua apresentação
    public String getTipo() {
        return "Pessoa";
    }

    // Sobrecarga: exibir com ou sem detalhe
    public void exibirInfo() {
        System.out.println(this);
    }

    public void exibirInfo(boolean detalhado) {
        if (detalhado) {
            System.out.println("=== DETALHES ===");
            System.out.println("Tipo    : " + getTipo());
            System.out.println("ID      : " + id);
            System.out.println("Nome    : " + nome);
            System.out.println("CPF     : " + cpf);
            System.out.println("Idade   : " + idade);
            System.out.println("Telefone: " + telefone);
        } else {
            exibirInfo();
        }
    }

    @Override
    public String toString() {
        return String.format("Pessoa[ID=%d, Nome=%s, CPF=%s, Idade=%d, Tel=%s]",
                id, nome, cpf, idade, telefone);
    }
}
