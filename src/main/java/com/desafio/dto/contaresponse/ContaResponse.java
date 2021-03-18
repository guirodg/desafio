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
    private int numeroConta;
    private String tipoConta;
    private int digitoVerificador;
    private double saldo;
    private String cpfCliente;
    private String status;
}
