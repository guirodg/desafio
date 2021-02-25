package com.desafio.erros;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErroController {
    @ExceptionHandler(ExecaoMenssagem.class)
    public ResponseEntity<ExecaoAtributos> requestExecao(ExecaoMenssagem e) {
        return new ResponseEntity<>(
                ExecaoAtributos.builder()
                        .timestamp(LocalDateTime.now())
                        .status(400)
                        .titulo("Bad Request")
                        .message(e.getMessage())
                        .build(), HttpStatus.BAD_REQUEST);
    }

}
