package com.ventas.tienda.dto.detalleEnvio;

import com.ventas.tienda.dto.pedido.PedidoDto;

public record DetalleEnvioDto(
        Long idDetallePedido,
        String direccionEnvio,
        String transportadoraEnvio,
        String numeroGuiaEnvio,
        PedidoDto pedido
) {
}
