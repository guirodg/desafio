package com.desafio.service;

import com.desafio.dto.clienterequest.ClienteRequestPost;
import com.desafio.dto.clienterequest.ClienteRequestPut;
import com.desafio.dto.clienteresponse.ClienteResponsePost;
import com.desafio.dto.clienteresponse.ClienteResponsePut;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.mapper.ClienteMapper;
import com.desafio.model.Cliente;
import com.desafio.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteResponsePost buscaIdCliente(Long id) {
        Optional<Cliente> clienteSalvo = Optional.ofNullable(clienteRepository.findById(id).orElseThrow(() ->
                new ExecaoMensagem("ID nao exite")));

        ClienteResponsePost clienteResponsePost = ClienteMapper.INSTANCE.toDtoPost(clienteSalvo.get());
        clienteResponsePost.setStatus("Cliente obtido com sucesso!");
        return clienteResponsePost;
    }

    public List<ClienteResponsePost> listaCliente() {
        List<ClienteResponsePost> clienteResponsPosts = new ArrayList<>();
        List<Cliente> clientes = clienteRepository.findAll();
        for (Cliente cliente : clientes) {
            Cliente clienteSalvo = clienteRepository.save(cliente);
            ClienteResponsePost clienteResponsePost = ClienteMapper.INSTANCE.toDtoPost(clienteSalvo);
            clienteResponsePost.setStatus("Sucesso!");
            clienteResponsPosts.add(clienteResponsePost);
        }
        return clienteResponsPosts;
    }

    public ClienteResponsePost salvar(ClienteRequestPost clienteRequestPost) {
        if (clienteRequestPost.getNome().isEmpty())
            throw new ExecaoMensagem("Preencha o campo nome!");
        if (clienteRequestPost.getEndereco().isEmpty())
            throw new ExecaoMensagem("Preencha o campo endereço!");
        if (clienteRequestPost.getTelefone().isEmpty())
            throw new ExecaoMensagem("Preencha o campo telefone!");
        if (clienteRequestPost.getCpf().isEmpty())
            throw new ExecaoMensagem("Preencha o campo cpf!");

        if (clienteRepository.findByCpf(clienteRequestPost.getCpf()) != null)
            throw new ExecaoMensagem("CPF já existe");

        Cliente cliente = ClienteMapper.INSTANCE.toModelPost(clienteRequestPost);
        clienteRepository.save(cliente);
        ClienteResponsePost clienteResponsePost = ClienteMapper.INSTANCE.toDtoPost(cliente);
        clienteResponsePost.setStatus("Cliente cadastrado com sucesso!");
        return clienteResponsePost;
    }

    public ClienteResponsePut atualizar(ClienteRequestPut clienteRequestPut) {
        clienteRequestPut.setId(encontreIdOuErro(clienteRequestPut.getId()).getId());
        if (clienteRequestPut.getNome().isEmpty())
            throw new ExecaoMensagem("Preencha o campo nome!");
        if (clienteRequestPut.getEndereco().isEmpty())
            throw new ExecaoMensagem("Preencha o campo endereço!");
        if (clienteRequestPut.getTelefone().isEmpty())
            throw new ExecaoMensagem("Preencha o campo telefone!");

        Cliente cliente = ClienteMapper.INSTANCE.toModelPut(clienteRequestPut);
        clienteRepository.save(cliente);
        ClienteResponsePut clienteResponsePut = ClienteMapper.INSTANCE.toDtoPut(cliente);
        clienteResponsePut.setStatus("Cliente atualizado com sucesso!");
        return clienteResponsePut;
    }

    public void deletar(Long id) {
        clienteRepository.delete(encontreIdOuErro(id));
    }

    public Cliente encontreIdOuErro(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ExecaoMensagem("ID não existe"));
    }
}
