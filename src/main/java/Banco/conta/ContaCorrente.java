package Banco.conta;

public class ContaCorrente implements Conta {

    private final String titular;

    public ContaCorrente(String titular) {
        this.titular = titular;
    }

    @Override
    public String getDescricao() {
        return "Conta Corrente [titular: " + titular + "]";
    }

    @Override
    public double getTaxaMensal() {
        return 12.90; // Taxa básica da conta corrente
    }
}
