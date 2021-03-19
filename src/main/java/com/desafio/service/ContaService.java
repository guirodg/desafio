package com.desafio.service;

import com.desafio.dto.contarequest.ContaRequest;
import com.desafio.dto.contarequest.ContaRequestDesconto;
import com.desafio.dto.contaresponse.ContaResponse;
import com.desafio.dto.contaresponse.ContaResponseDesconto;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.externo.ControleContaExterno;
import com.desafio.mapper.ContaMapper;
import com.desafio.model.Cliente;
import com.desafio.model.Conta;
import com.desafio.repository.ClienteRepository;
import com.desafio.repository.ContaRepository;
import com.desafio.util.Validador;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContaService {
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ContaResponse listarPorParam(String cpfCliente, int numeroConta, int agencia) {
        Cliente cliente = clienteRepository.findByCpfCnpj(cpfCliente);
        Conta conta = contaRepository.findByNumeroConta(numeroConta);
        if (cliente == null)
            throw new ExecaoMensagem("Não existe conta para o CPF informado");
        if (conta == null)
            throw new ExecaoMensagem("Não existe numero da conta informado");
        if (conta.getAgencia() != agencia)
            throw new ExecaoMensagem("Agencia informada não confere");

        ContaResponse contaResponse = ContaMapper.INSTANCE.toDTO(conta);
        contaResponse.setStatus("Busca com sucesso!");
        return contaResponse;
    }

    public List<ContaResponse> listaConta() {
        List<ContaResponse> contaResponses = new ArrayList<>();
        List<Conta> contas = contaRepository.findAll();
        for (Conta conta : contas) {
            Conta contaSalva = contaRepository.save(conta);
            ContaResponse contaResponse = ContaMapper.INSTANCE.toDTO(contaSalva);
            contaResponse.setStatus("Sucesso!");
            contaResponses.add(contaResponse);
        }
        return contaResponses;
    }

    public ContaResponse salvar(ContaRequest contaRequest) throws JsonProcessingException {
        if (contaRequest.getTipoConta().isEmpty())
            throw new ExecaoMensagem("Preencha o campo tipo conta");
        if (clienteRepository.findByCpfCnpj(contaRequest.getCpfCliente()) == null)
            throw new ExecaoMensagem("CPF Informado não existe");
        if (contaRepository.findByNumeroConta(contaRequest.getNumeroConta()) != null)
            throw new ExecaoMensagem("Numero de conta ja existe");
        if (!contaRequest.getTipoConta().equals("pessoa fisica") == contaRequest.getTipoConta().equals("pessoa juridica") == contaRequest.getTipoConta().equals("governamental")) {
            throw new ExecaoMensagem("Deve ser 'pessoa fisica' ou 'pessoa juridica' ou 'governamental' para cadastrar");
        }

        int limeteSaque = 0;
        if (contaRequest.getTipoConta().equals("pessoa fisica"))
            limeteSaque = 5;
        if (contaRequest.getTipoConta().equals("pessoa juridica"))
            limeteSaque = 50;
        if (contaRequest.getTipoConta().equals("governamental"))
            limeteSaque = 250;

        Conta conta = ContaMapper.INSTANCE.toModel(contaRequest);
        conta.setSaldo(0);
        conta.setCpfCliente(contaRequest.getCpfCliente());
        Conta contaSalva = contaRepository.save(conta);

        ControleContaExterno controleContaExterno = ControleContaExterno.builder().numeroConta(contaSalva.getNumeroConta()).
                limeteSaque(limeteSaque).tipoConta(conta.getTipoConta()).build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonControleConta = objectMapper.writeValueAsString(controleContaExterno);
        kafkaTemplate.send("TOPIC_BANCO", jsonControleConta);

        ContaResponse contaResponse = ContaMapper.INSTANCE.toDTO(conta);
        contaResponse.setCpfCliente(contaRequest.getCpfCliente());
        contaResponse.setAgencia(conta.getAgencia());
        contaResponse.setStatus("Conta cadastrada com Sucesso");
        return contaResponse;
    }

    public ContaResponse atualizar(ContaRequest contaRequest) {
        encontreIdOuErro(contaRequest.getId());
        if (contaRequest.getId() <= 0)
            throw new ExecaoMensagem("Preencha id da conta");
        if (contaRequest.getTipoConta().isEmpty())
            throw new ExecaoMensagem("Preencha o campo tipo conta");
        if (contaRepository.findByNumeroConta(contaRequest.getNumeroConta()) != null)
            throw new ExecaoMensagem("Numero de conta ja existe");
        if (!contaRequest.getTipoConta().equals("pessoa fisica") == contaRequest.getTipoConta().equals("pessoa juridica") == contaRequest.getTipoConta().equals("governamental")) {
            throw new ExecaoMensagem("Deve ser 'pessoa fisica' ou 'pessoa juridica' ou 'governamental' para cadastrar");
        }
        Validador.validaCpf(contaRequest.getCpfCliente());

        Conta conta = ContaMapper.INSTANCE.toModel(contaRequest);
        conta.setId(contaRequest.getId());
        conta.setAgencia(new Random().nextInt(10));
        contaRepository.save(conta);

        ContaResponse contaResponse = ContaMapper.INSTANCE.toDTO(conta);
        contaResponse.setStatus("Conta atualizada!");
        return contaResponse;
    }

    public ContaResponseDesconto atualizarSaldo(ContaRequestDesconto contaRequestDesconto) {
        encontreIdOuErro(contaRequestDesconto.getId());
        if (contaRequestDesconto.getSaldo() <= 0)
            throw new ExecaoMensagem("Saldo não pode ser zero");
        Optional<Conta> conta = contaRepository.findById(contaRequestDesconto.getId());
        conta.get().setSaldo(conta.get().getSaldo() - contaRequestDesconto.getSaldo());
        Conta contaMapper = ContaMapper.INSTANCE.toModel(contaRequestDesconto);

        contaRepository.save(conta.get());
        ContaResponseDesconto contaResponse = ContaMapper.INSTANCE.toDTODesconto(contaMapper);
        contaResponse.setStatus("Desconto realizado!");
        return contaResponse;
    }

    public void deletar(Long id) {
        contaRepository.delete(encontreIdOuErro(id));
    }

    public Conta encontreIdOuErro(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new ExecaoMensagem("ID não existe"));
    }
}
