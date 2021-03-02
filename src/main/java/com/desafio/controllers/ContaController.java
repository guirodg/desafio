package com.desafio.controllers;

import com.desafio.dto.reqconta.ContaPostDto;
import com.desafio.dto.reqconta.ContaPutDto;
import com.desafio.model.Conta;
import com.desafio.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Conta> salvar(@RequestBody ContaPostDto contaPostDto) {
        return new ResponseEntity<>(contaService.salvar(contaPostDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Conta> atualizar(@RequestBody ContaPutDto contaPutDto) {
        return new ResponseEntity<>(contaService.atualizar(contaPutDto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contaService.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
