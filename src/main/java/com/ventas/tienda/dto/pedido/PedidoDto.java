package com.ventas.tienda.dto.pedido;

import com.ventas.tienda.dto.cliente.ClienteDto;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record PedidoDto(
        Long idPedido,
        LocalDateTime fechaPedido,
        ClienteDto cliente,
        com.ventas.tienda.dto.pago.PagoDto pago,
        DetalleEnvioDto detalleEnvio,
        List<com.ventas.tienda.dto.itemPedido.ItemPedidoDto> itemPedidos
) {
    public List<com.ventas.tienda.dto.itemPedido.ItemPedidoDto> itemPedidos(){
        return Collections.unmodifiableList(itemPedidos);
    }
}
