package com.ventas.tienda.dto.pago;

import com.ventas.tienda.Entities.Pedido;

import java.time.LocalDateTime;

public record PagoToSaveDto(
        Long idPago,
        Double totalPago,
        LocalDateTime fechaPago,
        String metodoPago,
        Pedido pedido
) {
}
