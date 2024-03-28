package com.ventas.tienda.dto.cliente;

public record ClienteToSaveDto(
        Long idCliente,
        String nombreCliente,
        String emailCliente,
        String direccionCliente
) {
}
