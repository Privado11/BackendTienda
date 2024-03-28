package com.ventas.tienda.dto.producto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ventas.tienda.Entities.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductoMapperTest {


    @Autowired
    ProductoMapper productoMapper;



    Producto producto = Producto.builder()
            .nombreProducto("Laptop Hp Victus")
            .precioProducto(2000.0)
            .stockProducto(20)
            .build();


    Producto producto2 = Producto.builder()
            .nombreProducto("Teclado Mecanico")
            .precioProducto(300.0)
            .stockProducto(15)
            .build();

    @Test
    void toEntity() {
    }

    @Test
    void productoToSaveDtoToEntity() {
    }

    @Test
    void toDto() {
        ProductoDto p = productoMapper.toDto(producto);
        assertThat(p.nombreProducto()).isEqualTo("Laptop Hp Victus");

    }
}