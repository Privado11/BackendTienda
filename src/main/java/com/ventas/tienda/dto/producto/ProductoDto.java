package com.ventas.tienda.dto.producto;

import com.ventas.tienda.dto.itemPedido.ItemPedidoDto;
import com.ventas.tienda.dto.pedido.PedidoDto;

import java.util.Collections;
import java.util.List;

public record ProductoDto(
        Long idProducto,
        String nombreProducto,
        Double precioProducto,
        Integer stockProducto
        //List<ItemPedidoDto> itemsPedidos
) {

    /*public List<ItemPedidoDto> itemPedidos(){
        if(itemsPedidos == null){
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(itemsPedidos);
    }*/
}
