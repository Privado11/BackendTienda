package com.ventas.tienda.service.itemPedido;

import com.ventas.tienda.Entities.Producto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoDto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoToSaveDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService {

    @Override
    public ItemPedidoDto guardarItemPedido(ItemPedidoToSaveDto itemPedido) {
        return null;
    }

    @Override
    public ItemPedidoDto actualizarItemPedido(Long idItemPedido, ItemPedidoToSaveDto itemPedido) {
        return null;
    }

    @Override
    public ItemPedidoDto buscarItemPedidoPorId(Long idItemPedido) {
        return null;
    }

    @Override
    public void removerItemPedido(Long idDetalleEnvio) {

    }

    @Override
    public List<ItemPedidoDto> getAllItemPedidos() {
        return null;
    }

    @Override
    public List<ItemPedidoDto> buscarItemPedidoPorNombreProducto(String nombreProducto) {
        return null;
    }

    @Override
    public List<ItemPedidoDto> buscarItemPedidoPorIdPedido(Long idPedido) {
        return null;
    }

    @Override
    public Double sumaVentasProducto(Producto producto) {
        return null;
    }
}
