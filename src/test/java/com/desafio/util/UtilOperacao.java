package com.desafio.util;

import com.desafio.dto.reqoperacao.OperacaoPostDto;
import com.desafio.model.Conta;

public class UtilOperacao {

    public static OperacaoPostDto criarOperacaoPostDtoSemValor() {
        return OperacaoPostDto.builder()
                .tipoOperacao("Deposito")
                .contaOrigem(Conta.builder().id(1l).build())
                .contaDestino(Conta.builder().id(1l).build())
                .build();
    }

    public static OperacaoPostDto criarOperacaoPostDtoSemTipoOperacaoVazio() {
        return OperacaoPostDto.builder()
                .tipoOperacao("")
                .contaOrigem(Conta.builder().id(1l).build())
                .contaDestino(Conta.builder().id(1l).build())
                .build();
    }

    public static OperacaoPostDto criarOperacaoPostDtoSemIdOrigem() {
        return OperacaoPostDto.builder()
                .tipoOperacao("Deposito")
                .valor(1)
                .contaOrigem(Conta.builder().id(-1l).build())
                .contaDestino(Conta.builder().id(1l).build())
                .build();
    }

    public static OperacaoPostDto criarOperacaoPostDtoSemIdDestino() {
        return OperacaoPostDto.builder()
                .tipoOperacao("transferencia")
                .valor(1)
                .contaOrigem(Conta.builder().id(1l).build())
                .contaDestino(Conta.builder().id(-1l).build())
                .build();
    }

    public static OperacaoPostDto criarOperacaoPostDtoSemIdNoBanco() {
        return OperacaoPostDto.builder()
                .tipoOperacao("saque")
                .valor(1)
                .contaOrigem(Conta.builder().id(10l).build())
                .contaDestino(Conta.builder().id(-1l).build())
                .build();
    }

    public static OperacaoPostDto criarOperacaoPostDtoSemIdNoBancoDeposito() {
        return OperacaoPostDto.builder()
                .tipoOperacao("deposito")
                .valor(1)
                .contaOrigem(Conta.builder().id(10l).build())
                .contaDestino(Conta.builder().id(-1l).build())
                .build();
    }

    public static OperacaoPostDto criarOperacaoPostDtoSemOperacao() {
        return OperacaoPostDto.builder()
                .tipoOperacao("sasdas")
                .valor(1)
                .contaOrigem(Conta.builder().id(10l).build())
                .contaDestino(Conta.builder().id(-1l).build())
                .build();
    }
}
