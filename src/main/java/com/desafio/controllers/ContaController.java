package com.desafio.controllers;

import com.desafio.dto.contarequest.ContaRequest;
import com.desafio.dto.contarequest.ContaRequestDesconto;
import com.desafio.dto.contaresponse.ContaResponse;
import com.desafio.dto.contaresponse.ContaResponseDesconto;
import com.desafio.service.ContaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("contas")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService contaService;

    @GetMapping(value = "busca")
    public ResponseEntity<ContaResponse> listarPorParam(@Valid @RequestParam String cpfCliente,
                                                        @RequestParam int numeroConta, @RequestParam int digito) {
        return ResponseEntity.ok(contaService.listarPorParam(cpfCliente, numeroConta, digito));
    }

    @GetMapping
    public ResponseEntity<List<ContaResponse>> listar() {
        return ResponseEntity.ok(contaService.listaConta());
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
    public ResponseEntity<ContaResponseDesconto> atualizarSaldo(@RequestBody ContaRequestDesconto contaRequestDesconto) {
        return new ResponseEntity<>(contaService.atualizarSaldo(contaRequestDesconto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contaService.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
