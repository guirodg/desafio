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
    private int numeroConta;
    private String tipoConta;
    private int agencia;
    private double saldo;
    private String cpfCliente;
}
