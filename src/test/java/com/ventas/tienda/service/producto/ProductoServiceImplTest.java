package com.ventas.tienda.service.producto;

import static org.mockito.ArgumentMatchers.any;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.ventas.tienda.Entities.Producto;
import com.ventas.tienda.dto.producto.ProductoDto;
import com.ventas.tienda.dto.producto.ProductoMapper;
import com.ventas.tienda.dto.producto.ProductoSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductoServiceImpl productoService;

    Producto producto, producto2;
    ProductoDto productoDto;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .nombreProducto("Laptop Hp Victus")
                .precioProducto(2000.0)
                .stockProducto(20)
                .build();

        producto2 = Producto.builder()
                .nombreProducto("Teclado Mecanico")
                .precioProducto(300.0)
                .stockProducto(15)
                .build();

        productoDto = ProductoMapper.INSTANCE.toDto(producto);

    }

    @Test
    void givenProductoService_whenGuardarProducto_thenReturnProductoGuardado() {

       when(productoRepository.save(any())).thenReturn(producto);

        ProductoSaveDto productoSaveDto = new ProductoSaveDto(null
        ,"Mouse Logitech", 500.0, 50);


        when(productoMapper.toDto(any())).thenReturn(productoDto);

        ProductoDto productoDtoG = productoService.guardarProducto(productoSaveDto);

        assertThat(productoDtoG).isNotNull();

    }

    @Test
    void givenProductoService_whenActualizarProducto_thenReturnProductoActualizado() throws NotFoundExceptionEntity {
        when(productoRepository.findById(any())).thenReturn(Optional.of(producto));
        ProductoSaveDto productoT = new ProductoSaveDto(null, "Laptop Hp Victus", 2500.0, 15);


        when(productoRepository.save(any())).thenReturn(producto);

        when(productoMapper.toDto(any())).thenReturn(productoDto);

        ProductoDto productoActualizado = productoService.actualizarProducto(1l, productoT);

        assertThat(productoActualizado).isNotNull();
    }

    @Test
    void givenProductoService_whenBuscarProductoPorId_thenReturnProducto() throws NotFoundExceptionEntity {
        Long idProducto = 1l;

        when(productoRepository.findById(idProducto)).thenReturn(Optional.of(producto));

        when(productoMapper.toDto(any())).thenReturn(productoDto);

        ProductoDto productoDtoE = productoService.buscarProductoPorId(idProducto);

        assertThat(productoDtoE).isNotNull();
    }

    @Test
    void givenProductoService_whenRemoverProducto_thenReturnMensaje() throws NotAbleToDeleteException {
        Long idProducto = 1l;

        when(productoRepository.findById(idProducto)).thenReturn(Optional.of(producto));

        productoService.removerProducto(idProducto);

        verify(productoRepository, times(1)).delete(any());
    }

    @Test
    void givenProductoService_whenGetAllProductos_thenReturnListOfProductos() {
        List<Producto> productos = List.of(producto, producto2);

        when(productoRepository.findAll()).thenReturn(productos);

        List<ProductoDto> productoDtoList = productoService.getAllProductos();

        assertThat(productoDtoList).isNotEmpty();

        assertThat(productoDtoList).hasSize(2);
    }

    @Test
    void givenProductoService_whenBuscarProductoPorNombre_thenReturnProducto() throws NotFoundExceptionEntity {
        String nombreProducto = "Laptop Hp Victus";

        when(productoRepository.findByNombreProducto(any())).thenReturn(producto);

        when(productoMapper.toDto(any())).thenReturn(productoDto);

        ProductoDto productoDtoE = productoService.buscarProductoPornombre(nombreProducto);

        assertThat(productoDtoE).isNotNull();

    }

    @Test
    void givenProductoService_whenProductosEnStock_thenReturnListOfProductos() {
        List<Producto> productos = List.of(producto, producto2);

        when(productoRepository.productosEnStock()).thenReturn(productos);

        List<ProductoDto> productoDtoList = productoService.productosEnStock();

        assertThat(productoDtoList).isNotEmpty();

        assertThat(productoDtoList).hasSize(2);
    }

    @Test
    void givenProductoService_whenProductosConPrecioyStockMenorQue_thenReturnListOfProductos(){
        Double precio = 2000.0;
        Integer stock = 10;

        List<Producto> productos = List.of(producto);

        when(productoRepository.productosConPrecioyStockMenorQue(precio, stock)).thenReturn(productos);

        List<ProductoDto> productoDtoList = productoService.productosConPrecioyStockMenorQue(precio, stock);

        assertThat(productoDtoList).isNotEmpty();

        assertThat(productoDtoList).hasSize(1);

    }
}