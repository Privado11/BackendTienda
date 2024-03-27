package com.ventas.tienda.service.pedido;

import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.Entities.Pedido;
import com.ventas.tienda.dto.pedido.PedidoDto;
import com.ventas.tienda.dto.pedido.PedidoToSaveDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {
    PedidoDto guardarPedido(PedidoToSaveDto pago);
    PedidoDto actualizarPedido(Long idPedido, PedidoToSaveDto pedido);
    PedidoDto buscarPedidoPorId(Long idPedido);
    void removerPedido(Long idPedido);
    List<PedidoDto> getAllItemPedidos();

    List<PedidoDto> buscarPedidosEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal);

    List<PedidoDto> buscarPedidoPorClienteYStatus(Cliente cliente, String status);

    List<Pedido> BuscarPedidosyItemsPorCliente(Cliente cliente);
}
