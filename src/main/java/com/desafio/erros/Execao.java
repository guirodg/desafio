package com.desafio.erros;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Execao extends RuntimeException {
    public Execao(String message) {
        super(message);
    }
}
