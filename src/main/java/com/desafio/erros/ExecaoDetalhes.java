package com.desafio.erros;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExecaoDetalhes {
    private String titulo;
    private int status;
    private String detalhe;
    private String message;
    private LocalDateTime timestamp;
}
