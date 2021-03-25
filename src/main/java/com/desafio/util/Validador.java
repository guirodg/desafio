package com.desafio.util;

import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.desafio.erros.ExecaoMensagem;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Validador {

    public static void validaCpf(String cpfCnpj) {
        if (cpfCnpj.length() == 14) {
            try {
                CPFValidator cpfValidator = new CPFValidator();
                cpfValidator.assertValid(cpfCnpj);
                CPFFormatter formata = new CPFFormatter();
                formata.unformat(cpfCnpj);
            } catch (InvalidStateException e) {
                throw new ExecaoMensagem("CPF Inválido - 000.000.000-00");
            }
        } else if (cpfCnpj.length() == 18) {
            try {
                CNPJValidator cnpjValidator = new CNPJValidator();
                cnpjValidator.assertValid(cpfCnpj);
                CNPJFormatter cnpjFormatter = new CNPJFormatter();
                cnpjFormatter.unformat(cpfCnpj);
            } catch (InvalidStateException e) {
                throw new ExecaoMensagem("CNPJ Inválido - 00.000.000/0000-00");
            }
        } else
            throw new ExecaoMensagem("Formato para CPF = 000.000.000-00 CNPJ = 00.000.000/0000-00");

    }
}
