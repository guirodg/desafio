package com.desafio.dto.clienterequest;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class ClienteRequest {

    @NotBlank(message = "Não pode ser nulo ou vazio")
    @Size(max = 15, message = "Limite maximo de caracter 15")
    private String nome;

    @NotBlank(message = "CPF/CNPJ não pode ser nulo!")
    @Size(min = 11, message = "Formato para CPF = 000.000.000-00 CNPJ = 00.000.000/0000-00")
    private String cpfCnpj;

    @Pattern(regexp = "^((\\(\\d{2}\\)))\\d{4,5}-\\d{4}$", message = "Deve corresponder (11)90000-0000 ou (11)0000-0000")
    @NotBlank(message = "Tel não pode ser nulo!")
    private String telefone;

    @NotBlank(message = "Endereço não pode ser nulo!")
    @Size(max = 20, message = "Limite maximo de caracter 15")
    private String endereco;
}
