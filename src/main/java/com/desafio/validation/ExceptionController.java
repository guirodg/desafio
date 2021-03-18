package com.desafio.validation;

import ch.qos.logback.classic.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationException> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationException.builder()
                        .timestamp(LocalDateTime.now())
                        .status(422)
                        .titulo("Bad Request")
                        .message(fieldsMessage)
                        .campo(fields)
                        .build(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ValidationRunTime> handlerRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(
                ValidationRunTime.builder()
                        .timestamp(LocalDateTime.now())
                        .status(403)
                        .titulo("Bad Request")
                        .message("requisição invalida")
                        .build(), HttpStatus.FORBIDDEN);
    }
}
