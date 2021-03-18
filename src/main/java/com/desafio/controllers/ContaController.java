package com.desafio.controllers;

import com.desafio.dto.contarequest.ContaPutDtoDesconto;
import com.desafio.dto.contarequest.ContaRequest;
import com.desafio.dto.contaresponse.ContaResponse;
import com.desafio.model.Conta;
import com.desafio.service.ContaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("contas")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService contaService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Conta> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.encontreIdOuErro(id));
    }

    @PostMapping
    public ResponseEntity<ContaResponse> salvar(@Valid @RequestBody ContaRequest contaRequest) throws JsonProcessingException {
        return new ResponseEntity<>(contaService.salvar(contaRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ContaResponse> atualizar(@Valid @RequestBody ContaRequest contaRequest) {
        return new ResponseEntity<>(contaService.atualizar(contaRequest), HttpStatus.OK);
    }

    @PutMapping(path = "/descontar")
    public ResponseEntity<Conta> atualizarSaldo(@RequestBody ContaPutDtoDesconto contaPutDtoDesconto) {
        return new ResponseEntity<>(contaService.atualizarSaldo(contaPutDtoDesconto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contaService.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
