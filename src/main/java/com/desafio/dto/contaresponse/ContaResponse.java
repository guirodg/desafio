package com.desafio.dto.contaresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaResponse {
    private Long id;
    private String cpfCliente;
    private int agencia;
    private int numeroConta;
    private String tipoConta;
    private double saldo;
    private String status;
}
