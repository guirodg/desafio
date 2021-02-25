package com.desafio.service;

import com.desafio.dto.ClientePostDto;
import com.desafio.dto.ClientePutDto;
import com.desafio.erros.ExecaoMenssagem;
import com.desafio.mapper.ClienteMapper;
import com.desafio.model.Cliente;
import com.desafio.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public List<Cliente> listAll() {
        return clienteRepository.findAll();
    }

    public Cliente save(ClientePostDto clientePostDto) {
        if (clientePostDto.getNome().isEmpty() || clientePostDto.getCpf().isEmpty() ||
                clientePostDto.getEndereco().isEmpty() || clientePostDto.getTelefone().isEmpty()) {
            throw new ExecaoMenssagem("Todos os campos não foram preenchidos");
        }

        return clienteRepository.save(ClienteMapper.INSTANCE.toCliente(clientePostDto));
    }

    public Cliente replace(ClientePutDto clientePutDto) {
        if (clientePutDto.getNome().isEmpty() || clientePutDto.getCpf().isEmpty() ||
                clientePutDto.getEndereco().isEmpty() || clientePutDto.getTelefone().isEmpty() ||
                clientePutDto.getId() <= 0) {
            throw new ExecaoMenssagem("Todos os campos não foram preenchidos");
        }

        return clienteRepository.save(ClienteMapper.INSTANCE.toCliente(clientePutDto));
    }

    public void delete(Long id) {
        clienteRepository.delete(findeByIdOrThrowBadRequest(id));
    }

    public Cliente findeByIdOrThrowBadRequest(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ExecaoMenssagem("Cliente nao encontrado"));
    }

}
