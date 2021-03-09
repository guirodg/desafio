package com.desafio.util;

import com.desafio.dto.reqconta.ContaPostDto;
import com.desafio.dto.reqconta.ContaPutDto;
import com.desafio.model.Cliente;

public class UtilConta {

    public static ContaPostDto criarContaPostDtoVazio() {
        return ContaPostDto.builder()
                .numeroConta(123123)
                .tipoConta("")
                .digitoVerificador(454454)
                .saldo(0)
                .cliente(Cliente.builder().id(1l).build())
                .build();
    }

    public static ContaPutDto criarContaPutDtoVazio() {
        return ContaPutDto.builder()
                .numeroConta(123123)
                .tipoConta("")
                .digitoVerificador(454454)
                .cliente(Cliente.builder().id(1l).build())
                .build();
    }
}
