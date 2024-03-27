package com.ventas.tienda.dto.cliente;

import com.ventas.tienda.Entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Cliente toEntity(ClienteDto clienteDto);
    Cliente clienteToSaveDtotoEntity(ClienteToSaveDto clienteToSaveDto);

    ClienteDto toDto(Cliente cliente);
}
