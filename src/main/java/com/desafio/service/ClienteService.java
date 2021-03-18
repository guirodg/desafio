package com.desafio.service;

import com.desafio.dto.clienterequest.ClienteRequest;
import com.desafio.dto.clienteresponse.ClienteResponse;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.mapper.ClienteMapper;
import com.desafio.model.Cliente;
import com.desafio.repository.ClienteRepository;
import com.desafio.util.Validador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final Validador validador;

    public ClienteResponse buscaIdCliente(Long cliente) {
        Optional<Cliente> clienteSalvo = Optional.ofNullable(clienteRepository.findById(cliente).orElseThrow(() ->
                new ExecaoMensagem("ID nao exite")));

        ClienteResponse clienteResponse = ClienteMapper.INSTANCE.toDTO(clienteSalvo.get());
        clienteResponse.setStatus("Cliente obtido com sucesso!");
        return clienteResponse;
    }

    public List<ClienteResponse> listaCliente() {
        List<ClienteResponse> clienteResponsPosts = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.findAll();
        for (Cliente cliente : clientes) {
            Cliente clienteSalvo = clienteRepository.save(cliente);
            ClienteResponse clienteResponse = ClienteMapper.INSTANCE.toDTO(clienteSalvo);
            clienteResponse.setStatus("Sucesso!");
            clienteResponsPosts.add(clienteResponse);
        }
        return clienteResponsPosts;
    }

    public ClienteResponse salvar(ClienteRequest clienteRequest) {
        if (clienteRequest.getNome().isEmpty())
            throw new ExecaoMensagem("Preencha o campo nome!");
        if (clienteRequest.getEndereco().isEmpty())
            throw new ExecaoMensagem("Preencha o campo endereço!");
        if (clienteRequest.getTelefone().isEmpty())
            throw new ExecaoMensagem("Preencha o campo telefone!");
        if (clienteRequest.getCpfCnpj().isEmpty())
            throw new ExecaoMensagem("Preencha o campo cpf!");

        if (clienteRepository.findByCpfCnpj(clienteRequest.getCpfCnpj()) != null)
            throw new ExecaoMensagem("CPF já existe");

        validador.validaCpf(clienteRequest.getCpfCnpj());

        Cliente cliente = ClienteMapper.INSTANCE.toModel(clienteRequest);
        clienteRepository.save(cliente);
        ClienteResponse clienteResponse = ClienteMapper.INSTANCE.toDTO(cliente);
        clienteResponse.setStatus("Cliente cadastrado com sucesso!");
        return clienteResponse;
    }

    public ClienteResponse atualizar(ClienteRequest clienteRequest) {
        clienteRequest.setId(encontreIdOuErro(clienteRequest.getId()).getId());
        if (clienteRequest.getNome().isEmpty() || clienteRequest.getNome().equals(null))
            throw new ExecaoMensagem("Preencha o campo nome!");
        if (clienteRequest.getEndereco().isEmpty())
            throw new ExecaoMensagem("Preencha o campo endereço!");
        if (clienteRequest.getTelefone().isEmpty())
            throw new ExecaoMensagem("Preencha o campo telefone!");
        if (clienteRequest.getCpfCnpj().isEmpty())
            throw new ExecaoMensagem("Preencha o campo cpf!");
        if (clienteRepository.findByCpfCnpj(clienteRequest.getCpfCnpj()) != null)
            throw new ExecaoMensagem("CPF já existe");

        validador.validaCpf(clienteRequest.getCpfCnpj());

        Cliente cliente = ClienteMapper.INSTANCE.toModel(clienteRequest);
        clienteRepository.save(cliente);
        ClienteResponse clienteResponse = ClienteMapper.INSTANCE.toDTO(cliente);
        clienteResponse.setStatus("Cliente atualizado com sucesso!");
        return clienteResponse;
    }

    public void deletar(Long id) {
        clienteRepository.delete(encontreIdOuErro(id));
    }

    public Cliente encontreIdOuErro(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ExecaoMensagem("ID não existe"));
    }
}
