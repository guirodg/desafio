package com.desafio.controllers;

import com.desafio.dto.reqoperacao.OperacaoPostDto;
import com.desafio.model.Operacao;
import com.desafio.service.OperacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("operacoes")
@RequiredArgsConstructor
public class OperacaoController {
    private final OperacaoService operacaoService;

    @GetMapping
    public ResponseEntity<List<Operacao>> listarTodos() {
        return ResponseEntity.ok(operacaoService.listarTodos());
    }

    @PostMapping(value = "/deposito")
    public ResponseEntity<Operacao> salvarSaldo(@RequestBody OperacaoPostDto operacaoPostDto) {
        return new ResponseEntity<>(operacaoService.salvarSaldo(operacaoPostDto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/saque")
    public ResponseEntity<Operacao> salvarSaque(@RequestBody OperacaoPostDto operacaoPostDto) {
        return new ResponseEntity<>(operacaoService.salvarSaque(operacaoPostDto), HttpStatus.CREATED);
    }
}
