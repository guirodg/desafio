package com.desafio.util;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.desafio.erros.ExecaoMensagem;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Validador {

    public static void validaCpf(String cpfCnpj) {
        if (cpfCnpj.length() == 11) {
            try {
                CPFValidator cpfValidator = new CPFValidator();
                cpfValidator.assertValid(cpfCnpj);
            } catch (InvalidStateException e) {
                throw new ExecaoMensagem("CPF ou CNPJ Inválido");
            }
        }
        if (cpfCnpj.length() == 14) {
            try {
                CNPJValidator cnpjValidator = new CNPJValidator();
                cnpjValidator.assertValid(cpfCnpj);
            } catch (InvalidStateException e) {
                throw new ExecaoMensagem("CNPJ ou CPF Inválido");
            }
        } else if (cpfCnpj.length() != 14 && cpfCnpj.length() != 11) {
            throw new ExecaoMensagem("Digite 11 caracter para CPF e 14 caracter para CNPJ");
        }
    }
}
