package com.desafio.controllers;

import com.desafio.dto.reqconta.ContaPostDto;
import com.desafio.model.Conta;
import com.desafio.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contas")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> save(@RequestBody ContaPostDto contaPostDto) {
        return new ResponseEntity<>(contaService.save(contaPostDto), HttpStatus.CREATED);
    }
}
