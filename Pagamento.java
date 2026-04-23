public class Pagamento {

    private int id;
    private Aluno aluno;
    private double valorMensalidade;
    private double desconto;
    private String dataVencimento;
    private String dataPagamento;
    private String status;

    public Pagamento(int id, Aluno aluno, double valorMensalidade, double desconto, String dataVencimento) {
        setId(id);
        setAluno(aluno);
        setValorMensalidade(valorMensalidade);
        setDesconto(desconto);
        setDataVencimento(dataVencimento);
        this.dataPagamento = null;
        this.status = "Pendente";
    }

    public int getId() { return id; }
    public Aluno getAluno() { return aluno; }
    public double getValorMensalidade() { return valorMensalidade; }
    public double getDesconto() { return desconto; }
    public String getDataVencimento() { return dataVencimento; }
    public String getStatus() { return status; }
    public double getValorFinal() { return valorMensalidade * (1 - desconto / 100.0); }

    public void setId(int id) { if (id <= 0) throw new IllegalArgumentException("ID inválido."); this.id = id; }
    public void setAluno(Aluno aluno) { if (aluno == null) throw new IllegalArgumentException("Aluno nulo."); this.aluno = aluno; }
    public void setValorMensalidade(double v) { if (v <= 0) throw new IllegalArgumentException("Valor inválido."); valorMensalidade = v; }
    public void setDesconto(double d) { if (d < 0 || d > 100) throw new IllegalArgumentException("Desconto inválido."); desconto = d; }
    public void setDataVencimento(String d) { if (d == null || d.trim().isEmpty()) throw new IllegalArgumentException("Data inválida."); dataVencimento = d; }

    public void registrarPagamento(String dataPagamento) {
        if (dataPagamento == null || dataPagamento.trim().isEmpty())
            throw new IllegalArgumentException("Data de pagamento inválida.");
        this.dataPagamento = dataPagamento;
        this.status = "Pago";
    }

    @Override
    public String toString() {
        return String.format("Pagamento[ID=%d, Aluno=%s, Mensalidade=R$%.2f, Desconto=%.0f%%, Final=R$%.2f, Venc=%s, Status=%s]",
                id, aluno.getNome(), valorMensalidade, desconto, getValorFinal(), dataVencimento, status);
    }
}
