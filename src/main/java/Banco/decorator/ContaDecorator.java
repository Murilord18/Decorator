package Banco.decorator;

import Banco.conta.Conta;

public abstract class ContaDecorator implements Conta {
    protected final Conta contaDecorada;

    public ContaDecorator(Conta conta) {
        this.contaDecorada = conta;
    }

    @Override
    public String getDescricao() {
        return contaDecorada.getDescricao();
    }

    @Override
    public double getTaxaMensal() {
        return contaDecorada.getTaxaMensal();
    }

}
