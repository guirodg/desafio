package com.desafio.mapper;

import com.desafio.dto.ClientePostDto;
import com.desafio.dto.ClientePutDto;
import com.desafio.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ClienteMapper {
    public static final ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    public abstract Cliente toCliente(ClientePostDto clientePostDto);

    public abstract Cliente toCliente(ClientePutDto clientePutDto);
}
