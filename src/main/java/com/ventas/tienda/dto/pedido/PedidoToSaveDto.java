package com.ventas.tienda.dto.pedido;

import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.Entities.DetalleEnvio;
import com.ventas.tienda.Entities.Pago;
import com.ventas.tienda.Enum.StatusPedido;

import java.time.LocalDateTime;

public record PedidoToSaveDto(
        Long idPedido,
        LocalDateTime fechaPedido,
        StatusPedido status,
        Cliente cliente
) {

}
