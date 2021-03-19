package com.desafio.service;

import com.desafio.dto.operacaorequest.OperacaoRequest;
import com.desafio.dto.operacaoresponse.OperacaoResponse;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.mapper.OperacaoMapper;
import com.desafio.model.Conta;
import com.desafio.model.Operacao;
import com.desafio.repository.ContaRepository;
import com.desafio.repository.OperacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperacaoService {
    private final OperacaoRepository operacaoRepository;
    private final ContaRepository contaRepository;

//    public List<Operacao> listarExtrato(Long contaId) {
//        return operacaoRepository.findAllByContaOrigemId(contaId);
//    }

    public OperacaoResponse salvarDeposito(OperacaoRequest operacaoRequest) {
        if (operacaoRequest.getTipoOperacao().isEmpty())
            throw new ExecaoMensagem("Digite tipo da Operacao");
        if (operacaoRequest.getNumeroContaOrigem() <= 0)
            throw new ExecaoMensagem("Digite Numero da conta origem para operação");
        if (operacaoRequest.getValor() <= 0)
            throw new ExecaoMensagem("Digite 'valor' para realizar deposito");
        if (!operacaoRequest.getTipoOperacao().equals("deposito"))
            throw new ExecaoMensagem("Digite tipo de operacao: 'deposito'");

        if (contaRepository.findByNumeroConta(operacaoRequest.getNumeroContaOrigem()) == null)
            throw new ExecaoMensagem("Numero da conta origem não existe");

        Conta conta = contaRepository.findByNumeroConta(operacaoRequest.getNumeroContaOrigem());
        conta.setSaldo(conta.getSaldo() + operacaoRequest.getValor());
        contaRepository.save(conta);

        Operacao operacao = OperacaoMapper.INSTANCE.toModel(operacaoRequest);
        operacaoRepository.save(operacao);

        OperacaoResponse operacaoResponse = OperacaoMapper.INSTANCE.toDto(operacao);
        operacaoResponse.setStatus("Deposito realizado!");
        return operacaoResponse;
    }

    public OperacaoResponse salvarSaque(OperacaoRequest operacaoRequest) {
        if (operacaoRequest.getValor() <= 0)
            throw new ExecaoMensagem("Digite o valor do saque");
        if (operacaoRequest.getNumeroContaOrigem() <= 0)
            throw new ExecaoMensagem("Digite Numero da conta que deseja relizar operação");
        if (!operacaoRequest.getTipoOperacao().equals("saque"))
            throw new ExecaoMensagem("Digite tipo de operacao: 'saque'");
        if (contaRepository.findByNumeroConta(operacaoRequest.getNumeroContaOrigem()) == null)
            throw new ExecaoMensagem("Numero da conta origem não existe");

        Conta conta = contaRepository.findByNumeroConta(operacaoRequest.getNumeroContaOrigem());
        if (conta.getSaldo() < operacaoRequest.getValor())
            throw new ExecaoMensagem("Saldo insuficiente");

        if (conta.getSaldo() >= operacaoRequest.getValor()) {
            conta.setSaldo(conta.getSaldo() - operacaoRequest.getValor());
            contaRepository.save(conta);
        }

//        ControleContaExterno controleContaExterno = ControleContaExterno.builder().numeroConta(operacaoRequest.getNumeroContaOrigem()).build();
//        new RestTemplate().put("http://localhost:8081/controle", controleContaExterno);

        Operacao operacao = OperacaoMapper.INSTANCE.toModel(operacaoRequest);
        operacaoRepository.save(operacao);
        OperacaoResponse operacaoResponse = OperacaoMapper.INSTANCE.toDto(operacao);
        operacaoResponse.setStatus("Saque realizado!");
        return operacaoResponse;
    }

    public OperacaoResponse salvarTransferencia(OperacaoRequest operacaoRequest) {
        if (operacaoRequest.getValor() <= 0)
            throw new ExecaoMensagem("Digite o valor para a transferencia");
        if (operacaoRequest.getNumeroContaOrigem() <= 0)
            throw new ExecaoMensagem("Digite Numero da conta que deseja relizar operação");
        if (!operacaoRequest.getTipoOperacao().equals("transferencia"))
            throw new ExecaoMensagem("Digite tipo de operacao: 'transferencia'");
        if (operacaoRequest.getNumeroContaOrigem() == operacaoRequest.getNumeroContaDestino())
            throw new ExecaoMensagem("Não pode realizar transferencia para mesma conta");

        if (contaRepository.findByNumeroConta(operacaoRequest.getNumeroContaOrigem()) == null)
            throw new ExecaoMensagem("Numero da conta origem não existe");
        if (contaRepository.findByNumeroConta(operacaoRequest.getNumeroContaDestino()) == null)
            throw new ExecaoMensagem("Numero da conta destino não existe");

        Conta contaOrigem = contaRepository.findByNumeroConta(operacaoRequest.getNumeroContaOrigem());

        if (contaOrigem.getSaldo() < operacaoRequest.getValor()) {
            throw new ExecaoMensagem("Saldo insuficiente da conta origem");
        }

        if (contaOrigem.getSaldo() >= operacaoRequest.getValor()) {
            contaOrigem.setSaldo(contaOrigem.getSaldo() - operacaoRequest.getValor());
            contaRepository.save(contaOrigem);

            Conta contaDestino = contaRepository.findByNumeroConta(operacaoRequest.getNumeroContaDestino());
            contaDestino.setSaldo(contaDestino.getSaldo() + operacaoRequest.getValor());
            contaRepository.save(contaDestino);
        }

        Operacao operacao = OperacaoMapper.INSTANCE.toModel(operacaoRequest);

        OperacaoResponse operacaoResponse = OperacaoMapper.INSTANCE.toDto(operacao);
        operacaoResponse.setStatus("Transferencia realizada!");
        operacaoRepository.save(operacao);
        return operacaoResponse;
    }
}
