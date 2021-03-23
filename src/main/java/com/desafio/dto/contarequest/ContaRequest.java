package com.desafio.dto.contarequest;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ContaRequest {
    @Range(min = 1000, max = 10000, message = "Agencia deve ser 4 digitos")
    private int agencia;
    @Range(min = 10000000, max = 100000000, message = "Numero da conta deve ser 8 digitos")
    private int numeroConta;
    @NotBlank(message = "Tipo da conta não pode ser nulo")
    private String tipoConta;
    @NotBlank(message = "CPF da conta não pode ser nulo")
    private String cpfCliente;
}
