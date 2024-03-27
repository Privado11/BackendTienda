package com.ventas.tienda.dto.detalleEnvio;

import com.ventas.tienda.Entities.DetalleEnvio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DetalleEnvioMapper {

    DetalleEnvioMapper INSTANCE = Mappers.getMapper(DetalleEnvioMapper.class);

    DetalleEnvio toEntity(DetalleEnvioDto pagoDto);
    DetalleEnvio DetalleEnvioToSaveDtoToEntity(DetalleEnvioToSaveDto detalleEnvioToSaveDto);

    DetalleEnvioDto toDto(DetalleEnvio detalleEnvio);
}
