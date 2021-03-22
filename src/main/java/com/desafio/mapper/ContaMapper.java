package com.desafio.mapper;

import com.desafio.dto.contarequest.ContaRequest;
import com.desafio.dto.contarequest.ContaRequestDesconto;
import com.desafio.dto.contaresponse.ContaResponse;
import com.desafio.dto.contaresponse.ContaResponseDesconto;
import com.desafio.model.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ContaMapper {
    public static final ContaMapper INSTANCE = Mappers.getMapper(ContaMapper.class);

    public abstract Conta toModel(ContaRequest contaRequest);

    public abstract Conta toModel(ContaRequestDesconto contaRequestDesconto);

    public abstract ContaResponse toDTO(Conta conta);

    public abstract ContaResponseDesconto toDTODesconto(Conta conta);

}
