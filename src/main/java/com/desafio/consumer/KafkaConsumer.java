package com.desafio.consumer;

import com.desafio.dto.contarequest.ContaRequestDesconto;
import com.desafio.service.ContaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaConsumer {
    private final ContaService contaService;

    @KafkaListener(topics = "TOPIC_CONTA", groupId = "GROUP_CONTA")
    public void consume(String message) throws JsonProcessingException {
        System.out.println("Seu limite de saque acabou - Descontar = " + message);
        ObjectMapper objectMapper = new ObjectMapper();
        ContaRequestDesconto converteMenssagem = objectMapper.readValue(message, ContaRequestDesconto.class);
        ContaRequestDesconto contaRequestDesconto = ContaRequestDesconto.builder().id(converteMenssagem.getId())
                .saldo(converteMenssagem.getSaldo()).build();
        contaService.atualizarSaldo(contaRequestDesconto);
    }
}
