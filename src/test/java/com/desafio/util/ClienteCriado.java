package com.desafio.util;

import com.desafio.dto.reqcliente.ClientePostDto;
import com.desafio.dto.reqcliente.ClientePutDto;
import com.desafio.model.Cliente;

public class ClienteCriado {

    public static Cliente clienteValido() {
        return Cliente.builder()
                .id(1L)
                .nome("Gui")
                .endereco("Rua Test  4")
                .telefone("(11)4578-8956")
                .cpf("65454654646")
                .build();
    }

    public static ClientePostDto clienteValidoPost() {
        return ClientePostDto.builder()
                .nome(clienteValido().getNome())
                .endereco("Rua Test  4")
                .telefone("(11)4578-8956")
                .cpf("65454654646")
                .build();
    }

    public static ClientePutDto clienteValidoPut() {
        return ClientePutDto.builder()
                .id(clienteValido().getId())
                .nome(clienteValido().getNome())
                .endereco("Rua Test  4")
                .telefone("(11)4578-8956")
                .cpf("65454654646")
                .build();
    }
}
