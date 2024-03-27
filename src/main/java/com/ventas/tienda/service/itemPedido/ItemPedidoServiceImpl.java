package com.ventas.tienda.service.itemPedido;


import com.ventas.tienda.Entities.ItemPedido;
import com.ventas.tienda.Entities.Producto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoDto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoMapper;
import com.ventas.tienda.dto.itemPedido.ItemPedidoToSaveDto;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private ItemPedidoMapper itemPedidoMapper;

    @Autowired
    public ItemPedidoServiceImpl(ItemPedidoRepository itemPedidoRepository, ItemPedidoMapper itemPedidoMapper) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.itemPedidoMapper = itemPedidoMapper;
    }

    @Override
    public ItemPedidoDto guardarItemPedido(ItemPedidoToSaveDto itemPedido) {
        ItemPedido itemPedidoG = itemPedidoMapper.itemPedidoToSaveDtoToEntity(itemPedido);
        return itemPedidoMapper.toDto(itemPedidoRepository.save(itemPedidoG));
    }

    @Override
    public ItemPedidoDto actualizarItemPedido(Long idItemPedido, ItemPedidoToSaveDto itemPedido) throws NotFoundExceptionEntity {
        return itemPedidoRepository.findById(idItemPedido)
                .map(itemPedidoE -> {
                    itemPedidoE.setCantidadItem(itemPedido.cantidadItem());
                    itemPedidoE.setPedido(itemPedido.pedido());
                    itemPedidoE.setPrecio(itemPedido.precio());
                    itemPedidoE.setProducto(itemPedido.producto());

                    ItemPedido itemPedidoG = itemPedidoRepository.save(itemPedidoE);

                    return itemPedidoMapper.toDto(itemPedidoG);
                }).orElseThrow(() -> new NotFoundExceptionEntity("ItemPedido no encontrado."));
    }

    @Override
    public ItemPedidoDto buscarItemPedidoPorId(Long idItemPedido) throws NotFoundExceptionEntity {
        ItemPedido itemPedidoE = itemPedidoRepository.findById(idItemPedido)
                .orElseThrow(() -> new NotFoundExceptionEntity("ItemPedido no encontrado."));
        return itemPedidoMapper.toDto(itemPedidoE);
    }

    @Override
    public void removerItemPedido(Long idItemPedido) throws NotFoundExceptionEntity {
        ItemPedido itemPedidoE = itemPedidoRepository.findById(idItemPedido)
                .orElseThrow(() -> new NotFoundExceptionEntity("ItemPedido no encontrado."));
        itemPedidoRepository.delete(itemPedidoE);

    }

    @Override
    public List<ItemPedidoDto> getAllItemPedidos() {
        return itemPedidoRepository.findAll()
                .stream()
                .map(itemPedido -> itemPedidoMapper.toDto(itemPedido))
                .toList();
    }

    @Override
    public List<ItemPedidoDto> buscarItemPedidoPorNombreProducto(String nombreProducto) {
        return itemPedidoRepository.findByProducto_NombreProductoLike(nombreProducto)
                .stream()
                .map(itemPedido -> itemPedidoMapper.toDto(itemPedido))
                .toList();
    }

    @Override
    public List<ItemPedidoDto> buscarItemPedidoPorIdPedido(Long idPedido) {
        return itemPedidoRepository.findByPedido_IdPedido(idPedido)
                .stream()
                .map(itemPedido -> itemPedidoMapper.toDto(itemPedido))
                .toList();
    }

    @Override
    public Double sumaVentasProducto(Producto producto) {
        return itemPedidoRepository.sumaTotalVentasProducto(producto);
    }
}
