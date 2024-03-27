package com.ventas.tienda.dto.detalleEnvio;

import com.ventas.tienda.Entities.Pedido;

public record DetalleEnvioToSaveDto(
        Long idDetallePedido,
        String direccionEnvio,
        String transportadoraEnvio,
        String numeroGuiaEnvio,
        Pedido pedido
) {
}
