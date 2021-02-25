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
                clientePutDto.getEndereco().isEmpty() || clientePutDto.getTelefone().isEmpty()) {
            throw new ExecaoMenssagem("Todos os campos não foram preenchidos");
        }
        Cliente clienteCadastrado = findByIdOrErro(clientePutDto.getId());
        Cliente cliente = ClienteMapper.INSTANCE.toCliente(clientePutDto);
        cliente.setId(clienteCadastrado.getId());

        return clienteRepository.save(cliente);
    }

    public void delete(Long id) {
        clienteRepository.delete(findByIdOrErro(id));
    }

    public Cliente findByIdOrErro(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ExecaoMenssagem("Id não existe"));
    }

}
