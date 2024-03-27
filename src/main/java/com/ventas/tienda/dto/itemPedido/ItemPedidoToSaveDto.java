package com.ventas.tienda.dto.itemPedido;

import com.ventas.tienda.Entities.Pedido;
import com.ventas.tienda.Entities.Producto;

public record ItemPedidoToSaveDto(
        Long idItemPedido,
        Integer cantidadItem,
        Double precio,
        Pedido pedido,
        Producto producto

) {
}
