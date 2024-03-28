package com.ventas.tienda.service.producto;

import com.ventas.tienda.Entities.Producto;
import com.ventas.tienda.dto.producto.ProductoDto;
import com.ventas.tienda.dto.producto.ProductoMapper;
import com.ventas.tienda.dto.producto.ProductoSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private ProductoMapper productoMapper;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public ProductoDto guardarProducto(ProductoSaveDto producto) {
        Producto productoG = productoMapper.productoToSaveDtoToEntity(producto);
        return productoMapper.toDto(productoRepository.save(productoG));
    }

    @Override
    public ProductoDto actualizarProducto(Long idProducto, ProductoSaveDto producto) throws NotFoundExceptionEntity {
        return productoRepository.findById(idProducto)
                .map(productoE -> {
                    productoE.setNombreProducto(producto.nombreProducto());
                    productoE.setPrecioProducto(producto.precioProducto());
                    productoE.setStockProducto(producto.stockProducto());

                    Producto productoG = productoRepository.save(productoE);

                    return productoMapper.toDto(productoG);
                }).orElseThrow(() -> new NotFoundExceptionEntity("Producto no encontrado."));
    }

    @Override
    public ProductoDto buscarProductoPorId(Long idProducto) throws NotFoundExceptionEntity {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new NotFoundExceptionEntity("Producto no encontrado."));

        return productoMapper.toDto(producto);
    }

    @Override
    public void removerProducto(Long idProducto) throws NotAbleToDeleteException {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new NotAbleToDeleteException("Producto no encontrado."));

        productoRepository.delete(producto);

    }

    @Override
    public List<ProductoDto> getAllProductos() {
        return productoRepository.findAll()
                .stream()
                .map(producto -> productoMapper.toDto(producto))
                .toList();
    }

    @Override
    public ProductoDto buscarProductoPornombre(String nombreProducto) throws NotFoundExceptionEntity {
        Producto producto = productoRepository.findByNombreProducto(nombreProducto);

        if(Objects.isNull(producto)){
            throw new NotFoundExceptionEntity("Producto no encontrado.");
        }

        return productoMapper.toDto(producto);
    }

    @Override
    public List<ProductoDto> productosEnStock() {
        return productoRepository.productosEnStock()
                .stream()
                .map(producto -> productoMapper.toDto(producto))
                .toList();
    }

    @Override
    public List<ProductoDto> productosConPrecioyStockMenorQue(Double precio, Integer cantidad) {
        return productoRepository.productosConPrecioyStockMenorQue(precio, cantidad)
                .stream()
                .map(producto -> productoMapper.toDto(producto))
                .toList();
    }
}
