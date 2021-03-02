package com.desafio.dto.reqcliente;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientePostDto {
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
}
