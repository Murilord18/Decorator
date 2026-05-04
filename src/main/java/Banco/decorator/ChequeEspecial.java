package Banco.decorator;

import Banco.conta.Conta;

public class ChequeEspecial extends ContaDecorator {

    private static final double TAXA_CHEQUE_ESPECIAL = 9.90;
    private final double limite;

    public ChequeEspecial(Conta conta, double limite) {
        super(conta);
        this.limite = limite;
    }

    @Override
    public String getDescricao() {
        return contaDecorada.getDescricao()
                + " + Cheque Especial (limite: R$ " + String.format("%.2f", limite) + ")";
    }

    @Override
    public double getTaxaMensal() {
        return contaDecorada.getTaxaMensal() + TAXA_CHEQUE_ESPECIAL;
    }

    public double getLimite() {
        return limite;
    }
}
