package com.ventas.tienda.repository;

import com.ventas.tienda.Entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductoRepository extends JpaRepository<Producto, Long>{
    Producto findByNombreProducto(String nombre);

    @Query("select u from Producto u where u.stockProducto >= 1 ")
    List<Producto> productosEnStock();

    @Query("select u from Producto u where u.precioProducto <= ?1  and u.stockProducto <= ?2 ")
    List<Producto> productosConPrecioyStockMenorQue(Double precio, Integer cantidad);
}
