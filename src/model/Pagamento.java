package model;

import util.Auditavel;
import util.Calculavel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pagamento implements Auditavel, Calculavel {

    private int id;
    private Aluno aluno;
    private double valorMensalidade;
    private double desconto;
    private String dataVencimento;
    private String dataPagamento;
    private String status;
    private final List<String> historico = new ArrayList<>();

    public Pagamento() {}

    public Pagamento(int id, Aluno aluno, double valorMensalidade, double desconto, String dataVencimento) {
        setId(id);
        setAluno(aluno);
        setValorMensalidade(valorMensalidade);
        setDesconto(desconto);
        setDataVencimento(dataVencimento);
        this.dataPagamento = null;
        this.status = "Pendente";
        registrarLog("Pagamento criado para o aluno " + aluno.getNome());
    }

    public int getId()                  { return id; }
    public Aluno getAluno()             { return aluno; }
    public double getValorMensalidade() { return valorMensalidade; }
    public double getDesconto()         { return desconto; }
    public String getDataVencimento()   { return dataVencimento; }
    public String getDataPagamento()    { return dataPagamento; }
    public String getStatus()           { return status; }
    public double getValorFinal()       { return calcularValorFinal(); }

    public void setId(int id)                         { if (id <= 0) throw new IllegalArgumentException("ID inválido."); this.id = id; }
    public void setAluno(Aluno aluno)                 { if (aluno == null) throw new IllegalArgumentException("Aluno nulo."); this.aluno = aluno; }
    public void setValorMensalidade(double v)         { if (v <= 0) throw new IllegalArgumentException("Valor inválido."); valorMensalidade = v; }
    public void setDesconto(double d)                 { if (d < 0 || d > 100) throw new IllegalArgumentException("Desconto inválido."); desconto = d; }
    public void setDataVencimento(String d)           { if (d == null || d.trim().isEmpty()) throw new IllegalArgumentException("Data inválida."); dataVencimento = d; }
    public void setDataPagamento(String dataPagamento){ this.dataPagamento = dataPagamento; }
    public void setStatus(String status)              { this.status = status; }


    @Override
    public void registrarLog(String acao) {
        historico.add(LocalDateTime.now() + " - " + acao);
    }

    @Override
    public String obterHistorico() {
        return String.join("\n", historico);
    }

    @Override
    public double calcularTotal() {
        return valorMensalidade;
    }

    @Override
    public double calcularDesconto() {
        return valorMensalidade * (desconto / 100.0);
    }

    @Override
    public double calcularValorFinal() {
        return calcularTotal() - calcularDesconto();
    }

    public void registrarPagamento(String dataPagamento) {
        if (dataPagamento == null || dataPagamento.trim().isEmpty())
            throw new IllegalArgumentException("Data de pagamento inválida.");
        this.dataPagamento = dataPagamento;
        this.status = "Pago";
        registrarLog("Pagamento confirmado em " + dataPagamento);
    }

    @Override
    public String toString() {
        return String.format("Pagamento[ID=%d, Aluno=%s, Mensalidade=R$%.2f, Desconto=%.0f%%, Final=R$%.2f, Venc=%s, Status=%s]",
                id, aluno.getNome(), valorMensalidade, desconto, getValorFinal(), dataVencimento, status);
    }
}
