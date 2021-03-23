package com.desafio.dto.contaresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContaResponseDesconto {
    private double saldo;
    private String status;
}
