package com.desafio.service;

import com.desafio.dto.reqoperacao.OperacaoPostDto;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.model.Cliente;
import com.desafio.model.Conta;
import com.desafio.model.Operacao;
import com.desafio.repository.ContaRepository;
import com.desafio.repository.OperacaoRepository;
import com.desafio.util.UtilOperacao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OperacaoServiceTest {

    @InjectMocks
    private OperacaoService operacaoService;

    @Mock
    private OperacaoRepository operacaoRepositoryMock;

    @Mock
    private ContaRepository contaRepositoryMock;


    @Test
    void listarExtrato_ComSucesso() {
        Operacao operacaoSalvar = this.criarOperacao();

        when(operacaoRepositoryMock.findAllByContaOrigemId(1l))
                .thenReturn(List.of(operacaoSalvar));

        List<Operacao> operacao = operacaoService.listarExtrato(1l);

        Assertions.assertThat(operacao).isNotNull();
    }

    @Test
    void salvarDeposito_ComSucesso() {
        Operacao operacaoSalvar = this.criarOperacao();
        Conta contaSalvar = this.criarConta();

        OperacaoPostDto operacaoPostDtoSalvar = this.criarOperacaoPostDto();

        when(operacaoRepositoryMock.save(any(Operacao.class)))
                .thenReturn(operacaoSalvar);
        when(contaRepositoryMock.findById(1l))
                .thenReturn(Optional.of(contaSalvar));

        Operacao operacao = operacaoService.salvarDeposito(operacaoPostDtoSalvar);

        Assertions.assertThat(operacao).isNotNull();
    }

    @Test
    void salvarSaque_ComSucesso() {
        Operacao operacaoSalvar = this.criarOperacao();
        Conta contaSalvar = this.criarConta();

        OperacaoPostDto operacaoPostDtoSalvar = this.criarOperacaoPostDto();

        when(operacaoRepositoryMock.save(any(Operacao.class)))
                .thenReturn(operacaoSalvar);
        when(contaRepositoryMock.findById(1l))
                .thenReturn(Optional.of(contaSalvar));

        Operacao operacao = operacaoService.salvarSaque(operacaoPostDtoSalvar);

        Assertions.assertThat(operacao).isNotNull();
    }

    @Test
    void salvarTransferencia_ComSucesso() {
        Operacao operacaoSalvar = this.criarOperacao();
        Conta contaSalvar = this.criarConta();

        OperacaoPostDto operacaoPostDtoSalvar = this.criarOperacaoPostDto();

        when(operacaoRepositoryMock.save(any(Operacao.class)))
                .thenReturn(operacaoSalvar);
        when(contaRepositoryMock.findById(1l))
                .thenReturn(Optional.of(contaSalvar));

        Operacao operacao = operacaoService.salvarTransferencia(operacaoPostDtoSalvar);

        Assertions.assertThat(operacao).isNotNull();
    }

    // Validaçoes Post Deposito
    @Test
    void salvarDeposito_SemValor() {
        OperacaoPostDto operacaoPostDtoSalvar = UtilOperacao.criarOperacaoPostDtoSemValor();

        Assertions.assertThatThrownBy(() -> this.operacaoService.salvarDeposito(operacaoPostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Digite 'valor' para realizar deposito");
    }

    @Test
    void salvarDeposito_SemTipoOperacao() {
        OperacaoPostDto operacaoPostDtoSalvar = UtilOperacao.criarOperacaoPostDtoSemTipoOperacao();

        Assertions.assertThatThrownBy(() -> this.operacaoService.salvarDeposito(operacaoPostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Digite tipo da Operacao");
    }

    @Test
    void salvarDeposito_SemIdOrigem() {
        OperacaoPostDto operacaoPostDtoSalvar = UtilOperacao.criarOperacaoPostDtoSemIdOrigem();

        Assertions.assertThatThrownBy(() -> this.operacaoService.salvarDeposito(operacaoPostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Digite id da conta que deseja relizar operação");
    }

    @Test
    void salvarDeposito_SemIdConta() {
        OperacaoPostDto operacaoPostDtoSalvar = UtilOperacao.criarOperacaoPostDtoSemIdOrigem();

        Assertions.assertThatThrownBy(() -> this.operacaoService.salvarDeposito(operacaoPostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Digite id da conta que deseja relizar operação");
    }

    @Test
    void salvarDeposito_SemIdContaNoBanco() {
        OperacaoPostDto operacaoPostDtoSalvar = UtilOperacao.criarOperacaoPostDtoSemIdNoBanco();

        Assertions.assertThatThrownBy(() -> this.operacaoService.salvarDeposito(operacaoPostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Id da contaOrigem não existe");
    }

    // Validaçoes Post Saque

    @Test
    void salvarSaque_SemValor() {
        OperacaoPostDto operacaoPostDtoSalvar = UtilOperacao.criarOperacaoPostDtoSemValor();

        Assertions.assertThatThrownBy(() -> this.operacaoService.salvarSaque(operacaoPostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Digite o valor do saque");
    }

    @Test
    void salvarSaque_SemIdOrigem() {
        OperacaoPostDto operacaoPostDtoSalvar = UtilOperacao.criarOperacaoPostDtoSemIdOrigem();

        Assertions.assertThatThrownBy(() -> this.operacaoService.salvarSaque(operacaoPostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Digite id da conta que deseja relizar operação");
    }

    @Test
    void salvarSaque_SemIdContaNoBanco() {
        OperacaoPostDto operacaoPostDtoSalvar = UtilOperacao.criarOperacaoPostDtoSemIdNoBanco();

        Assertions.assertThatThrownBy(() -> this.operacaoService.salvarSaque(operacaoPostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Id da contaOrigem não existe");
    }

    // Validaçoes Post Transferencia

    @Test
    void salvarTransferencia_SemValor() {
        OperacaoPostDto operacaoPostDtoSalvar = UtilOperacao.criarOperacaoPostDtoSemValor();

        Assertions.assertThatThrownBy(() -> this.operacaoService.salvarTransferencia(operacaoPostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Digite o valor para a transferencia");
    }

    @Test
    void salvarTransferencia_SemIdContaOrigem() {
        OperacaoPostDto operacaoPostDtoSalvar = UtilOperacao.criarOperacaoPostDtoSemIdOrigem();

        Assertions.assertThatThrownBy(() -> this.operacaoService.salvarTransferencia(operacaoPostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Digite id da conta origem");
    }

    @Test
    void salvarTransferencia_SemIdContaDestino() {
        OperacaoPostDto operacaoPostDtoSalvar = UtilOperacao.criarOperacaoPostDtoSemIdDestino();

        Assertions.assertThatThrownBy(() -> this.operacaoService.salvarTransferencia(operacaoPostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Digite id da conta destino");
    }

    private Operacao criarOperacao() {
        return Operacao.builder()
                .id(1l)
                .valor(100)
                .tipoOperacao("Deposito")
                .contaOrigem(Conta.builder().id(1l).build())
                .contaDestino(Conta.builder().id(1l).build())
                .build();
    }

    private Conta criarConta() {
        return Conta.builder()
                .id(1l)
                .numeroConta(4564566)
                .tipoConta("Test")
                .digitoVerificador(454564)
                .saldo(10)
                .cliente(Cliente.builder().id(1l).build())
                .limiteSaque(0)
                .build();
    }

    private OperacaoPostDto criarOperacaoPostDto() {
        return OperacaoPostDto.builder()
                .valor(1)
                .tipoOperacao("Deposito")
                .contaOrigem(Conta.builder().id(1l).build())
                .contaDestino(Conta.builder().id(1l).build())
                .build();
    }

}