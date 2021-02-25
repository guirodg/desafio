package com.desafio.dto.reqcliente;

import lombok.Data;

@Data
public class ClientePutDto {
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
}
