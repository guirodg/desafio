package com.desafio.controllers;

import com.desafio.dto.operacaorequest.OperacaoRequest;
import com.desafio.dto.operacaoresponse.OperacaoResponse;
import com.desafio.dto.operacaoresponse.OperacaoResponseDepositoSaque;
import com.desafio.service.OperacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("operacoes")
@RequiredArgsConstructor
public class OperacaoController {

    private final OperacaoService operacaoService;

    @GetMapping
    public ResponseEntity<List<OperacaoResponse>> listarTodosExtratosPorId(@Valid @RequestParam String cpfCliente, @RequestParam int numeroConta, @RequestParam int agencia) {
        return ResponseEntity.ok(operacaoService.listarExtrato(cpfCliente, numeroConta, agencia));
    }

    @PostMapping(path = "/deposito")
    public ResponseEntity<OperacaoResponseDepositoSaque> salvarSaldo(@Valid @RequestBody OperacaoRequest operacaoRequest) {
        return new ResponseEntity<>(operacaoService.salvarDeposito(operacaoRequest), HttpStatus.CREATED);
    }

    @PostMapping(path = "/saque")
    public ResponseEntity<OperacaoResponseDepositoSaque> salvarSaque(@Valid @RequestBody OperacaoRequest operacaoRequest) {
        return new ResponseEntity<>(operacaoService.salvarSaque(operacaoRequest), HttpStatus.CREATED);
    }

    @PostMapping(path = "/transferencia")
    public ResponseEntity<OperacaoResponse> salvarTransferencia(@Valid @RequestBody OperacaoRequest operacaoRequest) {
        return new ResponseEntity<>(operacaoService.salvarTransferencia(operacaoRequest), HttpStatus.CREATED);
    }
}
