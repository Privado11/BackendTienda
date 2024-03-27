package com.ventas.tienda.service.producto;

import com.ventas.tienda.dto.producto.ProductoDto;
import com.ventas.tienda.dto.producto.ProductoSaveDto;
import com.ventas.tienda.exception.NotFoundExceptionEntity;

import java.util.List;

public interface ProductoService {

    ProductoDto guardarProducto(ProductoSaveDto producto);
    ProductoDto actualizarProducto(Long idProducto, ProductoSaveDto producto) throws NotFoundExceptionEntity;
    ProductoDto buscarProductoPorId(Long idProducto) throws NotFoundExceptionEntity;
    void removerProducto(Long idProducto) throws NotFoundExceptionEntity;
    List<ProductoDto> getAllProductos();

    ProductoDto buscarProductoPornombre(String nombreProducto) throws NotFoundExceptionEntity;

    List<ProductoDto> productosEnStock();

    List<ProductoDto> productosConPrecioyStockMenorQue(Double precio, Integer cantidad);

}
