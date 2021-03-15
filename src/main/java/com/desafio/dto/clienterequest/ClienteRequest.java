package com.desafio.dto.clienterequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteRequest {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
}
