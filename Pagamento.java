public class Pagamento {

    private int id;
    private Aluno aluno;
    private double valorMensalidade;
    private double desconto; // percentual de desconto aplicado
    private String dataVencimento;
    private String dataPagamento; // null se ainda não pago
    private String status; // "Pago" ou "Pendente"

    public Pagamento(int id, Aluno aluno, double valorMensalidade, double desconto,
                     String dataVencimento) {
        setId(id);
        setAluno(aluno);
        setValorMensalidade(valorMensalidade);
        setDesconto(desconto);
        setDataVencimento(dataVencimento);
        this.dataPagamento = null;
        this.status = "Pendente";
    }

    // Getters
    public int getId() { return id; }
    public Aluno getAluno() { return aluno; }
    public double getValorMensalidade() { return valorMensalidade; }
    public double getDesconto() { return desconto; }
    public String getDataVencimento() { return dataVencimento; }
    public String getDataPagamento() { return dataPagamento; }
    public String getStatus() { return status; }

    public double getValorFinal() {
        return valorMensalidade * (1 - desconto / 100.0);
    }

    // Setters com validação
    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID deve ser positivo.");
        this.id = id;
    }

    public void setAluno(Aluno aluno) {
        if (aluno == null) throw new IllegalArgumentException("Aluno não pode ser nulo.");
        this.aluno = aluno;
    }

    public void setValorMensalidade(double valorMensalidade) {
        if (valorMensalidade <= 0)
            throw new IllegalArgumentException("Valor da mensalidade deve ser positivo.");
        this.valorMensalidade = valorMensalidade;
    }

    public void setDesconto(double desconto) {
        if (desconto < 0 || desconto > 100)
            throw new IllegalArgumentException("Desconto deve estar entre 0 e 100.");
        this.desconto = desconto;
    }

    public void setDataVencimento(String dataVencimento) {
        if (dataVencimento == null || dataVencimento.trim().isEmpty())
            throw new IllegalArgumentException("Data de vencimento não pode ser vazia.");
        this.dataVencimento = dataVencimento;
    }

    public void registrarPagamento(String dataPagamento) {
        if (dataPagamento == null || dataPagamento.trim().isEmpty())
            throw new IllegalArgumentException("Data de pagamento não pode ser vazia.");
        this.dataPagamento = dataPagamento;
        this.status = "Pago";
    }

    @Override
    public String toString() {
        return String.format(
                "Pagamento[ID=%d, Aluno=%s, Mensalidade=R$%.2f, Desconto=%.0f%%, Valor Final=R$%.2f, Vencimento=%s, Status=%s]",
                id, aluno.getNome(), valorMensalidade, desconto, getValorFinal(), dataVencimento, status);
    }
}
