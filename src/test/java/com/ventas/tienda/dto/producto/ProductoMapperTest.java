package com.ventas.tienda.dto.producto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ventas.tienda.Entities.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductoMapperTest {


    @Autowired
    ProductoMapper productoMapper;

    @Test
    void toEntity() {
        ProductoDto productoDto = new ProductoDto(1L, "Producto 1", 10.0, 100);
        Producto producto = productoMapper.toEntity(productoDto);

        assertThat(producto).isNotNull();
        assertThat(productoDto.idProducto()).isEqualTo(producto.getIdProducto());
        assertThat(productoDto.nombreProducto()).isEqualTo(producto.getNombreProducto());
        assertThat(productoDto.precioProducto()).isEqualTo(producto.getPrecioProducto());
        assertThat(productoDto.stockProducto()).isEqualTo(producto.getStockProducto());
    }

    @Test
    void productoToSaveDtoToEntity() {
        ProductoSaveDto productoSaveDto = new ProductoSaveDto(null, "Nuevo Producto", 20.0, 50);
        Producto producto = productoMapper.productoToSaveDtoToEntity(productoSaveDto);

        assertThat(producto).isNotNull();
        assertThat(productoSaveDto.nombreProducto()).isEqualTo(producto.getNombreProducto());
        assertThat(productoSaveDto.precioProducto()).isEqualTo(producto.getPrecioProducto());
        assertThat(productoSaveDto.stockProducto()).isEqualTo(producto.getStockProducto());
    }

    @Test
    void toDto() {
        Producto producto = new Producto(1L, "Producto 1", 10.0, 100, Collections.emptyList());
        ProductoDto productoDto = productoMapper.toDto(producto);

        assertThat(productoDto).isNotNull();
        assertThat(producto.getIdProducto()).isEqualTo(productoDto.idProducto());
        assertThat(producto.getNombreProducto()).isEqualTo(productoDto.nombreProducto());
        assertThat(producto.getPrecioProducto()).isEqualTo(productoDto.precioProducto());
        assertThat(producto.getStockProducto()).isEqualTo(productoDto.stockProducto());

    }
}