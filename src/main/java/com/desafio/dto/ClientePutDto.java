package com.desafio.dto;

import lombok.Data;

@Data
public class ClientePutDto {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
}
