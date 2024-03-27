package com.ventas.tienda.service.itemPedido;

import com.ventas.tienda.Entities.Producto;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioDto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoDto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoToSaveDto;

import java.util.List;

public interface ItemPedidoService {
    ItemPedidoDto guardarItemPedido(ItemPedidoToSaveDto itemPedido);
    ItemPedidoDto actualizarItemPedido(Long idItemPedido, ItemPedidoToSaveDto itemPedido);
    ItemPedidoDto buscarItemPedidoPorId(Long idItemPedido);
    void removerItemPedido(Long idDetalleEnvio);
    List<ItemPedidoDto> getAllItemPedidos();

    List<ItemPedidoDto> buscarItemPedidoPorNombreProducto(String nombreProducto);

    List<ItemPedidoDto> buscarItemPedidoPorIdPedido(Long idPedido);

    Double sumaVentasProducto(Producto producto);
}
