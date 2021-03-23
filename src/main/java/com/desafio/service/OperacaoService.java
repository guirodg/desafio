package com.desafio.service;

import com.desafio.dto.operacaorequest.OperacaoRequest;
import com.desafio.dto.operacaoresponse.OperacaoResponse;
import com.desafio.dto.operacaoresponse.OperacaoResponseDepositoSaque;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.externo.ControleContaExterno;
import com.desafio.mapper.OperacaoMapper;
import com.desafio.model.Cliente;
import com.desafio.model.Conta;
import com.desafio.model.Operacao;
import com.desafio.repository.ClienteRepository;
import com.desafio.repository.ContaRepository;
import com.desafio.repository.OperacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OperacaoService {
    private final OperacaoRepository operacaoRepository;
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public List<OperacaoResponse> listarExtrato(String cpfCliente, int numeroConta, int agencia) {
        List<Operacao> operacaoNumero = operacaoRepository.findAllByNumeroContaOrigem(numeroConta);
        Cliente cliente = clienteRepository.findByCpfCnpj(cpfCliente);
        Conta conta = contaRepository.findByNumeroConta(numeroConta);

        if (conta == null)
            throw new ExecaoMensagem("Conta informada não existe");
        if (conta.getAgencia() != agencia)
            throw new ExecaoMensagem("Agencia informada não existe");
        if (cliente == null)
            throw new ExecaoMensagem("CPF informado não existe");

        List<OperacaoResponse> operacaoResponses = new ArrayList<>();
        for (Operacao operacao : operacaoNumero) {
            OperacaoResponse operacaoResponse = OperacaoMapper.INSTANCE.toDto(operacao);
            operacaoResponses.add(operacaoResponse);

            if(Objects.isNull(operacaoResponses.get(0).getMensagem()))
                operacaoResponse.setMensagem(operacaoNumero.size() + " Operaçoes realizadas");
        }
        return operacaoResponses;
    }

    public OperacaoResponseDepositoSaque salvarDeposito(OperacaoRequest operacaoRequest) {
        if (operacaoRequest.getTipoOperacao().isEmpty())
            throw new ExecaoMensagem("Digite tipo da Operacao");
        if (operacaoRequest.getNumeroContaOrigem() <= 0)
            throw new ExecaoMensagem("Digite Numero da conta origem para operação");
        if (operacaoRequest.getValor() <= 0)
            throw new ExecaoMensagem("Digite 'valor' para realizar deposito");
        if (!operacaoRequest.getTipoOperacao().equalsIgnoreCase("deposito"))
            throw new ExecaoMensagem("Digite tipo de operacao: 'deposito'");

        Conta conta = contaRepository.findByNumeroConta(operacaoRequest.getNumeroContaOrigem());
        if (conta == null)
            throw new ExecaoMensagem("Numero da conta origem não existe");
        if (conta.getAgencia() != operacaoRequest.getAgenciaOrigem())
            throw new ExecaoMensagem("Agencia da conta origem não confere");

        conta.setSaldo(conta.getSaldo() + operacaoRequest.getValor());
        contaRepository.save(conta);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
        operacaoRequest.setData(LocalDateTime.now().format(dateTimeFormatter));

        Operacao operacao = OperacaoMapper.INSTANCE.toModel(operacaoRequest);
        operacaoRepository.save(operacao);
        OperacaoResponseDepositoSaque operacaoResponseDepositoSaque = OperacaoMapper.INSTANCE.toDtoDepositoSaque(operacao);
        return operacaoResponseDepositoSaque;
    }

    public OperacaoResponseDepositoSaque salvarSaque(OperacaoRequest operacaoRequest) {
        if (operacaoRequest.getValor() <= 0)
            throw new ExecaoMensagem("Digite o valor do saque");
        if (operacaoRequest.getNumeroContaOrigem() <= 0)
            throw new ExecaoMensagem("Digite Numero da conta que deseja relizar operação");
        if (!operacaoRequest.getTipoOperacao().equalsIgnoreCase("saque"))
            throw new ExecaoMensagem("Digite tipo de operacao: 'saque'");

        Conta conta = contaRepository.findByNumeroConta(operacaoRequest.getNumeroContaOrigem());
        if (conta == null)
            throw new ExecaoMensagem("Numero da conta origem não existe");
        if (conta.getAgencia() != operacaoRequest.getAgenciaOrigem())
            throw new ExecaoMensagem("Agencia da conta origem não confere");

        if (conta.getSaldo() < operacaoRequest.getValor())
            throw new ExecaoMensagem("Saldo insuficiente");

        if (conta.getSaldo() >= operacaoRequest.getValor()) {
            conta.setSaldo(conta.getSaldo() - operacaoRequest.getValor());
            contaRepository.save(conta);
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
        operacaoRequest.setData(LocalDateTime.now().format(dateTimeFormatter));

        ControleContaExterno controleContaExterno = ControleContaExterno.builder()
                .numeroConta(operacaoRequest.getNumeroContaOrigem())
                .agencia(operacaoRequest.getAgenciaOrigem())
                .build();
        new RestTemplate().put("http://localhost:8081/controle", controleContaExterno);

        Operacao operacao = OperacaoMapper.INSTANCE.toModel(operacaoRequest);
        operacaoRepository.save(operacao);
        OperacaoResponseDepositoSaque operacaoResponseDepositoSaque = OperacaoMapper.INSTANCE.toDtoDepositoSaque(operacao);
        return operacaoResponseDepositoSaque;
    }

    public OperacaoResponse salvarTransferencia(OperacaoRequest operacaoRequest) {
        if (operacaoRequest.getValor() <= 0)
            throw new ExecaoMensagem("Digite o valor para a transferencia");
        if (operacaoRequest.getNumeroContaOrigem() <= 0)
            throw new ExecaoMensagem("Digite Numero da conta que deseja relizar operação");
        if (!operacaoRequest.getTipoOperacao().equalsIgnoreCase("transferencia"))
            throw new ExecaoMensagem("Digite tipo de operacao: 'transferencia'");
        if (operacaoRequest.getNumeroContaOrigem() == operacaoRequest.getNumeroContaDestino())
            throw new ExecaoMensagem("Não pode realizar transferencia para mesma conta");

        Conta contaOrigem = contaRepository.findByNumeroConta(operacaoRequest.getNumeroContaOrigem());
        Conta contaDestino = contaRepository.findByNumeroConta(operacaoRequest.getNumeroContaDestino());
        if (contaOrigem == null)
            throw new ExecaoMensagem("Numero da conta origem não existe");
        if (contaDestino == null)
            throw new ExecaoMensagem("Numero da conta destino não existe");
        if (contaOrigem.getAgencia() != operacaoRequest.getAgenciaOrigem())
            throw new ExecaoMensagem("Agencia da conta origem não confere");
        if (contaDestino.getAgencia() != operacaoRequest.getAgenciaDestino())
            throw new ExecaoMensagem("Agencia da conta destino não confere");
        if (contaOrigem.getSaldo() < operacaoRequest.getValor()) {
            throw new ExecaoMensagem("Saldo insuficiente da conta origem");
        }

        if (contaOrigem.getSaldo() >= operacaoRequest.getValor()) {
            contaOrigem.setSaldo(contaOrigem.getSaldo() - operacaoRequest.getValor());
            contaRepository.save(contaOrigem);
            contaDestino.setSaldo(contaDestino.getSaldo() + operacaoRequest.getValor());
            contaRepository.save(contaDestino);
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
        operacaoRequest.setData(LocalDateTime.now().format(dateTimeFormatter));

        Operacao operacao = OperacaoMapper.INSTANCE.toModel(operacaoRequest);
        OperacaoResponse operacaoResponse = OperacaoMapper.INSTANCE.toDto(operacao);
        operacaoRepository.save(operacao);
        return operacaoResponse;
    }
}
