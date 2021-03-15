package com.desafio.controllers;

import com.desafio.dto.clienterequest.ClienteRequestPost;
import com.desafio.dto.clienterequest.ClienteRequestPut;
import com.desafio.dto.clienteresponse.ClienteResponsePost;
import com.desafio.dto.clienteresponse.ClienteResponsePut;
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

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClienteResponsePost> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscaIdCliente(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponsePost>> listar() {
        return ResponseEntity.ok(clienteService.listaCliente());
    }

    @PostMapping
    public ResponseEntity<ClienteResponsePost> salvar(@RequestBody ClienteRequestPost clienteRequestPost) {
        return new ResponseEntity<>(clienteService.salvar(clienteRequestPost), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ClienteResponsePut> atualizar(@RequestBody ClienteRequestPut clienteRequestPut) {
        return new ResponseEntity<>(clienteService.atualizar(clienteRequestPut), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
