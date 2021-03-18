package com.desafio.controllers;

import com.desafio.dto.clienterequest.ClienteRequest;
import com.desafio.dto.clienteresponse.ClienteResponse;
import com.desafio.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping(value = "busca")
    public ResponseEntity<ClienteResponse> listarPorId(@RequestParam Long cliente) {
        return ResponseEntity.ok(clienteService.buscaIdCliente(cliente));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listar() {
        return ResponseEntity.ok(clienteService.listaCliente());
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> salvar(@Valid @RequestBody ClienteRequest clienteRequest) {
        return new ResponseEntity<>(clienteService.salvar(clienteRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ClienteResponse> atualizar(@Valid @RequestBody ClienteRequest clienteRequest) {
        return new ResponseEntity<>(clienteService.atualizar(clienteRequest), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
