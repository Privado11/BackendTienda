package com.ventas.tienda.service.producto;

import com.ventas.tienda.dto.producto.ProductoDto;
import com.ventas.tienda.dto.producto.ProductoSaveDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Override
    public ProductoDto guardarProducto(ProductoSaveDto producto) {
        return null;
    }

    @Override
    public ProductoDto actualizarProducto(Long idProducto, ProductoSaveDto producto) {
        return null;
    }

    @Override
    public ProductoDto buscarProductoPorId(Long idProducto) {
        return null;
    }

    @Override
    public void removerProducto(Long idProducto) {

    }

    @Override
    public List<ProductoDto> getAllItemProducto() {
        return null;
    }

    @Override
    public ProductoDto buscarProductoPornombre(String nombreProducto) {
        return null;
    }

    @Override
    public List<ProductoDto> productosEnStock() {
        return null;
    }

    @Override
    public List<ProductoDto> productosConPrecioyStockMenorQue(Double precio, Integer cantidad) {
        return null;
    }
}
