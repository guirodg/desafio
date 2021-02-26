package com.desafio.dto.reqoperacao;

import com.desafio.model.Conta;
import lombok.Data;

@Data
public class OperacaoSaqueDto {
    private double saque;
    private Conta contaOrigem;
}
