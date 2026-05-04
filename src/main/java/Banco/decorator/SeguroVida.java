package Banco.decorator;

import Banco.conta.Conta;

public class SeguroVida extends ContaDecorator {

    private static final double TAXA_SEGURO_VIDA = 29.90;

    public SeguroVida(Conta conta) {
        super(conta);
    }

    @Override
    public String getDescricao() {
        return contaDecorada.getDescricao() + " + Seguro de Vida";
    }

    @Override
    public double getTaxaMensal() {
        return contaDecorada.getTaxaMensal() + TAXA_SEGURO_VIDA;
    }
}
