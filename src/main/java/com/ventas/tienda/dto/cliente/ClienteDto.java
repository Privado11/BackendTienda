package com.ventas.tienda.dto.cliente;

import com.ventas.tienda.dto.pedido.PedidoDto;

import java.util.Collections;
import java.util.List;

public record ClienteDto(
        Long idCliente,
        String nombreCliente,
        String emailCliente,
        String direccionCLiente,
        List<PedidoDto> pedidos
) {

    public List<PedidoDto> pedidos(){
        return Collections.unmodifiableList(pedidos);
    }
}
