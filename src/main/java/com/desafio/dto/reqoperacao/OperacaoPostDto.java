package com.desafio.dto.reqoperacao;

import com.desafio.model.Conta;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperacaoPostDto {
    private double valor;
    private String tipoOperacao;
    private Conta contaOrigem;
    private Conta contaDestino;
}
