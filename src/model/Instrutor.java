package model;

public class Instrutor extends Pessoa {

    private String cref;
    private String especialidade;
    private double salario;
    private int alunosOrientados;

    public Instrutor() {}

    public Instrutor(int id, String nome, String cpf, int idade, String telefone,
                     String cref, String especialidade, double salario) {
        super(id, nome, cpf, idade, telefone);
        setCref(cref);
        setEspecialidade(especialidade);
        setSalario(salario);
        this.alunosOrientados = 0;
    }

    // Getters
    public String getCref()          { return cref; }
    public String getEspecialidade() { return especialidade; }
    public double getSalario()       { return salario; }
    public int getAlunosOrientados() { return alunosOrientados; }

    // Setters
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

    public void setAlunosOrientados(int alunosOrientados) {
        if (alunosOrientados < 0) throw new IllegalArgumentException("Alunos orientados não pode ser negativo.");
        this.alunosOrientados = alunosOrientados;
    }

    public void incrementarAlunosOrientados() {
        alunosOrientados++;
    }

    // Sobrecarga: calcular bônus sem parâmetro (10%) ou com percentual informado
    public double calcularBonus() {
        return calcularBonus(10.0);
    }

    public double calcularBonus(double percentual) {
        if (percentual < 0) throw new IllegalArgumentException("Percentual não pode ser negativo.");
        return salario * (percentual / 100.0);
    }

    public double calcularBonus(int alunosExtra) {
        double bonusExtra = alunosExtra > 10 ? (alunosExtra - 10) * 50.0 : 0;
        return calcularBonus() + bonusExtra;
    }

    @Override
    public String getTipo() {
        return "Instrutor";
    }

    @Override
    public void exibirDetalhesEspecificos() {
        System.out.println("CREF    : " + cref);
        System.out.println("Espec.  : " + especialidade);
        System.out.printf("Salário : R$%.2f (Bônus: R$%.2f)%n", salario, calcularBonus());
        System.out.println("Alunos  : " + alunosOrientados);
    }

    @Override
    public String toString() {
        return String.format("Instrutor[ID=%d, Nome=%s, CREF=%s, Espec=%s, Salário=R$%.2f, Alunos=%d]",
                id, nome, cref, especialidade, salario, alunosOrientados);
    }
}
