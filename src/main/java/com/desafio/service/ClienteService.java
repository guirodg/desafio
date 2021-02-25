package com.desafio.service;

import com.desafio.dto.reqcliente.ClientePostDto;
import com.desafio.dto.reqcliente.ClientePutDto;
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
        if (clientePostDto.getNome().isEmpty() ||
                clientePostDto.getEndereco().isEmpty() || clientePostDto.getTelefone().isEmpty()) {
            throw new ExecaoMenssagem("Todos os campos não foram preenchidos");
        }

        return clienteRepository.save(ClienteMapper.INSTANCE.toCliente(clientePostDto));
    }

//    public Cliente replace(ClientePutDto clientePutDto) {
//        if (clientePutDto.getNome().isEmpty() ||
//                clientePutDto.getEndereco().isEmpty() || clientePutDto.getTelefone().isEmpty()) {
//            throw new ExecaoMenssagem("Todos os campos não foram preenchidos");
//        }
//        Cliente clienteCadastrado = findByIdOrErro(clientePutDto.getCpf());
//        Cliente cliente = ClienteMapper.INSTANCE.toCliente(clientePutDto);
//        cliente.setCpf(clienteCadastrado.getCpf());
//
//        return clienteRepository.save(cliente);
//    }
//
//    public void delete(String cpf) {
//        clienteRepository.delete(findByIdOrErro(cpf));
//    }

//    public Cliente findByIdOrErro(String cpf) {
//        return clienteRepository.findById(cpf)
//                .orElseThrow(() -> new ExecaoMenssagem("Id não existe"));
//    }

}
