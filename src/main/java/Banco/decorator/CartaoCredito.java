package Banco.decorator;

import Banco.conta.Conta;

public class CartaoCredito extends ContaDecorator {

    private static final double ANUIDADE_MENSAL = 19.90;
    private final int limiteCredito;

    public CartaoCredito(Conta conta, int limiteCredito) {
        super(conta);
        this.limiteCredito = limiteCredito;
    }

    @Override
    public String getDescricao() {
        return contaDecorada.getDescricao()
                + " + Cartão de Crédito (limite: R$ " + limiteCredito + ",00)";
    }

    @Override
    public double getTaxaMensal() {
        return contaDecorada.getTaxaMensal() + ANUIDADE_MENSAL;
    }

    public int getLimiteCredito() {
        return limiteCredito;
    }
}
