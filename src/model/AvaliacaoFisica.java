package model;

public class AvaliacaoFisica {

    private int id;
    private Aluno aluno;
    private double pesoAtual;
    private double alturaAtual;
    private double percentualGordura;
    private String dataAvaliacao;

    public AvaliacaoFisica() {}

    public AvaliacaoFisica(int id, Aluno aluno, double pesoAtual, double alturaAtual,
                           double percentualGordura, String dataAvaliacao) {
        setId(id);
        setAluno(aluno);
        setPesoAtual(pesoAtual);
        setAlturaAtual(alturaAtual);
        setPercentualGordura(percentualGordura);
        setDataAvaliacao(dataAvaliacao);
    }

    public int getId()                  { return id; }
    public Aluno getAluno()             { return aluno; }
    public double getPesoAtual()        { return pesoAtual; }
    public double getAlturaAtual()      { return alturaAtual; }
    public double getPercentualGordura(){ return percentualGordura; }
    public String getDataAvaliacao()    { return dataAvaliacao; }

    public void setId(int id)                          { if (id <= 0) throw new IllegalArgumentException("ID inválido."); this.id = id; }
    public void setAluno(Aluno aluno)                  { if (aluno == null) throw new IllegalArgumentException("Aluno nulo."); this.aluno = aluno; }
    public void setPesoAtual(double p)                 { if (p <= 0) throw new IllegalArgumentException("Peso inválido."); pesoAtual = p; }
    public void setAlturaAtual(double a)               { if (a <= 0 || a > 3) throw new IllegalArgumentException("Altura inválida."); alturaAtual = a; }
    public void setPercentualGordura(double g)         { if (g < 0 || g > 100) throw new IllegalArgumentException("Gordura inválida."); percentualGordura = g; }
    public void setDataAvaliacao(String d)             { if (d == null || d.trim().isEmpty()) throw new IllegalArgumentException("Data inválida."); dataAvaliacao = d; }

    public double calcularImc() { return pesoAtual / (alturaAtual * alturaAtual); }

    public String classificarImc() {
        double imc = calcularImc();
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 25.0) return "Normal";
        if (imc < 30.0) return "Sobrepeso";
        return "Obesidade";
    }

    @Override
    public String toString() {
        return String.format("AvaliacaoFisica[ID=%d, Aluno=%s, IMC=%.2f(%s), Gordura=%.1f%%, Data=%s]",
                id, aluno.getNome(), calcularImc(), classificarImc(), percentualGordura, dataAvaliacao);
    }
}
