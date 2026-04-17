public class AvaliacaoFisica {

    private int id;
    private Aluno aluno;
    private double pesoAtual;
    private double alturaAtual;
    private double percentualGordura;
    private String dataAvaliacao;

    public AvaliacaoFisica(int id, Aluno aluno, double pesoAtual, double alturaAtual,
                           double percentualGordura, String dataAvaliacao) {
        setId(id);
        setAluno(aluno);
        setPesoAtual(pesoAtual);
        setAlturaAtual(alturaAtual);
        setPercentualGordura(percentualGordura);
        setDataAvaliacao(dataAvaliacao);
    }

    // Getters
    public int getId() { return id; }
    public Aluno getAluno() { return aluno; }
    public double getPesoAtual() { return pesoAtual; }
    public double getAlturaAtual() { return alturaAtual; }
    public double getPercentualGordura() { return percentualGordura; }
    public String getDataAvaliacao() { return dataAvaliacao; }

    // Setters com validação
    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID deve ser positivo.");
        this.id = id;
    }

    public void setAluno(Aluno aluno) {
        if (aluno == null) throw new IllegalArgumentException("Aluno não pode ser nulo.");
        this.aluno = aluno;
    }

    public void setPesoAtual(double pesoAtual) {
        if (pesoAtual <= 0) throw new IllegalArgumentException("Peso deve ser positivo.");
        this.pesoAtual = pesoAtual;
    }

    public void setAlturaAtual(double alturaAtual) {
        if (alturaAtual <= 0 || alturaAtual > 3.0)
            throw new IllegalArgumentException("Altura inválida.");
        this.alturaAtual = alturaAtual;
    }

    public void setPercentualGordura(double percentualGordura) {
        if (percentualGordura < 0 || percentualGordura > 100)
            throw new IllegalArgumentException("Percentual de gordura inválido.");
        this.percentualGordura = percentualGordura;
    }

    public void setDataAvaliacao(String dataAvaliacao) {
        if (dataAvaliacao == null || dataAvaliacao.trim().isEmpty())
            throw new IllegalArgumentException("Data da avaliação não pode ser vazia.");
        this.dataAvaliacao = dataAvaliacao;
    }

    // ── Regra de Negócio Complexa ──────────────────────────────────────────────

    public double calcularImc() {
        return pesoAtual / (alturaAtual * alturaAtual);
    }

    public String classificarImc() {
        double imc = calcularImc();
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 25.0) return "Normal";
        if (imc < 30.0) return "Sobrepeso";
        return "Obesidade";
    }

    /**
     * Calcula desconto progressivo na mensalidade:
     * - IMC Normal + 12+ meses  → 15%
     * - IMC Normal + 6-11 meses → 10%
     * - IMC fora + 12+ meses    →  5%
     * - Demais casos            →  0%
     */
    public double calcularDescontoMensalidade() {
        String classificacao = classificarImc();
        int meses = aluno.getMesesCadastrado();
        boolean imcNormal = classificacao.equals("Normal");

        if (imcNormal && meses >= 12) return 15.0;
        if (imcNormal && meses >= 6)  return 10.0;
        if (!imcNormal && meses >= 12) return 5.0;
        return 0.0;
    }

    // ──────────────────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format(
                "AvaliacaoFisica[ID=%d, Aluno=%s, Peso=%.1fkg, Altura=%.2fm, IMC=%.2f (%s), Gordura=%.1f%%, Desconto=%.0f%%, Data=%s]",
                id, aluno.getNome(), pesoAtual, alturaAtual,
                calcularImc(), classificarImc(), percentualGordura,
                calcularDescontoMensalidade(), dataAvaliacao);
    }
}
