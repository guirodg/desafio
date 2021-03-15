package com.desafio.dto.clienteresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponsePut {
    private Long id;
    private String nome;
    private String telefone;
    private String endereco;
    private String status;
}