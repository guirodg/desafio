package com.desafio.service;

import com.desafio.dto.reqconta.ContaPostDto;
import com.desafio.dto.reqconta.ContaPutDto;
import com.desafio.erros.ExecaoMenssagem;
import com.desafio.mapper.ContaMapper;
import com.desafio.model.Conta;
import com.desafio.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;

    public List<Conta> listarTodos() {
        return contaRepository.findAll();
    }

    public Conta salvar(ContaPostDto contaPostDto) {
        if (contaPostDto.getNumeroConta() <= 0 ||
                contaPostDto.getDigitoVerificador() < 0 ||
                contaPostDto.getTipoConta().isEmpty() ||
                contaPostDto.getCliente().getId() <= 0) {
            throw new ExecaoMenssagem("Preencha todos os campos");
        }
        Conta conta = ContaMapper.INSTANCE.toConta(contaPostDto);
        conta.setSaldo(0);
        return contaRepository.save(conta);
    }

    public Conta atualizar(ContaPutDto contaPutDto) {
        if (contaPutDto.getNumeroConta() <= 0 ||
                contaPutDto.getDigitoVerificador() < 0 ||
                contaPutDto.getTipoConta().isEmpty() ||
                contaPutDto.getCliente().getId() <= 0) {
            throw new ExecaoMenssagem("Preencha todos os campos");
        }
        Conta contaSalva = findByIdOrErro(contaPutDto.getId());
        Conta conta = ContaMapper.INSTANCE.toConta(contaPutDto);
        conta.setId(contaSalva.getId());
        return contaRepository.save(conta);
    }

    public void deletar(Long id) {
        contaRepository.delete(findByIdOrErro(id));
    }

    public Conta findByIdOrErro(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new ExecaoMenssagem("ID não existe"));
    }
}
