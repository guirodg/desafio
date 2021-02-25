package com.desafio.controllers;

import com.desafio.dto.reqcliente.ClientePostDto;
import com.desafio.dto.reqcliente.ClientePutDto;
import com.desafio.erros.ExecaoMenssagem;
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
        try {
            return new ResponseEntity<>(clienteService.save(clientePostDto), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ExecaoMenssagem("CPF Inválido");
        }
    }

    @PutMapping
    public ResponseEntity replace(@RequestBody ClientePutDto clientePutDto) {
        return new ResponseEntity<>(clienteService.replace(clientePutDto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{cpf}")
    public ResponseEntity delete(@PathVariable Long cpf) {
        clienteService.delete(cpf);
        return new ResponseEntity(HttpStatus.OK);
    }
}
