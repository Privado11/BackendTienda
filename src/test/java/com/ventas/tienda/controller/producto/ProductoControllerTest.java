package com.ventas.tienda.controller.producto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventas.tienda.Entities.Producto;
import com.ventas.tienda.controller.ProductoController;
import com.ventas.tienda.dto.producto.ProductoDto;
import com.ventas.tienda.dto.producto.ProductoSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.service.producto.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ProductoController.class)
@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

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

        productoDto = new ProductoDto(
                1L,
                "Laptop Hp Victus",
                2000.0,
                20);
    }

    @Test
    void saveProducto_thenReturnProductoCreado() throws Exception {
        ProductoSaveDto productoSaveDto = new ProductoSaveDto(
                null,
                "Laptop Hp Victus",
                2000.0,
                20);

        when(productoService.guardarProducto(productoSaveDto)).thenReturn(productoDto);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombreProducto").value("Laptop Hp Victus"));
    }

    @Test
    void getProductos_thenReturnListaDeProductos() throws Exception {
        List<ProductoDto> productos = Collections.singletonList(productoDto);

        when(productoService.getAllProductos()).thenReturn(productos);

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombreProducto").value("Laptop Hp Victus"));
    }

    @Test
    void getProductobyId_thenReturnProductoExistente() throws Exception {

        when(productoService.buscarProductoPorId(1l)).thenReturn(productoDto);

        mockMvc.perform(get("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombreProducto").value("Laptop Hp Victus"));
    }

    @Test
    void getProductobyId_NotFoundException() throws Exception {
        when(productoService.buscarProductoPorId(999L)).thenThrow(new NotFoundExceptionEntity("Producto no encontrado"));

        mockMvc.perform(get("/api/v1/products/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void actualizarProducto_thenReturnProductoActualizado() throws Exception {
        ProductoSaveDto productoSaveDto = new ProductoSaveDto(
                null,
                "Laptop Hp Victus",
                2000.0,
                20);

        when(productoService.actualizarProducto(anyLong(), any())).thenReturn(productoDto);

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombreProducto").value("Laptop Hp Victus"));
    }

    @Test
    void actualizarProducto_NotFoundException() throws Exception {
        ProductoSaveDto productoSaveDto = new ProductoSaveDto(null, "Producto de prueba actualizado", 200.0, 20);

        when(productoService.actualizarProducto(any(), any())).thenThrow(new NotFoundExceptionEntity("Producto no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteProducto_thenReturnOk() throws Exception {
        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteProducto_NotAbleToDeleteException() throws Exception {
        doThrow(new NotAbleToDeleteException("Producto no encontrado."))
                .when(productoService).removerProducto(999L);

        mockMvc.perform(delete("/api/v1/products/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void enStock_thenReturnProductosEnStock() throws Exception {
        List<ProductoDto> productos = Collections.singletonList(productoDto);

        when(productoService.productosEnStock()).thenReturn(productos);

        mockMvc.perform(get("/api/v1/products/instock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombreProducto").value("Laptop Hp Victus"));
    }

    @Test
    void buscarProductoPorNombre_thenReturnProductoEncontrado() throws Exception {

        when(productoService.buscarProductoPornombre("Laptop Hp Victus")).thenReturn(productoDto);

        mockMvc.perform(get("/api/v1/products/search?nombreProducto=Laptop Hp Victus")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombreProducto").value("Laptop Hp Victus"));
    }

    @Test
        void buscarProductoPorNombre_NotFoundException() throws Exception {
        when(productoService.buscarProductoPornombre("Producto")).thenThrow(new NotFoundExceptionEntity("Producto no encontrado"));

        mockMvc.perform(get("/api/v1/products/search?nombreProducto=Producto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void buscarProductoPorPrecioyStockMenorQue() {
    }
}