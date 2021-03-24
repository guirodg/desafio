package com.desafio.erros;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErroController {

    @ExceptionHandler(ExecaoMensagem.class)
    public ResponseEntity<ExecaoAtributos> requestExecao(ExecaoMensagem e) {
        return new ResponseEntity<>(
                ExecaoAtributos.builder()
                        .timestamp(LocalDateTime.now())
                        .status(422)
                        .titulo("Unprocessable Entity")
                        .message(e.getMessage())
                        .build(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
