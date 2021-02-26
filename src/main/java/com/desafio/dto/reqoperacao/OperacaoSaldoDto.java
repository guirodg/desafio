package com.desafio.dto.reqoperacao;

import com.desafio.model.Conta;
import lombok.Data;

@Data
public class OperacaoSaldoDto {
    private double saldo;
    private Conta contaOrigem;
}
