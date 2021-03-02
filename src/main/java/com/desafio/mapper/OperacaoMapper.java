package com.desafio.mapper;

import com.desafio.dto.reqoperacao.OperacaoPostDto;
import com.desafio.model.Operacao;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class OperacaoMapper {
    public static final OperacaoMapper INSTANCE = Mappers.getMapper(OperacaoMapper.class);

    public abstract Operacao toOperacao(OperacaoPostDto operacaoPostDto);

}
