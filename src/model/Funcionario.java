package model;

public class Funcionario extends Pessoa {

    private String cargo;
    private String setor;
    private double salario;
    private int horasSemanais;

    public Funcionario() {}

    public Funcionario(int id, String nome, String cpf, int idade, String telefone,
                       String cargo, String setor, double salario, int horasSemanais) {
        super(id, nome, cpf, idade, telefone);
        setCargo(cargo);
        setSetor(setor);
        setSalario(salario);
        setHorasSemanais(horasSemanais);
    }

    // Getters
    public String getCargo()      { return cargo; }
    public String getSetor()      { return setor; }
    public double getSalario()    { return salario; }
    public int getHorasSemanais() { return horasSemanais; }

    // Setters
    public void setCargo(String cargo) {
        if (cargo == null || cargo.trim().isEmpty())
            throw new IllegalArgumentException("Cargo não pode ser vazio.");
        this.cargo = cargo;
    }

    public void setSetor(String setor) {
        if (setor == null || setor.trim().isEmpty())
            throw new IllegalArgumentException("Setor não pode ser vazio.");
        this.setor = setor;
    }

    public void setSalario(double salario) {
        if (salario < 0) throw new IllegalArgumentException("Salário não pode ser negativo.");
        this.salario = salario;
    }

    public void setHorasSemanais(int horasSemanais) {
        if (horasSemanais <= 0 || horasSemanais > 44)
            throw new IllegalArgumentException("Horas semanais deve ser entre 1 e 44.");
        this.horasSemanais = horasSemanais;
    }

    // Sobrecarga: calcular horas extras
    public double calcularHoraExtra() {
        return calcularHoraExtra(0);
    }

    public double calcularHoraExtra(int horasExtras) {
        if (horasExtras < 0) throw new IllegalArgumentException("Horas extras não pode ser negativo.");
        double valorHora = salario / (horasSemanais * 4.0);
        return valorHora * 1.5 * horasExtras;
    }

    public double calcularHoraExtra(int horasExtras, double multiplicador) {
        double valorHora = salario / (horasSemanais * 4.0);
        return valorHora * multiplicador * horasExtras;
    }

    @Override
    public String getTipo() {
        return "Funcionario";
    }

    @Override
    public void exibirDetalhesEspecificos() {
        System.out.println("Cargo   : " + cargo);
        System.out.println("Setor   : " + setor);
        System.out.printf("Salário : R$%.2f%n", salario);
        System.out.println("Horas/s : " + horasSemanais + "h");
    }

    @Override
    public String toString() {
        return String.format("Funcionario[ID=%d, Nome=%s, Cargo=%s, Setor=%s, Salário=R$%.2f]",
                id, nome, cargo, setor, salario);
    }
}
