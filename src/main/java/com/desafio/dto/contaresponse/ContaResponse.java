package com.desafio.dto.contaresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContaResponse {
    private String mensagem;
    private String cpfCliente;
    private int agencia;
    private int numeroConta;
    private String tipoConta;
    private double saldo;
}
