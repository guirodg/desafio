package com.desafio.mapper;

import com.desafio.dto.clienterequest.ClienteRequest;
import com.desafio.dto.clienteresponse.ClienteResponse;
import com.desafio.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ClienteMapper {
    public static final ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    public abstract Cliente toCliente(ClienteRequest clienteRequest);

    public abstract ClienteResponse toModel(Cliente cliente);

    public abstract Cliente toCliente(ClientePutDto clientePutDto);
}
