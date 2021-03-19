package com.desafio.dto.operacaoresponse;

import lombok.Data;

@Data
public class OperacaoResponse {
    private double valor;
    private String tipoOperacao;
    private int numeroContaOrigem;
    private int numeroContaDestino;
    private String status;
}
