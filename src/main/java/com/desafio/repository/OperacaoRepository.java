package com.desafio.repository;

import com.desafio.model.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperacaoRepository extends JpaRepository<Operacao, Long> {

    List<Operacao> findAllByNumeroContaOrigem(int numero);
}
