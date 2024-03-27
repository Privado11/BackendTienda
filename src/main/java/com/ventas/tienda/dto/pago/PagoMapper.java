package com.ventas.tienda.dto.pago;

import com.ventas.tienda.Entities.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    PagoMapper INSTANCE = Mappers.getMapper(PagoMapper.class);

    Pago toEntity(PagoDto pagoDto);
    Pago pagoTosaveDtoToEntity(PagoToSaveDto pagoToSaveDto);

    PagoDto toDto(Pago pago);

}
