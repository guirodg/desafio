package com.desafio.service;

import com.desafio.dto.clienterequest.ClienteRequest;
import com.desafio.dto.clienteresponse.ClienteResponse;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.mapper.ClienteMapper;
import com.desafio.model.Cliente;
import com.desafio.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteResponse salvar(ClienteRequest clienteRequest) {
        if (clienteRequest.getNome().isEmpty())
            throw new ExecaoMensagem("Preencha o campo nome!");
        if (clienteRequest.getEndereco().isEmpty())
            throw new ExecaoMensagem("Preencha o campo endereço!");
        if (clienteRequest.getTelefone().isEmpty())
            throw new ExecaoMensagem("Preencha o campo telefone!");
        if (clienteRequest.getCpf().isEmpty())
            throw new ExecaoMensagem("Preencha o campo cpf!");

        if (clienteRepository.findByCpf(clienteRequest.getCpf()) != null)
            throw new ExecaoMensagem("CPF já existe");

        Cliente cliente = ClienteMapper.INSTANCE.toModel(clienteRequest);
        clienteRepository.save(cliente);
        ClienteResponse clienteResponse = ClienteMapper.INSTANCE.toDto(cliente);
        clienteResponse.setMenssagem("Cliente cadastrado com sucesso!");
        return clienteResponse;
    }

    public ClienteResponse atualizar(ClienteRequest clienteRequest) {
        clienteRequest.setId(encontreIdOuErro(clienteRequest.getId()).getId());
        if (clienteRequest.getNome().isEmpty())
            throw new ExecaoMensagem("Preencha o campo nome!");
        if (clienteRequest.getEndereco().isEmpty())
            throw new ExecaoMensagem("Preencha o campo endereço!");
        if (clienteRequest.getTelefone().isEmpty())
            throw new ExecaoMensagem("Preencha o campo telefone!");
        if (clienteRequest.getCpf().isEmpty())
            throw new ExecaoMensagem("Preencha o campo cpf!");

        Cliente cliente = ClienteMapper.INSTANCE.toModel(clienteRequest);
        clienteRepository.save(cliente);
        ClienteResponse clienteResponse = ClienteMapper.INSTANCE.toDto(cliente);
        clienteResponse.setMenssagem("Cliente atualizado com sucesso!");
        return clienteResponse;
    }

    public void deletar(Long id) {
        clienteRepository.delete(encontreIdOuErro(id));
    }

    public Cliente encontreIdOuErro(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ExecaoMensagem("ID não existe"));
    }

    public List<ClienteResponse> listaCliente() {
        List<ClienteResponse> clienteResponses = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.findAll();
        for (Cliente cliente : clientes) {
            Cliente clienteSalvo = clienteRepository.save(cliente);
            ClienteResponse clienteResponse = ClienteMapper.INSTANCE.toDto(clienteSalvo);
            clienteResponse.setMenssagem("Ok");
            clienteResponses.add(clienteResponse);
        }
        return clienteResponses;
    }
}
