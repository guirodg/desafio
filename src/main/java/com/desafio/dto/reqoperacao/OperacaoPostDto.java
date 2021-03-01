package com.desafio.dto.reqoperacao;

import com.desafio.model.Conta;
import lombok.Data;

@Data
public class OperacaoPostDto {
    private double valor;
    private String operacao;
    private Conta contaOrigem;
    private Conta contaDestino;
}
