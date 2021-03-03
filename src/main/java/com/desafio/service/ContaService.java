package com.desafio.service;

import com.desafio.dto.reqconta.ContaPostDto;
import com.desafio.dto.reqconta.ContaPutDto;
import com.desafio.erros.ExecaoMensagem;
import com.desafio.externo.ControleConta;
import com.desafio.mapper.ContaMapper;
import com.desafio.model.Conta;
import com.desafio.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContaService {
    private final ContaRepository contaRepository;

    public Conta salvar(ContaPostDto contaPostDto) {
        if (contaPostDto.getNumeroConta() <= 0 ||
                contaPostDto.getDigitoVerificador() < 0 ||
                contaPostDto.getTipoConta().isEmpty() ||
                contaPostDto.getCliente().getId() <= 0) {
            throw new ExecaoMensagem("Preencha todos os campos");
        }

        Conta conta = ContaMapper.INSTANCE.toConta(contaPostDto);
        conta.setSaldo(0);

        int limeteSaque = 5;
        if (contaPostDto.getTipoConta().equals("pessoa fisica"))
            limeteSaque = 5;
        if (contaPostDto.getTipoConta().equals("pessoa juridica"))
            limeteSaque = 50;
        if (contaPostDto.getTipoConta().equals("governamental"))
            limeteSaque = 250;

        Conta contaSalva = contaRepository.save(conta);

        ControleConta controleConta = ControleConta.builder().idConta(contaSalva.getId()).limeteSaque(limeteSaque).build();
        new RestTemplate().postForEntity("http://localhost:8081/controle", controleConta, Conta.class);

        return contaSalva;
    }

    public Conta atualizar(ContaPutDto contaPutDto) {
        if (contaPutDto.getNumeroConta() <= 0 ||
                contaPutDto.getDigitoVerificador() < 0 ||
                contaPutDto.getTipoConta().isEmpty() ||
                contaPutDto.getCliente().getId() <= 0) {
            throw new ExecaoMensagem("Preencha todos os campos");
        }
        Conta contaSalva = encontreIdOuErro(contaPutDto.getId());
        Conta conta = ContaMapper.INSTANCE.toConta(contaPutDto);
        conta.setId(contaSalva.getId());
        return contaRepository.save(conta);
    }

    public void deletar(Long id) {
        contaRepository.delete(encontreIdOuErro(id));
    }

    public Conta encontreIdOuErro(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new ExecaoMensagem("ID não existe"));
    }
}
