package com.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Operacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double valor;
    private String tipoOperacao;
    @OnDelete(action = OnDeleteAction.CASCADE)
    private int numeroContaOrigem;
    private int agenciaOrigem;
    @OnDelete(action = OnDeleteAction.CASCADE)
    private int numeroContaDestino;
    private int agenciaDestino;
    private String data;
}
