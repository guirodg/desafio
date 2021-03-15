package com.desafio.mapper;

import com.desafio.dto.clienterequest.ClienteRequestPost;
import com.desafio.dto.clienterequest.ClienteRequestPut;
import com.desafio.dto.clienteresponse.ClienteResponsePost;
import com.desafio.dto.clienteresponse.ClienteResponsePut;
import com.desafio.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ClienteMapper {
    public static final ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    public abstract Cliente toModelPost(ClienteRequestPost clienteRequestPost);

    public abstract Cliente toModelPut(ClienteRequestPut clienteRequestPut);

    public abstract ClienteResponsePost toDtoPost(Cliente cliente);

    public abstract ClienteResponsePut toDtoPut(Cliente cliente);

}
