package com.desafio.mapper;

import com.desafio.dto.contarequest.ContaPutDtoDesconto;
import com.desafio.dto.contarequest.ContaRequest;
import com.desafio.dto.contaresponse.ContaResponse;
import com.desafio.model.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ContaMapper {
    public static final ContaMapper INSTANCE = Mappers.getMapper(ContaMapper.class);

    public abstract Conta toModel(ContaRequest contaRequest);


    public abstract Conta toModel(ContaPutDtoDesconto contaPutDtoDesconto);

    public abstract ContaResponse toDTO(Conta conta);

}
