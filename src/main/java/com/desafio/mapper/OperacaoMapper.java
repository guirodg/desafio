package com.desafio.mapper;

import com.desafio.dto.operacaorequest.OperacaoRequest;
import com.desafio.dto.operacaoresponse.OperacaoResponse;
import com.desafio.model.Operacao;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class OperacaoMapper {
    public static final OperacaoMapper INSTANCE = Mappers.getMapper(OperacaoMapper.class);

    public abstract Operacao toModel(OperacaoRequest operacaoRequest);

    public abstract OperacaoResponse toDto(Operacao operacao);

}
