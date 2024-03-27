package com.ventas.tienda.service.pedido;

import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.Entities.Pedido;
import com.ventas.tienda.dto.pedido.PedidoDto;
import com.ventas.tienda.dto.pedido.PedidoToSaveDto;
import com.ventas.tienda.exception.NotFoundExceptionEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {
    PedidoDto guardarPedido(PedidoToSaveDto pedido);
    PedidoDto actualizarPedido(Long idPedido, PedidoToSaveDto pedido) throws NotFoundExceptionEntity;
    PedidoDto buscarPedidoPorId(Long idPedido) throws NotFoundExceptionEntity;
    void removerPedido(Long idPedido) throws NotFoundExceptionEntity;
    List<PedidoDto> getAllItemPedidos();

    List<PedidoDto> buscarPedidosEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal);

    List<PedidoDto> buscarPedidoPorClienteYStatus(Cliente cliente, String status);

    List<PedidoDto> BuscarPedidosyItemsPorCliente(Cliente cliente);
}
