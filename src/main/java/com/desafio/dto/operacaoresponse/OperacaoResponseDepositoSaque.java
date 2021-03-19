package com.desafio.dto.operacaoresponse;

import lombok.Data;

@Data
public class OperacaoResponseDepositoSaque {
    private double valor;
    private String tipoOperacao;
    private int numeroContaOrigem;
    private int agenciaOrigem;
    private String status;
}
