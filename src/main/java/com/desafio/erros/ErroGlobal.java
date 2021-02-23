package com.desafio.erros;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErroGlobal {
    @ExceptionHandler(Execao.class)
    public ResponseEntity<ExecaoDetalhes> requestExecao(Execao e) {
        return new ResponseEntity<>(
                ExecaoDetalhes.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .titulo("Bad Request")
                        .detalhe(e.getMessage())
                        .message(e.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }

}
