package com.ventas.tienda.repository.producto;

import com.ventas.tienda.Entities.Producto;
import com.ventas.tienda.repository.AbstractIntegrationBDTest;
import com.ventas.tienda.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductoRepositoryTest extends AbstractIntegrationBDTest {

    ProductoRepository productoRepository;


    @Autowired
    public ProductoRepositoryTest(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    void initMockProductos(){
        Producto producto = Producto.builder()
                .nombreProducto("Laptop Hp Victus")
                .precioProducto(2000.0)
                .stockProducto(20)
                .build();
        productoRepository.save(producto);

        Producto producto2 = Producto.builder()
                .nombreProducto("Teclado Mecanico")
                .precioProducto(300.0)
                .stockProducto(0)
                .build();
        productoRepository.save(producto2);
        productoRepository.flush();
    }

    @BeforeEach
    void setUp() {
        productoRepository.deleteAll();
    }

    @Test
    void guardarProducto() {
        //Given
        Producto producto = Producto.builder()
                .nombreProducto("Laptop Lenovo Legion")
                .precioProducto(2500.0)
                .stockProducto(20)
                .build();

        Producto productoGuardado = productoRepository.save(producto);

        System.out.println(productoGuardado);

        assertThat(productoGuardado).isNotNull();
    }
    @Test
    void findByNombreProducto() {
        initMockProductos();
        String nombre = "Teclado Mecanico";

        Producto producto = productoRepository.findByNombreProducto(nombre);

        System.out.println(producto);

        assertThat(producto).isNotNull();;
    }

    @Test
    void productosEnStock() {
        initMockProductos();
        List<Producto> productosEnStock = productoRepository.productosEnStock();

        System.out.println(productosEnStock);
        assertThat(productosEnStock).isNotEmpty();
        assertThat(productosEnStock).hasSize(1);
    }

    @Test
    void productosConPrecioyStockMenorQue() {
        initMockProductos();
        Double precio = 2000.0;
        Integer stock = 15;
        List<Producto> productos = productoRepository.productosConPrecioyStockMenorQue(precio, stock);

        System.out.println(productos.toString());
        assertThat(productos).isNotEmpty();
    }

    @Test
    void findAll() {
        initMockProductos();
        List<Producto> productos = productoRepository.findAll();
        System.out.println(productos);
        assertThat(productos).isNotEmpty();
    }
}