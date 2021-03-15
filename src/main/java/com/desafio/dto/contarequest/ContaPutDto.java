package com.desafio.dto.contarequest;

import com.desafio.model.Cliente;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContaPutDto {
    private Long id;
    private int numeroConta;
    private String tipoConta;
    private int digitoVerificador;
    private Cliente cliente;
}
