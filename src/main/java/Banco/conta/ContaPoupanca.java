package Banco.conta;

public class ContaPoupanca implements Conta {

    private final String titular;

    public ContaPoupanca(String titular) {
        this.titular = titular;
    }

    @Override
    public String getDescricao() {
        return "Conta Poupança [titular: " + titular + "]";
    }

    @Override
    public double getTaxaMensal() {
        return 0.0; // Poupança não tem taxa mensal
    }
}
