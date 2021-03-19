package com.desafio.externo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ControleContaExterno {
    private int limeteSaque;
    private int numeroConta;
    private String tipoConta;
}
