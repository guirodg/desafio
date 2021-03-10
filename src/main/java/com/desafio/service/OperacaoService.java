package com.desafio.service;

import com.desafio.dto.reqoperacao.OperacaoPostDto;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.externo.ControleContaExterno;
import com.desafio.mapper.OperacaoMapper;
import com.desafio.model.Conta;
import com.desafio.model.Operacao;
import com.desafio.repository.ContaRepository;
import com.desafio.repository.OperacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        if (operacaoPostDto.getTipoOperacao().isEmpty())
            throw new ExecaoMensagem("Digite tipo da Operacao");
        if (operacaoPostDto.getContaOrigem().getId() <= 0)
            throw new ExecaoMensagem("Digite id da conta que deseja relizar operação");
        if (operacaoPostDto.getValor() <= 0)
            throw new ExecaoMensagem("Digite 'valor' para realizar deposito");
        if (!operacaoPostDto.getTipoOperacao().equals("deposito"))
            throw new ExecaoMensagem("Digite tipo de operacao: 'deposito'");


        Optional<Conta> conta = contaRepository.findById(operacaoPostDto.getContaOrigem().getId());
        if (!conta.isPresent())
            throw new ExecaoMensagem("Id da contaOrigem não existe");
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
        if (!operacaoPostDto.getTipoOperacao().equals("saque"))
            throw new ExecaoMensagem("Digite tipo de operacao: 'saque'");


        Optional<Conta> conta = contaRepository.findById(operacaoPostDto.getContaOrigem().getId());
        if (!conta.isPresent())
            throw new ExecaoMensagem("Id da contaOrigem não existe");
        if (conta.get().getSaldo() < operacaoPostDto.getValor())
            throw new ExecaoMensagem("Saldo insuficiente");

        if (conta.get().getSaldo() >= operacaoPostDto.getValor()) {
            conta.get().setSaldo(conta.get().getSaldo() - operacaoPostDto.getValor());
            contaRepository.save(conta.get());
        }

        ControleContaExterno controleContaExterno = ControleContaExterno.builder().idConta(operacaoPostDto.getContaOrigem().getId()).build();
        new RestTemplate().put("http://localhost:8081/controle", controleContaExterno);

        Operacao operacao = OperacaoMapper.INSTANCE.toOperacao(operacaoPostDto);
        operacao.setContaOrigem(operacaoPostDto.getContaOrigem());
        return operacaoRepository.save(operacao);
    }

    public Operacao salvarTransferencia(OperacaoPostDto operacaoPostDto) {
        if (operacaoPostDto.getValor() <= 0)
            throw new ExecaoMensagem("Digite o valor para a transferencia");
        if (operacaoPostDto.getContaOrigem().getId() <= 0)
            throw new ExecaoMensagem("Digite id da conta origem");
        if (!operacaoPostDto.getTipoOperacao().equals("transferencia"))
            throw new ExecaoMensagem("Digite tipo de operacao: 'transferencia'");
        if (!contaRepository.findById(operacaoPostDto.getContaDestino().getId()).isPresent())
            throw new ExecaoMensagem("ID da contaDestino informada não existe");

        Optional<Conta> contaOrigem = contaRepository.findById(operacaoPostDto.getContaOrigem().getId());
        if (!contaOrigem.isPresent())
            throw new ExecaoMensagem("Id da contaOrigem não existe");
        if (contaOrigem.get().getSaldo() < operacaoPostDto.getValor())
            throw new ExecaoMensagem("Saldo insuficiente da conta origem");
        if (contaOrigem.get().getSaldo() >= operacaoPostDto.getValor()) {
            contaOrigem.get().setSaldo(contaOrigem.get().getSaldo() - operacaoPostDto.getValor());
            contaRepository.save(contaOrigem.get());

            Optional<Conta> contaDestino = contaRepository.findById(operacaoPostDto.getContaDestino().getId());
            if (!contaDestino.isPresent())
                throw new ExecaoMensagem("Id da contaDestino não existe");
            contaDestino.get().setSaldo(contaDestino.get().getSaldo() + operacaoPostDto.getValor());
            contaRepository.save(contaDestino.get());
        }

        Operacao operacao = OperacaoMapper.INSTANCE.toOperacao(operacaoPostDto);
        operacao.setContaOrigem(operacaoPostDto.getContaOrigem());
        operacao.setContaOrigem(operacaoPostDto.getContaOrigem());
        return operacaoRepository.save(operacao);
    }
}
