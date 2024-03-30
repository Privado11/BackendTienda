package com.ventas.tienda.dto.pago;

import com.ventas.tienda.Entities.Pedido;
import com.ventas.tienda.Enum.MetodoPago;

import java.time.LocalDateTime;

public record PagoToSaveDto(
        Long idPago,
        Double totalPago,
        LocalDateTime fechaPago,
        MetodoPago metodoPago,
        Pedido pedido
) {
}
