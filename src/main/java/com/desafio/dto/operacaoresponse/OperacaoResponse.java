package com.desafio.dto.operacaoresponse;

import lombok.Data;

@Data
public class OperacaoResponse {
    private double valor;
    private String tipoOperacao;
    private int numeroContaOrigem;
    private int agenciaOrigem;
    private int numeroContaDestino;
    private int agenciaDestino;
    private String data;
    private String status;
}
