package com.desafio.erros;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExecaoAtributos {
    private String titulo;
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
