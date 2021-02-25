package com.desafio.repository;

import com.desafio.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    public Cliente findByCpf(String cpf);
}
