package com.desafio.controllers;

import com.desafio.dto.operacaorequest.OperacaoRequest;
import com.desafio.dto.operacaoresponse.OperacaoResponse;
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
    public ResponseEntity<List<OperacaoResponse>> listarTodosExtratosPorId(@RequestParam int numeroConta) {
        return ResponseEntity.ok(operacaoService.listarExtrato(numeroConta));
    }

    @PostMapping(path = "/deposito")
    public ResponseEntity<OperacaoResponse> salvarSaldo(@RequestBody OperacaoRequest operacaoRequest) {
        return new ResponseEntity<>(operacaoService.salvarDeposito(operacaoRequest), HttpStatus.CREATED);
    }

    @PostMapping(path = "/saque")
    public ResponseEntity<OperacaoResponse> salvarSaque(@RequestBody OperacaoRequest operacaoRequest) {
        return new ResponseEntity<>(operacaoService.salvarSaque(operacaoRequest), HttpStatus.CREATED);
    }

    @PostMapping(path = "/transferencia")
    public ResponseEntity<OperacaoResponse> salvarTransferencia(@RequestBody OperacaoRequest operacaoRequest) {
        return new ResponseEntity<>(operacaoService.salvarTransferencia(operacaoRequest), HttpStatus.CREATED);
    }
}
