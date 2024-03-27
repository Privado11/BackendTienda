package com.ventas.tienda.service.producto;

import com.ventas.tienda.dto.producto.ProductoDto;
import com.ventas.tienda.dto.producto.ProductoSaveDto;

import java.util.List;

public interface ProductoService {

    ProductoDto guardarProducto(ProductoSaveDto producto);
    ProductoDto actualizarProducto(Long idProducto, ProductoSaveDto producto);
    ProductoDto buscarProductoPorId(Long idProducto);
    void removerProducto(Long idProducto);
    List<ProductoDto> getAllItemProducto();

    ProductoDto buscarProductoPornombre(String nombreProducto);

    List<ProductoDto> productosEnStock();

    List<ProductoDto> productosConPrecioyStockMenorQue(Double precio, Integer cantidad);

}
