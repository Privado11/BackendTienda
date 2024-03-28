package com.ventas.tienda.dto.pedido;

import com.ventas.tienda.dto.cliente.ClienteDto;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioDto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoDto;
import com.ventas.tienda.dto.pago.PagoDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record PedidoDto(
        Long idPedido,
        LocalDateTime fechaPedido,
        String status,

        ClienteDto cliente,
        PagoDto pago,
        DetalleEnvioDto detalleEnvio,
        List<ItemPedidoDto> itemPedidos
) {
    public List<ItemPedidoDto> itemPedidos(){
        if(itemPedidos == null){
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(itemPedidos);
    }
}
