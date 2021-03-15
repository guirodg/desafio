package com.desafio.dto.clienterequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteRequestPut {
    private Long id;
    private String nome;
    private String telefone;
    private String endereco;
}
