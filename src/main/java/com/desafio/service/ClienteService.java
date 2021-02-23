package com.desafio.service;

import com.desafio.dto.ClientePostDto;
import com.desafio.dto.ClientePutDto;
import com.desafio.model.Cliente;
import com.desafio.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public List<Cliente> listAll(){
        return clienteRepository.findAll();
    }

    public Cliente save(ClientePostDto clientePostDto) {
        Cliente cliente = new Cliente();
        cliente.setNome(clientePostDto.getNome());
        cliente.setCpf(clientePostDto.getCpf());
        cliente.setTelefone(clientePostDto.getTelefone());
        cliente.setEndereco(clientePostDto.getEndereco());
        return clienteRepository.save(cliente);
    }

    public Cliente replace(ClientePutDto clientePutDto){
        Cliente cliente = new Cliente();
        cliente.setNome(clientePutDto.getNome());
        cliente.setCpf(clientePutDto.getCpf());
        cliente.setTelefone(clientePutDto.getTelefone());
        cliente.setEndereco(clientePutDto.getEndereco());
        cliente.setId(clientePutDto.getId());
        return clienteRepository.save(cliente);
    }

    public void delete(Long id){
        clienteRepository.delete(findeByIdOrThrowBadRequest(id));
    }

    public Cliente findeByIdOrThrowBadRequest(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new StringIndexOutOfBoundsException("Cliente nao encontrado"));
    }

}
