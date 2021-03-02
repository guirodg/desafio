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
    public ResponseEntity<List<Operacao>> listarTodosExtratosPorId(@RequestParam Long contaId) {
        return ResponseEntity.ok(operacaoService.listarExtrato(contaId));
    }

    @PostMapping(path = "/deposito")
    public ResponseEntity<Operacao> salvarSaldo(@RequestBody OperacaoPostDto operacaoPostDto) {
        return new ResponseEntity<>(operacaoService.salvarDeposito(operacaoPostDto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/saque")
    public ResponseEntity<Operacao> salvarSaque(@RequestBody OperacaoPostDto operacaoPostDto) {
        return new ResponseEntity<>(operacaoService.salvarSaque(operacaoPostDto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/transferencia")
    public ResponseEntity<Operacao> salvarTransferencia(@RequestBody OperacaoPostDto operacaoPostDto) {
        return new ResponseEntity<>(operacaoService.salvarTransferencia(operacaoPostDto), HttpStatus.CREATED);
    }


}
