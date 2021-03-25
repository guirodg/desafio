package com.desafio.dto.operacaorequest;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class OperacaoRequest {

    @NotNull(message = "Valor não pode ser nulo ou vazio")
    private double valor;

    @NotBlank(message = "Tipo Operacao não pode ser nulo ou vazio")
    private String tipoOperacao;

    @NotNull(message = "Conta Origem não pode ser nulo ou vazio")
    private int numeroContaOrigem;

    @NotNull(message = "Agencia Origem não pode ser nulo ou vazio")
    private int agenciaOrigem;

    @NotNull(message = "Conta Destino não pode ser nulo ou vazio")
    private int numeroContaDestino;

    @NotNull(message = "Agencia Destino não pode ser nulo ou vazio")
    private int agenciaDestino;
    
    private String data;
}
