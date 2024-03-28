package com.ventas.tienda.dto.producto;

import com.ventas.tienda.dto.pedido.PedidoDto;

import java.util.Collections;
import java.util.List;

public record ProductoDto(
        Long idProducto,
        String nombreProducto,
        Double precioProducto,
        Integer stockProducto,
        List<PedidoDto> pedidos
) {

    public List<PedidoDto> pedidos(){
        if(pedidos == null){
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(pedidos);
    }
}
