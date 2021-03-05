package com.desafio.service;

import com.desafio.dto.reqconta.ContaPostDto;
import com.desafio.dto.reqconta.ContaPutDto;
import com.desafio.dto.reqconta.ContaPutDtoDesconto;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.model.Cliente;
import com.desafio.model.Conta;
import com.desafio.repository.ClienteRepository;
import com.desafio.repository.ContaRepository;
import com.desafio.util.UtilConta;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepositoryMock;

    @Mock
    private ClienteRepository clienteRepositoryMock;


    @Test
    void salvarConta_ComSucesso() {
        Conta contaSalvar = this.criarConta();
        ContaPostDto contaPostDto = this.criarContaPostDto();
        Cliente cliente = new Cliente(1l, "45", "nome", "tel", "end");

        when(contaRepositoryMock.save(any(Conta.class)))
                .thenReturn(contaSalvar);
        when(clienteRepositoryMock.findById(1l)).thenReturn(Optional.of(cliente));

        Conta contaa = contaService.salvar(contaPostDto);
        Assertions.assertThat(contaa).isNotNull();
    }

    @Test
    void atualizarConta_ComSucesso() {
        Conta contaSalvar = this.criarConta();
        ContaPutDto contaPutDto = this.criarContaPutDto();

        when(contaRepositoryMock.save(any(Conta.class)))
                .thenReturn(contaSalvar);
        when(contaRepositoryMock.findById(1L))
                .thenReturn(Optional.of(contaSalvar));

        Conta conta = contaService.atualizar(contaPutDto);
        Assertions.assertThat(conta).isNotNull();
    }

    @Test
    void atualizarContaSaldo_ComSucesso() {
        Conta contaSalvar = this.criarConta();
        ContaPutDtoDesconto contaPutDto = this.criarContaPutDtoDesconto();

        when(contaRepositoryMock.save(any(Conta.class)))
                .thenReturn(contaSalvar);
        when(contaRepositoryMock.findById(1L))
                .thenReturn(Optional.of(contaSalvar));

        Conta conta = contaService.atualizarSaldo(contaPutDto);
        Assertions.assertThat(conta).isNotNull();
    }

    @Test
    void buscarPorId() {
        Conta contaSalvar = this.criarConta();

        when(contaRepositoryMock.findById(1L))
                .thenReturn(Optional.of(contaSalvar));

        Conta conta = contaService.encontreIdOuErro(1L);
        Assertions.assertThat(conta).isNotNull();
    }

    @Test
    void deletarPorId() {
        Conta contaSalvar = this.criarConta();

        when(contaRepositoryMock.findById(1L))
                .thenReturn(Optional.of(contaSalvar));

        contaService.deletar(1L);
        Assertions.assertThat(contaService).isNotNull();
    }

    @Test
    void salvarConta_Vazio() {
        ContaPostDto clientePostDtoSalvar = UtilConta.criarContaPostDtoVazio();

        Assertions.assertThatThrownBy(() -> this.contaService.salvar(clientePostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Preencha todos os campos");
    }


    @Test
    void atualizarConta_Vazio() {
        ContaPutDto clientePostDtoSalvar = UtilConta.criarContaPutDtoVazio();

        Assertions.assertThatThrownBy(() -> this.contaService.atualizar(clientePostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Preencha todos os campos");
    }

    @Test
    void salvarConta_SemIdCliente() {
        Conta contaSalvar = this.criarConta();
        ContaPostDto contaPostDto = this.criarContaPostDto();

        when(contaRepositoryMock.save(any(Conta.class)))
                .thenReturn(contaSalvar);

        Assertions.assertThatThrownBy(() -> this.contaService.salvar(contaPostDto))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("ID do cliente informado não existe");
    }


    private Conta criarConta() {
        return Conta.builder()
                .id(1l)
                .numeroConta(4564566)
                .tipoConta("pessoa fisica")
                .digitoVerificador(454564)
                .saldo(0)
                .cliente(Cliente.builder().id(1l).build())
                .build();
    }

    private ContaPostDto criarContaPostDto() {
        return ContaPostDto.builder()
                .numeroConta(4564566)
                .tipoConta("pessoa fisica")
                .cliente(Cliente.builder().id(1l).build())
                .digitoVerificador(454564)
                .saldo(0)
                .build();
    }

    private ContaPutDto criarContaPutDto() {
        return ContaPutDto.builder()
                .id(1l)
                .numeroConta(4564566)
                .tipoConta("Test")
                .cliente(Cliente.builder().id(1l).build())
                .digitoVerificador(454564)
                .build();
    }

    private ContaPutDtoDesconto criarContaPutDtoDesconto() {
        return ContaPutDtoDesconto.builder()
                .id(1l)
                .saldo(10)
                .build();
    }
}