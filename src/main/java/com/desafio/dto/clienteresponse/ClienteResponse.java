package com.desafio.dto.clienteresponse;

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
public class ClienteResponse {
    private String mensagem;
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String endereco;
}
