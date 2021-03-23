package com.desafio.dto.contarequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaRequestDesconto {
    @NotNull(message = "Numero Conta não pode ser nulo ou vazio")
    private int numeroConta;
    @NotNull(message = "Agencia não pode ser nulo ou vazio")
    private int agencia;
    @NotNull(message = "Saldo não pode ser nulo ou vazio")
    private double saldo;
}
