package com.desafio.dto.clienterequest;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ClienteRequest {
    private Long id;
    @NotNull(message = "Nome não pode ser nulo!")
    private String nome;
    @NotNull(message = "CPF/CNPJ não pode ser nulo!")
    private String cpfCnpj;
    @NotNull(message = "Tel não pode ser nulo!")
    private String telefone;
    @NotNull(message = "Endereço não pode ser nulo!")
    private String endereco;
}
