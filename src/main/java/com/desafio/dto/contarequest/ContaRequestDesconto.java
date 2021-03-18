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
    private Long id;
    @NotEmpty(message = "Saldo não pode ser nulo ou vazio")
    private double saldo;
}