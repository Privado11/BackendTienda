package com.ventas.tienda.dto.itemPedido;

import com.ventas.tienda.Entities.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedidoMapper INSTANCE = Mappers.getMapper(ItemPedidoMapper.class);

    ItemPedido toEntity(ItemPedidoDto itemPedidoDto);

    ItemPedido itemPedidoToSaveDtoToEntity(ItemPedidoToSaveDto itemPedidoToSaveDto);

    ItemPedidoDto toDto(ItemPedido itemPedido);
}
