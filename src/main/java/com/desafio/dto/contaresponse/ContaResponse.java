package com.desafio.dto.contaresponse;

import lombok.Data;

@Data
public class ContaResponse {
    private int numeroConta;
    private String tipoConta;
    private int digitoVerificador;
    private double saldo;
    private String cpfCliente;
    private String status;
}
