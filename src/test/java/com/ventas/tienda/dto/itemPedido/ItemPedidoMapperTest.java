package com.ventas.tienda.dto.itemPedido;

import static org.assertj.core.api.Assertions.assertThat;

import com.ventas.tienda.Entities.ItemPedido;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ItemPedidoMapperTest {

    @Autowired
    ItemPedidoMapper itemPedidoMapper;


    @Test
    void toEntity() {
        ItemPedidoDto itemPedidoDto = new ItemPedidoDto(1L, 2, 10.0, null);
        ItemPedido itemPedido = itemPedidoMapper.toEntity(itemPedidoDto);

        assertThat(itemPedido).isNotNull();
        assertThat(itemPedidoDto.idItemPedido()).isEqualTo(itemPedido.getIdItemPedido());
        assertThat(itemPedidoDto.cantidadItem()).isEqualTo(itemPedido.getCantidadItem());
        assertThat(itemPedidoDto.precio()).isEqualTo(itemPedido.getPrecio());
    }

    @Test
    void itemPedidoToSaveDtoToEntity() {
        ItemPedidoToSaveDto itemPedidoToSaveDto = new ItemPedidoToSaveDto(1l, 20, 30.0, null, null);

        ItemPedido itemPedido = itemPedidoMapper.itemPedidoToSaveDtoToEntity(itemPedidoToSaveDto);

        assertThat(itemPedido).isNotNull();
        assertThat(itemPedidoToSaveDto.idItemPedido()).isEqualTo(itemPedido.getIdItemPedido());
        assertThat(itemPedidoToSaveDto.cantidadItem()).isEqualTo(itemPedido.getCantidadItem());
        assertThat(itemPedidoToSaveDto.precio()).isEqualTo(itemPedido.getPrecio());
    }

    @Test
    void toDto() {
        ItemPedido itemPedido = new ItemPedido(1L, 2, 10.0, null, null);
        ItemPedidoDto itemPedidoDto = itemPedidoMapper.toDto(itemPedido);

        assertThat(itemPedidoDto).isNotNull();
        assertThat(itemPedido.getIdItemPedido()).isEqualTo(itemPedidoDto.idItemPedido());
        assertThat(itemPedido.getCantidadItem()).isEqualTo(itemPedidoDto.cantidadItem());
        assertThat(itemPedido.getPrecio()).isEqualTo(itemPedidoDto.precio());
    }
}