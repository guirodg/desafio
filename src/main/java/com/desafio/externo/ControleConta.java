package com.desafio.externo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ControleConta {
    private int limeteSaque;
    private Long idConta;
}
