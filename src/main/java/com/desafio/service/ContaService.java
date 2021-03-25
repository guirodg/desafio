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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final KafkaTemplate<String, ControleContaExterno> kafkaTemplate;

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
        return contaResponse;
    }

    public List<ContaResponse> listaConta() {
        List<Conta> contas = contaRepository.findAll();
        List<ContaResponse> contaResponses = new ArrayList<>();

        for (Conta conta : contas) {
            ContaResponse contaResponse = ContaMapper.INSTANCE.toDTO(conta);
            contaResponses.add(contaResponse);
            if (Objects.isNull(contaResponses.get(0).getMensagem())) {
                contaResponse.setMensagem(contas.size() + " contas cadastradas");
            }
        }
        return contaResponses;
    }

    public ContaResponse salvar(ContaRequest contaRequest) {
        if (contaRequest.getTipoConta().isEmpty())
            throw new ExecaoMensagem("Preencha o campo tipo conta");
        if (clienteRepository.findByCpfCnpj(contaRequest.getCpfCliente()) == null)
            throw new ExecaoMensagem("CPF Informado não existe");
        if (contaRepository.findByNumeroConta(contaRequest.getNumeroConta()) != null)
            throw new ExecaoMensagem("Numero de conta ja existe");
        if (!contaRequest.getTipoConta().equalsIgnoreCase("PF") == contaRequest.getTipoConta()
                .equalsIgnoreCase("PJ") == contaRequest.getTipoConta().equalsIgnoreCase("GOV")) {
            throw new ExecaoMensagem("Deve ser 'PF' ou 'PJ' ou 'GOV' para cadastrar");
        }

        int limeteSaque = 0;
        if (contaRequest.getTipoConta().equalsIgnoreCase("PF"))
            limeteSaque = 5;
        if (contaRequest.getTipoConta().equalsIgnoreCase("PJ"))
            limeteSaque = 50;
        if (contaRequest.getTipoConta().equalsIgnoreCase("GOV"))
            limeteSaque = 250;

        Conta conta = ContaMapper.INSTANCE.toModel(contaRequest);
        conta.setSaldo(0);
        conta.setCpfCliente(contaRequest.getCpfCliente());
        contaRepository.save(conta);

        ControleContaExterno controleContaExterno = ControleContaExterno.builder()
                .cpfCliente(contaRequest.getCpfCliente())
                .numeroConta(contaRequest.getNumeroConta())
                .agencia(contaRequest.getAgencia())
                .limeteSaque(limeteSaque)
                .tipoConta(conta.getTipoConta())
                .build();

        kafkaTemplate.send("TOPIC_BANCO", controleContaExterno);

        ContaResponse contaResponse = ContaMapper.INSTANCE.toDTO(conta);
        contaResponse.setCpfCliente(contaRequest.getCpfCliente());
        contaResponse.setAgencia(conta.getAgencia());
        contaResponse.setMensagem("Limite de saque " + limeteSaque);
        return contaResponse;
    }

    public ContaResponse atualizar(ContaRequest contaRequest) {
        if (clienteRepository.findByCpfCnpj(contaRequest.getCpfCliente()) == null)
            throw new ExecaoMensagem("CPF Informado não existe");
        if (contaRequest.getTipoConta().isEmpty())
            throw new ExecaoMensagem("Preencha o campo tipo conta");
        if (!contaRequest.getTipoConta().equalsIgnoreCase("PF") == contaRequest.getTipoConta()
                .equalsIgnoreCase("PJ") == contaRequest.getTipoConta().equalsIgnoreCase("GOV")) {
            throw new ExecaoMensagem("Deve ser 'PF' ou 'PJ' ou 'GOV' para cadastrar");
        }

        int limeteSaque = 0;
        if (contaRequest.getTipoConta().equalsIgnoreCase("PF"))
            limeteSaque = 5;
        if (contaRequest.getTipoConta().equalsIgnoreCase("PJ"))
            limeteSaque = 50;
        if (contaRequest.getTipoConta().equalsIgnoreCase("GOV"))
            limeteSaque = 250;

        Conta conta = ContaMapper.INSTANCE.toModel(contaRequest);
        contaRepository.save(conta);

        ControleContaExterno controleContaExterno = ControleContaExterno.builder()
                .agencia(contaRequest.getAgencia())
                .numeroConta(contaRequest.getNumeroConta())
                .tipoConta(contaRequest.getTipoConta())
                .limeteSaque(limeteSaque)
                .cpfCliente(contaRequest.getCpfCliente()).build();

        kafkaTemplate.send("TOPIC_BANCO", controleContaExterno);

        ContaResponse contaResponse = ContaMapper.INSTANCE.toDTO(conta);
        contaResponse.setMensagem("Limite de saque " + limeteSaque);
        return contaResponse;
    }

    public ContaResponseDesconto atualizarSaldo(ContaRequestDesconto contaRequestDesconto) {
        if (contaRequestDesconto.getSaldo() <= 0)
            throw new ExecaoMensagem("Saldo não pode ser zero");
        Conta conta = contaRepository.findByNumeroConta(contaRequestDesconto.getNumeroConta());
        conta.setSaldo(conta.getSaldo() - contaRequestDesconto.getSaldo());

        Conta contaMapper = ContaMapper.INSTANCE.toModel(contaRequestDesconto);
        contaRepository.save(conta);
        ContaResponseDesconto contaResponse = ContaMapper.INSTANCE.toDTODesconto(contaMapper);
        contaResponse.setStatus("Desconto realizado!");
        return contaResponse;
    }

    public void deletar(int numeroConta) {
        contaRepository.delete(encontreIdOuErro(numeroConta));
    }

    public Conta encontreIdOuErro(int numeroConta) {
        Conta conta = contaRepository.findByNumeroConta(numeroConta);
        if (conta == null) {
            throw new ExecaoMensagem("Numero Conta informada não existe na base");
        }
        return conta;
    }
}
