package com.desafio.externo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ControleContaExterno {
    private String cpfCliente;
    private int numeroConta;
    private int agencia;
    private int limeteSaque;
    private String tipoConta;
}
