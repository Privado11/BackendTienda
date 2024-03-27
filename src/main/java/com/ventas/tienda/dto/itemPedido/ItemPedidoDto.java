package com.ventas.tienda.dto.itemPedido;


import com.ventas.tienda.dto.pedido.PedidoDto;
import com.ventas.tienda.dto.producto.ProductoDto;

public record ItemPedidoDto(
        Long idItemPedido,
        Integer cantidadItem,
        Double precio,
        PedidoDto pedido,
        ProductoDto producto
) {
}
