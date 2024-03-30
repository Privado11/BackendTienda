package com.ventas.tienda.repository;

import com.ventas.tienda.Entities.ItemPedido;
import com.ventas.tienda.Entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByProducto_NombreProductoLike(String nombreProducto);
    ItemPedido findByPedido_IdPedido(Long idPedido);

    @Query("select sum(i.producto.precioProducto * i.cantidadItem) from ItemPedido i where i.producto = :producto")
    Double sumaTotalVentasProducto(@Param("producto") Producto producto);
}

