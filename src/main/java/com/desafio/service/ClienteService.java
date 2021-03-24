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
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponse buscaCpfCliente(String cpfCliente) {
        Cliente cliente = clienteRepository.findByCpfCnpj(cpfCliente);
        if (cliente == null)
            throw new ExecaoMensagem("CPF Informado não existe");

        ClienteResponse clienteResponse = ClienteMapper.INSTANCE.toDTO(cliente);
        clienteResponse.setMensagem("Cliente obtido com sucesso!");
        return clienteResponse;
    }

    public List<ClienteResponse> listaCliente() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteResponse> clienteResponses = new ArrayList<>();

        for (Cliente cliente : clientes) {
            ClienteResponse clienteResponse = ClienteMapper.INSTANCE.toDTO(cliente);
            clienteResponses.add(clienteResponse);

            if (Objects.isNull(clienteResponses.get(0).getMensagem()))
                clienteResponse.setMensagem(clientes.size() + " Clientes cadastrados");
        }
        return clienteResponses;
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

        Validador.validaCpf(clienteRequest.getCpfCnpj());

        Cliente cliente = ClienteMapper.INSTANCE.toModel(clienteRequest);
        clienteRepository.save(cliente);
        ClienteResponse clienteResponse = ClienteMapper.INSTANCE.toDTO(cliente);
        return clienteResponse;
    }

    public ClienteResponse atualizar(ClienteRequest clienteRequest) {
        if (clienteRequest.getNome().isEmpty() || clienteRequest.getNome().equals(null))
            throw new ExecaoMensagem("Preencha o campo nome!");
        if (clienteRequest.getEndereco().isEmpty())
            throw new ExecaoMensagem("Preencha o campo endereço!");
        if (clienteRequest.getTelefone().isEmpty())
            throw new ExecaoMensagem("Preencha o campo telefone!");
        if (clienteRequest.getCpfCnpj().isEmpty())
            throw new ExecaoMensagem("Preencha o campo cpf!");
        Cliente clienteCpf = clienteRepository.findByCpfCnpj(clienteRequest.getCpfCnpj());
        if (clienteCpf == null)
            throw new ExecaoMensagem("CPF Informado não existe na base");
        Validador.validaCpf(clienteRequest.getCpfCnpj());

        Cliente cliente = ClienteMapper.INSTANCE.toModel(clienteRequest);
        clienteRepository.save(cliente);
        ClienteResponse clienteResponse = ClienteMapper.INSTANCE.toDTO(cliente);
        clienteResponse.setMensagem("Cliente atualizado com sucesso!");
        return clienteResponse;
    }

    public void deletar(String cpf) {
        clienteRepository.delete(encontreIdOuErro(cpf));
    }

    public Cliente encontreIdOuErro(String cpf) {
        Cliente cliente = clienteRepository.findByCpfCnpj(cpf);
        if (cliente == null) {
            throw new ExecaoMensagem("CPF Informado não existe na base");
        }
        return cliente;
    }
}
