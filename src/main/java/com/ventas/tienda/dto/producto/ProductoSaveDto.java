package com.ventas.tienda.dto.producto;

public record ProductoSaveDto(
        Long idProducto,
        String nombreProducto,
        Double precioProducto,
        Integer stockProducto) {
}
