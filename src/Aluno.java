public class Aluno {

    private int id;
    private String nome;
    private int idade;
    private double peso;
    private double altura;
    private String plano; // Mensal, Trimestral, Anual
    private int mesesCadastrado;

    public Aluno(int id, String nome, int idade, double peso, double altura, String plano, int mesesCadastrado) {
        setId(id);
        setNome(nome);
        setIdade(idade);
        setPeso(peso);
        setAltura(altura);
        setPlano(plano);
        setMesesCadastrado(mesesCadastrado);
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public double getPeso() { return peso; }
    public double getAltura() { return altura; }
    public String getPlano() { return plano; }
    public int getMesesCadastrado() { return mesesCadastrado; }

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

    public void setIdade(int idade) {
        if (idade < 14 || idade > 100)
            throw new IllegalArgumentException("Idade deve ser entre 14 e 100 anos.");
        this.idade = idade;
    }

    public void setPeso(double peso) {
        if (peso <= 0) throw new IllegalArgumentException("Peso deve ser positivo.");
        this.peso = peso;
    }

    public void setAltura(double altura) {
        if (altura <= 0 || altura > 3.0)
            throw new IllegalArgumentException("Altura inválida.");
        this.altura = altura;
    }

    public void setPlano(String plano) {
        if (plano == null || plano.trim().isEmpty())
            throw new IllegalArgumentException("Plano não pode ser vazio.");
        this.plano = plano;
    }

    public void setMesesCadastrado(int mesesCadastrado) {
        if (mesesCadastrado < 0)
            throw new IllegalArgumentException("Meses cadastrado não pode ser negativo.");
        this.mesesCadastrado = mesesCadastrado;
    }

    @Override
    public String toString() {
        return String.format("Aluno[ID=%d, Nome=%s, Idade=%d, Peso=%.1fkg, Altura=%.2fm, Plano=%s, Tempo=%d mês(es)]",
                id, nome, idade, peso, altura, plano, mesesCadastrado);
    }
}
