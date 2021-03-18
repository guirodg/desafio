package com.desafio.service;

import com.desafio.dto.contarequest.ContaPutDtoDesconto;
import com.desafio.dto.contarequest.ContaRequest;
import com.desafio.dto.contaresponse.ContaResponse;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.externo.ControleContaExterno;
import com.desafio.mapper.ContaMapper;
import com.desafio.model.Conta;
import com.desafio.repository.ClienteRepository;
import com.desafio.repository.ContaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContaService {
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ContaResponse salvar(ContaRequest contaRequest) throws JsonProcessingException {
        if (contaRequest.getTipoConta().isEmpty())
            throw new ExecaoMensagem("Preencha o campo tipo conta");
        if (!(clienteRepository.findByCpfCnpj(contaRequest.getCpfCliente()) != null))
            throw new ExecaoMensagem("CPF Informado não existe");
        if (contaRepository.findByNumeroConta(contaRequest.getNumeroConta()) != null)
            throw new ExecaoMensagem("Numero de conta ja existe");
        if (!contaRequest.getTipoConta().equals("pessoa fisica") != contaRequest.getTipoConta().equals("pessoa juridica")
                != contaRequest.getTipoConta().equals("governamental")) {
            throw new ExecaoMensagem("Deve ser 'pessoa fisica' ou 'pessoa juridica' ou 'governamental' para cadastrar");
        }

        int limeteSaque = 5;
        if (contaRequest.getTipoConta().equals("pessoa fisica"))
            limeteSaque = 5;
        if (contaRequest.getTipoConta().equals("pessoa juridica"))
            limeteSaque = 50;
        if (contaRequest.getTipoConta().equals("governamental"))
            limeteSaque = 250;

        Conta conta = ContaMapper.INSTANCE.toModel(contaRequest);
        conta.setSaldo(0);
        conta.setCpfCliente(contaRequest.getCpfCliente());
        conta.setDigitoVerificador(new Random().nextInt(10));
        Conta contaSalva = contaRepository.save(conta);

        ControleContaExterno controleContaExterno = ControleContaExterno.builder().idConta(contaSalva.getId()).
                limeteSaque(limeteSaque).tipoConta(conta.getTipoConta()).build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonControleConta = objectMapper.writeValueAsString(controleContaExterno);
        kafkaTemplate.send("TOPIC_BANCO", jsonControleConta);

        ContaResponse contaResponse = ContaMapper.INSTANCE.toDTO(conta);
        contaResponse.setCpfCliente(contaRequest.getCpfCliente());
        contaResponse.setDigitoVerificador(conta.getDigitoVerificador());
        contaResponse.setStatus("Conta cadastrada com Sucesso");
        return contaResponse;
    }

    public ContaResponse atualizar(ContaRequest contaRequest) {
        if (contaRequest.getTipoConta().isEmpty())
            throw new ExecaoMensagem("Preencha o campo tipo conta");
        if (!(clienteRepository.findByCpfCnpj(contaRequest.getCpfCliente()) != null))
            throw new ExecaoMensagem("CPF Informado não existe");
        if (contaRepository.findByNumeroConta(contaRequest.getNumeroConta()) != null)
            throw new ExecaoMensagem("Numero de conta ja existe");
        if (!contaRequest.getTipoConta().equals("pessoa fisica") != contaRequest.getTipoConta().equals("pessoa juridica")
                != contaRequest.getTipoConta().equals("governamental")) {
            throw new ExecaoMensagem("Deve ser 'pessoa fisica' ou 'pessoa juridica' ou 'governamental' para cadastrar");
        }

        encontreIdOuErro(contaRequest.getId());
        Conta conta = ContaMapper.INSTANCE.toModel(contaRequest);
        conta.setId(contaRequest.getId());
        conta.setDigitoVerificador(new Random().nextInt(10));
        contaRepository.save(conta);

        ContaResponse contaResponse = ContaMapper.INSTANCE.toDTO(conta);
        contaResponse.setStatus("Conta atualizada!");
        return contaResponse;
    }

    public Conta atualizarSaldo(ContaPutDtoDesconto contaPutDtoDesconto) {
        Optional<Conta> contaId = contaRepository.findById(contaPutDtoDesconto.getId());
        Conta contaSalva = encontreIdOuErro(contaPutDtoDesconto.getId());
        if (contaPutDtoDesconto.getSaldo() <= 0)
            throw new ExecaoMensagem("Digite o valor a ser descontado");

        Conta conta = ContaMapper.INSTANCE.toModel(contaPutDtoDesconto);
        conta.setId(contaSalva.getId());
        contaId.get().setSaldo(contaId.get().getSaldo() - contaPutDtoDesconto.getSaldo());
        contaRepository.save(contaId.get());
        return contaRepository.save(contaId.get());
    }

    public void deletar(Long id) {
        contaRepository.delete(encontreIdOuErro(id));
    }

    public Conta encontreIdOuErro(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new ExecaoMensagem("ID não existe"));
    }
}
