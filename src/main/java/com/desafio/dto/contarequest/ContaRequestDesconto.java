package com.desafio.dto.contarequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaRequestDesconto {
    private int numeroConta;
    private int agencia;
    @NotEmpty(message = "Saldo não pode ser nulo ou vazio")
    private double saldo;
}
