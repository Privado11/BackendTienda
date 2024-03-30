package com.ventas.tienda.dto.cliente;

import com.ventas.tienda.dto.pedido.PedidoDto;

import java.util.Collections;
import java.util.List;

public record ClienteDto(
        Long idCliente,
        String nombreCliente,
        String emailCliente,
        String direccionCliente,
        List<PedidoDto> pedidosCliente
) {

    public List<PedidoDto> pedidosClientes(){
        if (pedidosCliente() == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(pedidosCliente);
    }
}
