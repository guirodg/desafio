package com.desafio.controllers;

import com.desafio.dto.reqcliente.ClientePostDto;
import com.desafio.dto.reqcliente.ClientePutDto;
import com.desafio.model.Cliente;
import com.desafio.service.ClienteService;
import com.desafio.util.ClienteCriado;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;
    @Mock
    private ClienteService clienteServiceMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(clienteServiceMock.salvar(ArgumentMatchers.any(ClientePostDto.class)))
                .thenReturn(ClienteCriado.clienteValido());

        BDDMockito.when(clienteServiceMock.atualizar(ArgumentMatchers.any(ClientePutDto.class)))
                .thenReturn(ClienteCriado.clienteValido());

        BDDMockito.doNothing().when(clienteServiceMock).deletar(ArgumentMatchers.anyLong());
    }

    @Test
    void criarCliente_ComSucesso() {
        Cliente cliente = clienteController.salvar(ClienteCriado.clienteValidoPost()).getBody();

        Assertions.assertThat(cliente).isNotNull().isEqualTo(ClienteCriado.clienteValido());
    }

    @Test
    void atualiarCliente_ComSucesso() {
        Assertions.assertThatCode(() -> clienteController.atualizar(ClienteCriado.clienteValidoPut()).getBody())
                .doesNotThrowAnyException();

    }

    @Test
    void deletarCliente_ComSucesso() {
        Assertions.assertThatCode(() -> clienteController.deletar(1L))
                .doesNotThrowAnyException();

    }
}