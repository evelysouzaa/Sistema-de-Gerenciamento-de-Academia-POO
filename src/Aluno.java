public class Aluno extends Pessoa {

    private double peso;
    private double altura;
    private String plano; // Mensal, Trimestral, Anual
    private int mesesCadastrado;

    public Aluno(int id, String nome, String cpf, int idade, String telefone,
                 double peso, double altura, String plano, int mesesCadastrado) {
        super(id, nome, cpf, idade, telefone); // chama construtor de Pessoa
        setPeso(peso);
        setAltura(altura);
        setPlano(plano);
        setMesesCadastrado(mesesCadastrado);
    }

    // Getters
    public double getPeso() { return peso; }
    public double getAltura() { return altura; }
    public String getPlano() { return plano; }
    public int getMesesCadastrado() { return mesesCadastrado; }

    // Setters com validação
    public void setPeso(double peso) {
        if (peso <= 0) throw new IllegalArgumentException("Peso deve ser positivo.");
        this.peso = peso;
    }

    public void setAltura(double altura) {
        if (altura <= 0 || altura > 3.0) throw new IllegalArgumentException("Altura inválida.");
        this.altura = altura;
    }

    public void setPlano(String plano) {
        if (plano == null || plano.trim().isEmpty())
            throw new IllegalArgumentException("Plano não pode ser vazio.");
        this.plano = plano;
    }

    public void setMesesCadastrado(int mesesCadastrado) {
        if (mesesCadastrado < 0)
            throw new IllegalArgumentException("Meses não pode ser negativo.");
        this.mesesCadastrado = mesesCadastrado;
    }

    public double calcularImc() {
        return peso / (altura * altura);
    }

    // Sobrecarga: desconto simples ou com número de meses informado
    public double calcularDesconto() {
        return calcularDesconto(mesesCadastrado);
    }

    public double calcularDesconto(int meses) {
        String imc = classificarImc();
        boolean normal = imc.equals("Normal");
        if (normal && meses >= 12) return 15.0;
        if (normal && meses >= 6)  return 10.0;
        if (!normal && meses >= 12) return 5.0;
        return 0.0;
    }

    public String classificarImc() {
        double imc = calcularImc();
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 25.0) return "Normal";
        if (imc < 30.0) return "Sobrepeso";
        return "Obesidade";
    }

    @Override
    public String getTipo() {
        return "Aluno";
    }

    @Override
    public void exibirInfo(boolean detalhado) {
        super.exibirInfo(detalhado); // chama comportamento base
        if (detalhado) {
            System.out.printf("Plano   : %s (%d meses)%n", plano, mesesCadastrado);
            System.out.printf("IMC     : %.2f → %s%n", calcularImc(), classificarImc());
            System.out.printf("Desconto: %.0f%%%n", calcularDesconto());
        }
    }

    @Override
    public String toString() {
        return String.format("Aluno[ID=%d, Nome=%s, Plano=%s, IMC=%.2f(%s), Desconto=%.0f%%]",
                id, nome, plano, calcularImc(), classificarImc(), calcularDesconto());
    }
}
