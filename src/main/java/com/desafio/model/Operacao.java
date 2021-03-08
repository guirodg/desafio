package com.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

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
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Conta contaOrigem;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Conta contaDestino;
}
