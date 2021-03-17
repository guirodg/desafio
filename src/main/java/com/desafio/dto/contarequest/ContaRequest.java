package com.desafio.dto.contarequest;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ContaRequest {
    @NotNull(message = "Numero da conta não pode ser nulo")
    private int numeroConta;
    @NotNull(message = "Tipo da conta não pode ser nulo")
    private String tipoConta;
//    @NotNull(message = "Digito da conta não pode ser nulo")
//    private int digitoVerificador;
    @NotNull(message = "CPF da conta não pode ser nulo")
    private String cpfCliente;
}
