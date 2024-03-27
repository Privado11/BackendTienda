package com.ventas.tienda.dto.pedido;

import com.ventas.tienda.Entities.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    Pedido toEntity(PedidoDto pedidoDto);

    Pedido pedidoToSaveDtoToEntity(PedidoToSaveDto pedidoToSaveDto);

    PedidoDto toDto(Pedido pedido);
}
