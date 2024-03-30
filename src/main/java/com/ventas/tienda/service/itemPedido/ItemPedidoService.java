package com.ventas.tienda.service.itemPedido;

import com.ventas.tienda.Entities.Producto;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioDto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoDto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoToSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;

import java.util.List;

public interface ItemPedidoService {
    ItemPedidoDto guardarItemPedido(ItemPedidoToSaveDto itemPedido);
    ItemPedidoDto actualizarItemPedido(Long idItemPedido, ItemPedidoToSaveDto itemPedido) throws NotFoundExceptionEntity;
    ItemPedidoDto buscarItemPedidoPorId(Long idItemPedido) throws NotFoundExceptionEntity;
    void removerItemPedido(Long idItemPedido) throws NotAbleToDeleteException;
    List<ItemPedidoDto> getAllItemPedidos();

    List<ItemPedidoDto> buscarItemPedidoPorNombreProducto(String nombreProducto);

    ItemPedidoDto buscarItemPedidoPorIdPedido(Long idPedido) throws NotFoundExceptionEntity;

    Double sumaVentasProducto(Producto producto);
}
