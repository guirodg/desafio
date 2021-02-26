package com.desafio.service;

import com.desafio.dto.reqoperacao.OperacaoSaldoDto;
import com.desafio.dto.reqoperacao.OperacaoSaqueDto;
import com.desafio.erros.ExecaoMenssagem;
import com.desafio.mapper.OperacaoMapper;
import com.desafio.model.Operacao;
import com.desafio.repository.OperacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperacaoService {
    private final OperacaoRepository operacaoRepository;

    public List<Operacao> listarTodos() {
        return operacaoRepository.findAll();
    }

    public Operacao salvarSaldo(OperacaoSaldoDto operacaoSaldoDto) {
        if (operacaoSaldoDto.getSaldo() <= 0)
            throw new ExecaoMenssagem("Digite Saldo");
        if (operacaoSaldoDto.getContaOrigem().getId() <= 0)
            throw new ExecaoMenssagem("Digite id da conta que deseja relizar operação");

        Operacao operacao = OperacaoMapper.INSTANCE.toOperacao(operacaoSaldoDto);
        return operacaoRepository.save(operacao);
    }

    public Operacao salvarSaque(OperacaoSaqueDto operacaoSaqueDto) {
        if (operacaoSaqueDto.getSaque() <= 0)
            throw new ExecaoMenssagem("Digite o valor do saque");
        if (operacaoSaqueDto.getContaOrigem().getId() <= 0)
            throw new ExecaoMenssagem("Digite id da conta que deseja relizar operação");

        Operacao operacao = OperacaoMapper.INSTANCE.toOperacao(operacaoSaqueDto);
        return operacaoRepository.save(operacao);
    }

    public Operacao findByIdOrErro(Long id) {
        return operacaoRepository.findById(id)
                .orElseThrow(() -> new ExecaoMenssagem("ID não existe"));
    }

}
