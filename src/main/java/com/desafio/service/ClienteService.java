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

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente salvar(ClientePostDto clientePostDto) {
        if (clientePostDto.getNome().isEmpty() ||
                clientePostDto.getEndereco().isEmpty() ||
                clientePostDto.getTelefone().isEmpty() ||
                clientePostDto.getCpf().isEmpty()) {
            throw new ExecaoMenssagem("Todos os campos não foram preenchidos");
        }
        if (clienteRepository.findByCpf(clientePostDto.getCpf()) != null)
            throw new ExecaoMenssagem("CPF já existe");

        Cliente cliente = ClienteMapper.INSTANCE.toCliente(clientePostDto);
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(ClientePutDto clientePutDto) {
        if (clientePutDto.getNome().isEmpty() ||
                clientePutDto.getEndereco().isEmpty() ||
                clientePutDto.getTelefone().isEmpty()) {
            throw new ExecaoMenssagem("Todos os campos não foram preenchidos");
        }
        Cliente clienteSalvo = findByIdOrErro(clientePutDto.getId());
        Cliente cliente = ClienteMapper.INSTANCE.toCliente(clientePutDto);
        cliente.setId(clienteSalvo.getId());

        return clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        clienteRepository.delete(findByIdOrErro(id));
    }

    public Cliente findByIdOrErro(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ExecaoMenssagem("ID não existe"));
    }
}
