package com.desafio.service;

import com.desafio.dto.reqcliente.ClientePostDto;
import com.desafio.dto.reqcliente.ClientePutDto;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.model.Cliente;
import com.desafio.repository.ClienteRepository;
import com.desafio.util.UtilCliente;
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
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepositoryMock;


    @Test
    void salvarCliente_ComSucesso() {
        Cliente clienteSalvar = this.criarCliente();
        ClientePostDto clientePostDto = this.criarClientePostDto();

        when(clienteRepositoryMock.save(any(Cliente.class)))
                .thenReturn(clienteSalvar);

        Cliente cliente = clienteService.salvar(clientePostDto);
        Assertions.assertThat(cliente).isNotNull();
    }

    @Test
    void atualizarCliente_ComSucesso() {
        Cliente clienteSalvar = this.criarCliente();
        ClientePutDto clientePutDto = this.criarClientePutDto();

        when(clienteRepositoryMock.save(any(Cliente.class)))
                .thenReturn(clienteSalvar);
        when(clienteRepositoryMock.findById(1L))
                .thenReturn(Optional.of(clienteSalvar));

        Cliente cliente = clienteService.atualizar(clientePutDto);
        Assertions.assertThat(cliente).isNotNull();
    }

    @Test
    void buscarPorId() {
        Cliente clienteSalvar = this.criarCliente();

        when(clienteRepositoryMock.findById(1L))
                .thenReturn(Optional.of(clienteSalvar));

        Cliente cliente = clienteService.encontreIdOuErro(1L);
        Assertions.assertThat(cliente).isNotNull();
    }

    @Test
    void deletarPorId() {
        Cliente clienteSalvar = this.criarCliente();

        when(clienteRepositoryMock.findById(1L))
                .thenReturn(Optional.of(clienteSalvar));

        clienteService.deletar(1L);
        Assertions.assertThat(clienteService).isNotNull();
    }

    @Test
    void salvarCliente_Vazio() {
        ClientePostDto clientePostDtoSalvar = UtilCliente.criarClientePostDtoVazio();

        Assertions.assertThatThrownBy(() -> this.clienteService.salvar(clientePostDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Todos os campos não foram preenchidos");
    }

    @Test
    void atualizarCliente_Vazio() {
        ClientePutDto clientePutDtoSalvar = UtilCliente.criarClientePutDtoVazio();

        Assertions.assertThatThrownBy(() -> this.clienteService.atualizar(clientePutDtoSalvar))
                .isInstanceOf(ExecaoMensagem.class)
                .hasMessageContaining("Todos os campos não foram preenchidos");
    }

    private Cliente criarCliente() {
        return Cliente.builder()
                .id(1l)
                .cpf("454455646456")
                .nome("Test")
                .telefone("(11)4558-5666")
                .endereco("Rua Test da silva")
                .build();
    }

    private ClientePostDto criarClientePostDto() {
        return ClientePostDto.builder()
                .cpf("454455646456")
                .nome("Test")
                .telefone("(11)4558-5666")
                .endereco("Rua Test da silva")
                .build();
    }

    private ClientePutDto criarClientePutDto() {
        return ClientePutDto.builder()
                .id(1l)
                .cpf("454455646456")
                .nome("Test")
                .telefone("(11)4558-5666")
                .endereco("Rua Test da silva")
                .build();
    }
}