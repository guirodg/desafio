package com.desafio.dto.contarequest;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ContaRequestPut {
    private Long id;
    @Range(min = 1000, max = 10000, message = "Agencia deve ser 4 digitos")
    private int agencia;
    @NotNull(message = "Numero da conta não pode ser nulo")
    @Range(min = 10000000, max = 100000000, message = "Numero da conta deve ser 8 digitos")
    private int numeroConta;
    @NotNull(message = "Tipo da conta não pode ser nulo")
    private String tipoConta;
}
