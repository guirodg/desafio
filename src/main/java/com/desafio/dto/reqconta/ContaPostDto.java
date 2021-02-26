package com.desafio.dto.reqconta;

import com.desafio.model.Cliente;
import lombok.Data;

@Data
public class ContaPostDto {
    private int numeroConta;
    private String tipoConta;
    private int digitoVerificador;
    private double saldo;
    private Cliente cliente;
}
