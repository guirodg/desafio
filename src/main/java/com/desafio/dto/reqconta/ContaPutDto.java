package com.desafio.dto.reqconta;

import com.desafio.model.Cliente;
import lombok.Data;

@Data
public class ContaPutDto {
    private Long id;
    private int numeroConta;
    private String tipoConta;
    private int digitoVerificador;
    private Cliente cliente;
}
