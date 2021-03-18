package com.desafio.validation;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ValidationRunTime {
    private String titulo;
    private int status;
    private LocalDateTime timestamp;
    private String message;

}
