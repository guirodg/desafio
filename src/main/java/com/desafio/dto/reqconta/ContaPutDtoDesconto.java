package com.desafio.dto.reqconta;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContaPutDtoDesconto {
    private Long id;
    private double saldo;
}
