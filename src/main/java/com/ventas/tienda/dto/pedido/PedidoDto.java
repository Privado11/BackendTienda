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
        //ClienteDto cliente,
        List<ItemPedidoDto> itemsPedido,

        DetalleEnvioDto detalleEnvio,

        PagoDto pago
) {
    public List<ItemPedidoDto> itemPedidos(){
        if(itemsPedido == null){
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(itemsPedido);
    }
}
