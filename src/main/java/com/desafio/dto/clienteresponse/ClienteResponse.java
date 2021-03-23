package com.desafio.dto.clienteresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String endereco;
    private String status;
}
