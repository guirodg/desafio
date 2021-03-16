package com.desafio.validation;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ValidationException {
    private String titulo;
    private int status;
    private LocalDateTime timestamp;
    private String campo;
    private String message;
}
