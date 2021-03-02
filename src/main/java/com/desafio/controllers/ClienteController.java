package com.desafio.controllers;

import com.desafio.dto.reqcliente.ClientePostDto;
import com.desafio.dto.reqcliente.ClientePutDto;
import com.desafio.model.Cliente;
import com.desafio.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Cliente> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.encontreIdOuErro(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody ClientePostDto clientePostDto) {
        return new ResponseEntity<>(clienteService.salvar(clientePostDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity atualizar(@RequestBody ClientePutDto clientePutDto) {
        return new ResponseEntity<>(clienteService.atualizar(clientePutDto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
