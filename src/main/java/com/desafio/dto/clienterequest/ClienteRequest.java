package com.desafio.dto.clienterequest;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class ClienteRequest {
    @NotBlank(message = "Não pode ser nulo ou vazio")
    private String nome;
    @NotBlank(message = "CPF/CNPJ não pode ser nulo!")
    private String cpfCnpj;
    @Pattern(regexp = "^((\\(\\d{2}\\)))\\d{4,5}-\\d{4}$", message = "Deve corresponder (11)90000-0000 ou (11)0000-0000")
    @NotBlank(message = "Tel não pode ser nulo!")
    private String telefone;
    @NotBlank(message = "Endereço não pode ser nulo!")
    private String endereco;
}
