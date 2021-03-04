package com.desafio.mapper;

import com.desafio.dto.reqconta.ContaPutDtoDesconto;
import com.desafio.dto.reqconta.ContaPostDto;
import com.desafio.dto.reqconta.ContaPutDto;
import com.desafio.model.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ContaMapper {
    public static final ContaMapper INSTANCE = Mappers.getMapper(ContaMapper.class);

    public abstract Conta toConta(ContaPostDto contaPostDto);

    public abstract Conta toConta(ContaPutDto contaPutDto);

    public abstract Conta toConta(ContaPutDtoDesconto contaPutDtoDesconto);

}
