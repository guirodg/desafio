package com.desafio.service;

import com.desafio.dto.reqconta.ContaPostDto;
import com.desafio.mapper.ContaMapper;
import com.desafio.model.Conta;
import com.desafio.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;

    public Conta save(ContaPostDto contaPostDto){
        Conta conta = ContaMapper.INSTANCE.toConta(contaPostDto);
        return contaRepository.save(conta);
    }


}
