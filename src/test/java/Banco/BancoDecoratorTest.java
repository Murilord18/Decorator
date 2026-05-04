package Banco;

import Banco.conta.Conta;
import Banco.conta.ContaCorrente;
import Banco.conta.ContaPoupanca;
import Banco.decorator.CartaoCredito;
import Banco.decorator.ChequeEspecial;
import Banco.decorator.SeguroVida;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Testes - Padrão Decorator: Sistema Bancário")
class BancoDecoratorTest {


    // Contas base reutilizadas nos testes


    private Conta contaCorrente;
    private Conta contaPoupanca;

    @BeforeEach
    void setUp() {
        contaCorrente = new ContaCorrente("João Silva");
        contaPoupanca = new ContaPoupanca("Maria Oliveira");
    }


    // Contas concretas (componentes base)


    @Nested
    @DisplayName("ContaCorrente - componente base")
    class ContaCorrenteTest {

        @Test
        @DisplayName("Deve retornar a descrição correta")
        void deveRetornarDescricaoCorreta() {
            assertEquals(
                    "Conta Corrente [titular: João Silva]",
                    contaCorrente.getDescricao()
            );
        }

        @Test
        @DisplayName("Deve ter taxa mensal de R$ 12,90")
        void deveTerTaxaMensalCorreta() {
            assertEquals(12.90, contaCorrente.getTaxaMensal(), 0.001);
        }
    }

    @Nested
    @DisplayName("ContaPoupanca - componente base")
    class ContaPoupancaTest {

        @Test
        @DisplayName("Deve retornar a descrição correta")
        void deveRetornarDescricaoCorreta() {
            assertEquals(
                    "Conta Poupança [titular: Maria Oliveira]",
                    contaPoupanca.getDescricao()
            );
        }

        @Test
        @DisplayName("Deve ter taxa mensal zero (isenta)")
        void deveTerTaxaZero() {
            assertEquals(0.0, contaPoupanca.getTaxaMensal(), 0.001);
        }
    }


    // Decorators individuais


    @Nested
    @DisplayName("Decorator: SeguroVida")
    class SeguroVidaTest {

        @Test
        @DisplayName("Deve acrescentar 'Seguro de Vida' na descrição")
        void deveAdicionarSeguroNaDescricao() {
            Conta contaComSeguro = new SeguroVida(contaCorrente);
            assertTrue(contaComSeguro.getDescricao().contains("Seguro de Vida"));
        }

        @Test
        @DisplayName("Deve somar R$ 29,90 à taxa da conta corrente")
        void deveAdicionarTaxaSeguroNaContaCorrente() {
            Conta contaComSeguro = new SeguroVida(contaCorrente);
            assertEquals(12.90 + 29.90, contaComSeguro.getTaxaMensal(), 0.001);
        }

        @Test
        @DisplayName("Deve somar R$ 29,90 à poupança (que é isenta)")
        void deveAdicionarTaxaSeguroNaPoupanca() {
            Conta poupancaComSeguro = new SeguroVida(contaPoupanca);
            assertEquals(29.90, poupancaComSeguro.getTaxaMensal(), 0.001);
        }
    }

    @Nested
    @DisplayName("Decorator: CartaoCredito")
    class CartaoCreditoTest {

        @Test
        @DisplayName("Deve acrescentar cartão e limite na descrição")
        void deveAdicionarCartaoNaDescricao() {
            Conta contaComCartao = new CartaoCredito(contaCorrente, 5000);
            String descricao = contaComCartao.getDescricao();
            assertTrue(descricao.contains("Cartão de Crédito"));
            assertTrue(descricao.contains("5000"));
        }

        @Test
        @DisplayName("Deve somar R$ 19,90 de anuidade mensal")
        void deveAdicionarAnuidadeMensal() {
            Conta contaComCartao = new CartaoCredito(contaCorrente, 5000);
            assertEquals(12.90 + 19.90, contaComCartao.getTaxaMensal(), 0.001);
        }

        @Test
        @DisplayName("Deve preservar o limite de crédito informado")
        void devePreservarLimiteDeCredito() {
            CartaoCredito cartao = new CartaoCredito(contaCorrente, 8000);
            assertEquals(8000, cartao.getLimiteCredito());
        }
    }

    @Nested
    @DisplayName("Decorator: ChequeEspecial")
    class ChequeEspecialTest {

        @Test
        @DisplayName("Deve acrescentar cheque especial e limite na descrição")
        void deveAdicionarChequeNaDescricao() {
            Conta contaComCheque = new ChequeEspecial(contaCorrente, 1500.0);
            assertTrue(contaComCheque.getDescricao().contains("Cheque Especial"));
        }

        @Test
        @DisplayName("Deve somar R$ 9,90 à taxa mensal")
        void deveAdicionarTaxaChequeEspecial() {
            Conta contaComCheque = new ChequeEspecial(contaCorrente, 1500.0);
            assertEquals(12.90 + 9.90, contaComCheque.getTaxaMensal(), 0.001);
        }

        @Test
        @DisplayName("Deve preservar o limite do cheque especial")
        void devePreservarLimiteChequeEspecial() {
            ChequeEspecial cheque = new ChequeEspecial(contaCorrente, 2000.0);
            assertEquals(2000.0, cheque.getLimite(), 0.001);
        }
    }


    // Composição de múltiplos decorators (empilhamento)


    @Nested
    @DisplayName("Composição de Decorators (empilhamento)")
    class ComposicaoDecoratorsTest {

        @Test
        @DisplayName("ContaCorrente + Cartão + SeguroVida: taxa deve ser 12,90 + 19,90 + 29,90")
        void deveAcumularTaxasCorretamente() {
            Conta pacotePremium = new SeguroVida(
                    new CartaoCredito(contaCorrente, 5000));

            double taxaEsperada = 12.90 + 19.90 + 29.90;
            assertEquals(taxaEsperada, pacotePremium.getTaxaMensal(), 0.001);
        }

        @Test
        @DisplayName("Descrição deve conter todos os serviços empilhados")
        void deveConterTodosServicosNaDescricao() {
            Conta pacoteCompleto = new SeguroVida(
                    new ChequeEspecial(
                            new CartaoCredito(contaCorrente, 10000), 3000.0));

            String descricao = pacoteCompleto.getDescricao();
            assertTrue(descricao.contains("Conta Corrente"));
            assertTrue(descricao.contains("Cartão de Crédito"));
            assertTrue(descricao.contains("Cheque Especial"));
            assertTrue(descricao.contains("Seguro de Vida"));
        }

        @Test
        @DisplayName("Pacote completo (3 decorators): taxa total correta")
        void deveSomarTaxasDeTresDecorators() {
            Conta pacoteCompleto = new SeguroVida(
                    new ChequeEspecial(
                            new CartaoCredito(contaCorrente, 10000), 3000.0));

            double taxaEsperada = 12.90 + 19.90 + 9.90 + 29.90;
            assertEquals(taxaEsperada, pacoteCompleto.getTaxaMensal(), 0.001);
        }

        @Test
        @DisplayName("Poupança + SeguroVida + Cartão: deve somar corretamente (base zero)")
        void poupancaComDoisDecoratorsDeveSomarCorretamente() {
            Conta poupancaEnriquecida = new CartaoCredito(
                    new SeguroVida(contaPoupanca), 3000);

            double taxaEsperada = 0.0 + 29.90 + 19.90;
            assertEquals(taxaEsperada, poupancaEnriquecida.getTaxaMensal(), 0.001);
        }

        @Test
        @DisplayName("Dois decorators iguais (double SeguroVida) devem acumular taxa duas vezes")
        void doisSeguroVidaDevemDobrarTaxa() {
            // Raro na prática, mas válido no padrão — testa que não há estado compartilhado
            Conta contaDuploSeguro = new SeguroVida(new SeguroVida(contaCorrente));
            double taxaEsperada = 12.90 + 29.90 + 29.90;
            assertEquals(taxaEsperada, contaDuploSeguro.getTaxaMensal(), 0.001);
        }

        @Test
        @DisplayName("Decorators devem ser independentes entre si (sem efeito colateral)")
        void decoratorsDevemSerIndependentes() {
            Conta contaA = new SeguroVida(new ContaCorrente("A"));
            Conta contaB = new CartaoCredito(new ContaCorrente("B"), 5000);

            // Modificar contaB não deve alterar contaA
            assertEquals(12.90 + 29.90, contaA.getTaxaMensal(), 0.001);
            assertEquals(12.90 + 19.90, contaB.getTaxaMensal(), 0.001);
        }
    }
}
