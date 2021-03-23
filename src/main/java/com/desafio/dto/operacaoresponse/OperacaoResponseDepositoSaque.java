package com.desafio.dto.operacaoresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperacaoResponseDepositoSaque {
    private double valor;
    private String tipoOperacao;
    private int numeroContaOrigem;
    private int agenciaOrigem;
    private String status;
}
