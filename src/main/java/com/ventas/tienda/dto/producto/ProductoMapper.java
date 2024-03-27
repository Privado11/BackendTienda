package com.ventas.tienda.dto.producto;

import com.ventas.tienda.Entities.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    Producto toEntity(ProductoDto productoDto);

    Producto productoToSaveDtoToEntity(ProductoSaveDto productoSaveDto);

    ProductoDto toDto(Producto producto);

}
