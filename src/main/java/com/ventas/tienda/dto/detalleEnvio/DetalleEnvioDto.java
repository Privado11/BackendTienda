package com.ventas.tienda.dto.detalleEnvio;

import com.ventas.tienda.dto.pedido.PedidoDto;

public record DetalleEnvioDto(
        Long idDetalleEnvio,
        String direccionEnvio,
        String transportadoraEnvio,
        String numeroGuiaEnvio
) {
}
