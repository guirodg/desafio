package com.desafio.service;

import com.desafio.dto.reqoperacao.OperacaoPostDto;
import com.desafio.erros.ExecaoMenssagem;
import com.desafio.mapper.OperacaoMapper;
import com.desafio.model.Operacao;
import com.desafio.repository.OperacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperacaoService {
    private final OperacaoRepository operacaoRepository;

    public List<Operacao> listarTodos() {
        return operacaoRepository.findAll();
    }

    public Operacao salvarSaldo(OperacaoPostDto operacaoPostDto) {
        if (operacaoPostDto.getSaldo() <= 0)
            throw new ExecaoMenssagem("Digite Saldo");
        if (operacaoPostDto.getConta().getId() <= 0)
            throw new ExecaoMenssagem("Digite id da conta que deseja relizar operação");

        Operacao operacao = OperacaoMapper.INSTANCE.toOperacao(operacaoPostDto);
        return operacaoRepository.save(operacao);
    }

    public Operacao salvarSaque(OperacaoPostDto operacaoPostDto) {
        if (operacaoPostDto.getSaque() <= 0)
            throw new ExecaoMenssagem("Digite o valor do saque");
        if (operacaoPostDto.getConta().getId() <= 0)
            throw new ExecaoMenssagem("Digite id da conta que deseja relizar operação");

        Operacao operacao = OperacaoMapper.INSTANCE.toOperacao(operacaoPostDto);
        return operacaoRepository.save(operacao);
    }


    public Operacao findByIdOrErro(Long id) {
        return operacaoRepository.findById(id)
                .orElseThrow(() -> new ExecaoMenssagem("ID não existe"));
    }

}
