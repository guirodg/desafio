package com.desafio.util;

import com.desafio.dto.reqcliente.ClientePostDto;
import com.desafio.dto.reqcliente.ClientePutDto;

public class UtilCliente {

    public static ClientePostDto criarClientePostDtoVazio() {
        return ClientePostDto.builder()
                .cpf("454455646456")
                .nome("")
                .telefone("(11)4558-5666")
                .endereco("Rua Test da silva")
                .build();
    }

    public static ClientePutDto criarClientePutDtoVazio() {
        return ClientePutDto.builder()
                .id(1l)
                .cpf("454455646456")
                .nome("")
                .telefone("(11)4558-5666")
                .endereco("Rua Test da silva")
                .build();
    }
}
