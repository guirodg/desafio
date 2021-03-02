package com.desafio.service;

import com.desafio.dto.reqoperacao.OperacaoPostDto;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.mapper.OperacaoMapper;
import com.desafio.model.Conta;
import com.desafio.model.Operacao;
import com.desafio.repository.ContaRepository;
import com.desafio.repository.OperacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperacaoService {
    private final OperacaoRepository operacaoRepository;
    private final ContaRepository contaRepository;

    public List<Operacao> listarExtrato(Long contaId) {
        return operacaoRepository.findAllByContaOrigemId(contaId);
    }

    public Operacao salvarDeposito(OperacaoPostDto operacaoPostDto) {
        if (operacaoPostDto.getOperacao().isEmpty())
            throw new ExecaoMensagem("Digite Saldo");
        if (operacaoPostDto.getContaOrigem().getId() <= 0)
            throw new ExecaoMensagem("Digite id da conta que deseja relizar operação");
        if (operacaoPostDto.getValor() <= 0)
            throw new ExecaoMensagem("Digite 'valor' para realizar deposito");

        Optional<Conta> conta = contaRepository.findById(operacaoPostDto.getContaOrigem().getId());
        if (!conta.isPresent())
            throw new ExecaoMensagem("Id da contaDestino não existe");
        if (conta.isPresent()) {
            conta.get().setSaldo(conta.get().getSaldo() + operacaoPostDto.getValor());
            contaRepository.save(conta.get());
        }
        Operacao operacao = OperacaoMapper.INSTANCE.toOperacao(operacaoPostDto);
        operacao.setContaOrigem(operacaoPostDto.getContaOrigem());
        return operacaoRepository.save(operacao);
    }

    public Operacao salvarSaque(OperacaoPostDto operacaoPostDto) {
        if (operacaoPostDto.getValor() <= 0)
            throw new ExecaoMensagem("Digite o valor do saque");
        if (operacaoPostDto.getContaOrigem().getId() <= 0)
            throw new ExecaoMensagem("Digite id da conta que deseja relizar operação");
        if (operacaoPostDto.getValor() <= 0)
            throw new ExecaoMensagem("Digite 'valor' para realizar saque");

        Optional<Conta> conta = contaRepository.findById(operacaoPostDto.getContaOrigem().getId());
        if (!conta.isPresent())
            throw new ExecaoMensagem("Id da contaDestino não existe");
        if (conta.get().getSaldo() < operacaoPostDto.getValor())
            throw new ExecaoMensagem("Saldo insuficiente");

        if (conta.isPresent()) {
            conta.get().setLimiteSaque(conta.get().getLimiteSaque() - 1);
            if (conta.get().getSaldo() >= operacaoPostDto.getValor()) {
                conta.get().setSaldo(conta.get().getSaldo() - operacaoPostDto.getValor());
                contaRepository.save(conta.get());
            }
        }
        if (conta.get().getLimiteSaque() <= 0 && conta.get().getTipoConta().equals("pessoa fisica"))
            conta.get().setSaldo(conta.get().getSaldo() - 10);

        Operacao operacao = OperacaoMapper.INSTANCE.toOperacao(operacaoPostDto);
        operacao.setContaOrigem(operacaoPostDto.getContaOrigem());
        return operacaoRepository.save(operacao);
    }

    public Operacao salvarTransferencia(OperacaoPostDto operacaoPostDto) {
        if (operacaoPostDto.getValor() <= 0)
            throw new ExecaoMensagem("Digite o valor para a transferencia");
        if (operacaoPostDto.getContaOrigem().getId() <= 0)
            throw new ExecaoMensagem("Digite id da conta origem");
        if (operacaoPostDto.getContaOrigem().getId() <= 0)
            throw new ExecaoMensagem("Digite id da conta destino");

        Optional<Conta> contaOrigem = contaRepository.findById(operacaoPostDto.getContaOrigem().getId());
        if (!contaOrigem.isPresent())
            throw new ExecaoMensagem("Id da contaOrigem não existe");
        if (contaOrigem.get().getSaldo() < operacaoPostDto.getValor())
            throw new ExecaoMensagem("Saldo insuficiente da conta origem");
        if (contaOrigem.get().getSaldo() >= operacaoPostDto.getValor()) {
            contaOrigem.get().setSaldo(contaOrigem.get().getSaldo() - operacaoPostDto.getValor());

            Optional<Conta> contaDestino = contaRepository.findById(operacaoPostDto.getContaOrigem().getId());
            if (!contaDestino.isPresent())
                throw new ExecaoMensagem("Id da contaDestino não existe");
            contaDestino.get().setSaldo(contaDestino.get().getSaldo() + operacaoPostDto.getValor());
            contaRepository.save(contaDestino.get());
            contaRepository.save(contaOrigem.get());
        }
        Operacao operacao = OperacaoMapper.INSTANCE.toOperacao(operacaoPostDto);
        operacao.setContaOrigem(operacaoPostDto.getContaOrigem());
        operacao.setContaOrigem(operacaoPostDto.getContaOrigem());
        return operacaoRepository.save(operacao);
    }
}
