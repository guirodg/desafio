package com.desafio.dto.operacaorequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperacaoRequest {
    private double valor;
    private String tipoOperacao;
    private int numeroContaOrigem;
    private int agenciaOrigem;
    private int numeroContaDestino;
    private int agenciaDestino;
}
