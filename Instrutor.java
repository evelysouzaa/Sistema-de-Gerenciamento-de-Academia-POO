public class Instrutor {

    private int id;
    private String nome;
    private String cref;
    private String especialidade;
    private double salario;

    public Instrutor(int id, String nome, String cref, String especialidade, double salario) {
        setId(id);
        setNome(nome);
        setCref(cref);
        setEspecialidade(especialidade);
        setSalario(salario);
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCref() { return cref; }
    public String getEspecialidade() { return especialidade; }
    public double getSalario() { return salario; }

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

    public void setCref(String cref) {
        if (cref == null || cref.trim().isEmpty())
            throw new IllegalArgumentException("CREF não pode ser vazio.");
        this.cref = cref;
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty())
            throw new IllegalArgumentException("Especialidade não pode ser vazia.");
        this.especialidade = especialidade;
    }

    public void setSalario(double salario) {
        if (salario < 0) throw new IllegalArgumentException("Salário não pode ser negativo.");
        this.salario = salario;
    }

    @Override
    public String toString() {
        return String.format("Instrutor[ID=%d, Nome=%s, CREF=%s, Especialidade=%s, Salário=R$%.2f]",
                id, nome, cref, especialidade, salario);
    }
}
