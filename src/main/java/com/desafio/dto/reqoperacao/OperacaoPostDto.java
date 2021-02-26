package com.desafio.dto.reqoperacao;

import com.desafio.model.Conta;
import lombok.Data;

@Data
public class OperacaoPostDto {
    private Long id;
    private double saldo;
    private double saque;
    private Conta conta;
}
