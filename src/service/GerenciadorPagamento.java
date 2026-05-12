package service;

import model.Pagamento;

public class GerenciadorPagamento {

    public boolean verificarPagamentoEmDia(Pagamento pagamento) {
        return "Pago".equalsIgnoreCase(pagamento.getStatus());
    }

    public double calcularMultaAtraso(Pagamento pagamento, int diasAtraso) {
        if (diasAtraso <= 0) {
            return pagamento.calcularValorFinal();
        }

        double multa = pagamento.calcularValorFinal() * 0.02;
        double juros = pagamento.calcularValorFinal() * 0.001 * diasAtraso;

        return pagamento.calcularValorFinal() + multa + juros;
    }
}
