package com.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numeroConta;
    private String tipoConta;
    private int digitoVerificador;
    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

}
