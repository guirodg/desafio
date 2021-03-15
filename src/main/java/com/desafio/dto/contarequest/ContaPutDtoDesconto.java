package com.desafio.dto.contarequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaPutDtoDesconto {
    private Long id;
    private double saldo;
}
