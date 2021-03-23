package com.desafio.dto.operacaorequest;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class OperacaoRequest {
    @NotNull(message = "Não pode ser nulo ou vazio")
    private double valor;
    @NotBlank(message = "Não pode ser nulo ou vazio")
    private String tipoOperacao;
    @NotNull(message = "Não pode ser nulo ou vazio")
    private int numeroContaOrigem;
    @NotNull(message = "Não pode ser nulo ou vazio")
    private int agenciaOrigem;
    @NotNull(message = "Não pode ser nulo ou vazio")
    private int numeroContaDestino;
    @NotNull(message = "Não pode ser nulo ou vazio")
    private int agenciaDestino;
    private String data;
}
