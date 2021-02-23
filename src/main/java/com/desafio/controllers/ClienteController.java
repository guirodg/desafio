package com.desafio.controllers;

import com.desafio.dto.ClientePostDto;
import com.desafio.dto.ClientePutDto;
import com.desafio.model.Cliente;
import com.desafio.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> list() {
        return ResponseEntity.ok(clienteService.listAll());
    }

    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody ClientePostDto clientePostDto) {
        return new ResponseEntity<>(clienteService.save(clientePostDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity replace(@RequestBody ClientePutDto clientePutDto) {
        return new ResponseEntity<>(clienteService.replace(clientePutDto), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        clienteService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
