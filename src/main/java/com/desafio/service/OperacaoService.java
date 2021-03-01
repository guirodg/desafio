package com.desafio.service;

import com.desafio.dto.reqoperacao.OperacaoPostDto;
import com.desafio.erros.ExecaoMenssagem;
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

    public List<Operacao> listarTodos() {
        return operacaoRepository.findAll();
    }

    public Operacao salvarDeposito(OperacaoPostDto operacaoPostDto) {
        if (operacaoPostDto.getOperacao().isEmpty())
            throw new ExecaoMenssagem("Digite Saldo");
        if (operacaoPostDto.getContaOrigem().getId() <= 0)
            throw new ExecaoMenssagem("Digite id da conta que deseja relizar operação");

        Optional<Conta> conta = contaRepository.findById(operacaoPostDto.getContaOrigem().getId());
        if (conta.isPresent()) {
            conta.get().setSaldo(conta.get().getSaldo() + operacaoPostDto.getValor());
            contaRepository.save(conta.get());

        }

        Operacao operacao = OperacaoMapper.INSTANCE.toOperacao(operacaoPostDto);
        return operacaoRepository.save(operacao);
    }

    public Operacao salvarSaque(OperacaoPostDto operacaoPostDto) {
        if (operacaoPostDto.getValor() <= 0)
            throw new ExecaoMenssagem("Digite o valor do saque");
        if (operacaoPostDto.getContaOrigem().getId() <= 0)
            throw new ExecaoMenssagem("Digite id da conta que deseja relizar operação");

        Optional<Conta> conta = contaRepository.findById(operacaoPostDto.getContaOrigem().getId());
        if (conta.isPresent()) {
            if (conta.get().getSaldo() >= operacaoPostDto.getValor()) {
                conta.get().setSaldo(conta.get().getSaldo() - operacaoPostDto.getValor());
                contaRepository.save(conta.get());
            }
        }

        Operacao operacao = OperacaoMapper.INSTANCE.toOperacao(operacaoPostDto);
        return operacaoRepository.save(operacao);
    }

    public Operacao salvarTransferencia(OperacaoPostDto operacaoPostDto) {
        if (operacaoPostDto.getValor() <=0)
            throw new ExecaoMenssagem("Digite o valor para a transferencia");
        if (operacaoPostDto.getContaOrigem().getId() <= 0)
            throw new ExecaoMenssagem("Digite id da conta origem");
        if (operacaoPostDto.getContaDestino().getId() <= 0)
            throw new ExecaoMenssagem("Digite id da conta destino");

        Optional<Conta> contaOrigem = contaRepository.findById(operacaoPostDto.getContaOrigem().getId());
        if (contaOrigem.get().getSaldo() >= operacaoPostDto.getValor()){ // saldo da conta > valor post
            contaOrigem.get().setSaldo(contaOrigem.get().getSaldo() - operacaoPostDto.getValor());

            Optional<Conta> contaDestino = contaRepository.findById(operacaoPostDto.getContaDestino().getId());
            contaDestino.get().setSaldo(contaDestino.get().getSaldo() + operacaoPostDto.getValor());
            contaRepository.save(contaDestino.get());
            contaRepository.save(contaOrigem.get());
        }
        Operacao operacao = OperacaoMapper.INSTANCE.toOperacao(operacaoPostDto);
        return operacaoRepository.save(operacao);
    }

    public Operacao findByIdOrErro(Long id) {
        return operacaoRepository.findById(id)
                .orElseThrow(() -> new ExecaoMenssagem("ID não existe"));
    }

}
