package com.ventas.tienda.dto.pago;

import com.ventas.tienda.dto.pedido.PedidoDto;

import java.time.LocalDateTime;

public record PagoDto(
        Long idPago,
        Double totalPago,
        LocalDateTime fechaPago,
        String metodoPago
) {
}
