package com.ventas.tienda.dto.pedido;

import static org.assertj.core.api.Assertions.assertThat;

import com.ventas.tienda.Entities.Pedido;
import com.ventas.tienda.Enum.StatusPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PedidoMapperTest {

    @Autowired
    PedidoMapper pedidoMapper;


    @Test
    void toEntity() {
        PedidoDto pedidoDto = new PedidoDto(1L, LocalDateTime.now(), "ENVIADO", null, null, null);
        Pedido pedido = pedidoMapper.toEntity(pedidoDto);

        assertThat(pedido).isNotNull();
        assertThat(pedidoDto.idPedido()).isEqualTo(pedido.getIdPedido());
        assertThat(pedidoDto.fechaPedido()).isEqualTo(pedido.getFechaPedido());
    }

    @Test
    void pedidoToSaveDtoToEntity() {
        PedidoToSaveDto pedidoToSaveDto = new PedidoToSaveDto(1L, LocalDateTime.now(), null, null);
        Pedido pedido = pedidoMapper.pedidoToSaveDtoToEntity(pedidoToSaveDto);

        assertThat(pedido).isNotNull();
        assertThat(pedidoToSaveDto.idPedido()).isEqualTo(pedido.getIdPedido());
        assertThat(pedidoToSaveDto.fechaPedido()).isEqualTo(pedido.getFechaPedido());
    }

    @Test
    void toDto() {
        Pedido pedido = new Pedido(1L, LocalDateTime.now(), StatusPedido.ENTREGADO , null, null, null, null);
        PedidoDto pedidoDto = pedidoMapper.toDto(pedido);

        assertThat(pedidoDto).isNotNull();
        assertThat(pedidoDto.idPedido()).isEqualTo(pedido.getIdPedido());
        assertThat(pedidoDto.fechaPedido()).isEqualTo(pedido.getFechaPedido());
    }
}