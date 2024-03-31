package com.ventas.tienda.controller.itemPedido;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventas.tienda.Entities.ItemPedido;
import com.ventas.tienda.controller.CreateEntytiesForTest;
import com.ventas.tienda.controller.ItemPedidoController;
import com.ventas.tienda.dto.itemPedido.ItemPedidoDto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoMapper;
import com.ventas.tienda.dto.itemPedido.ItemPedidoToSaveDto;
import com.ventas.tienda.service.itemPedido.ItemPedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ItemPedidoController.class)
@ExtendWith(MockitoExtension.class)
class ItemPedidoControllerTest extends CreateEntytiesForTest {

    @Autowired
    MockMvc  mockMvc;

    @MockBean
    private ItemPedidoService itemPedidoService;

    @Autowired
    ObjectMapper objectMapper;

    ItemPedido itemPedido, itemPedido2;
    ItemPedidoDto itemPedidoDto;

    @BeforeEach
    void setUp() {
        itemPedido = ItemPedido.builder()
                .idItemPedido(1l)
                .producto(productosList().get(0))
                .cantidadItem(4)
                .precio(productosList().get(0).getPrecioProducto())
                .build();

        itemPedido2 = ItemPedido.builder()
                .producto(productosList().get(0))
                .cantidadItem(4)
                .precio(productosList().get(0).getPrecioProducto())
                .build();

        itemPedidoDto = ItemPedidoMapper.INSTANCE.toDto(itemPedido);
    }

    @Test
    void saveItemPedido_thenReturnItemPedidoCreado() throws Exception {
        ItemPedidoToSaveDto itemPedidoToSaveDto = new ItemPedidoToSaveDto(
                null,
                100,
                500.0,
                null,
                null
        );

        when(itemPedidoService.guardarItemPedido(itemPedidoToSaveDto)).thenReturn(itemPedidoDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemPedidoToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idItemPedido").value(1));
    }

    @Test
    void getItemPedidos_thenReturnListOfItemPedidos() throws Exception {

        when(itemPedidoService.getAllItemPedidos()).thenReturn(Collections.singletonList(itemPedidoDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order-items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idItemPedido").value(1));
    }

    @Test
    void getItemPedidoById_thenReturnItemPedido() throws Exception {
        long id = 1L;

        when(itemPedidoService.buscarItemPedidoPorId(id)).thenReturn(itemPedidoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order-items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idItemPedido").value(id));
    }

    @Test
    void actualizarItemPedido_thenReturnItemPedidoActualizado() throws Exception {
        long id = 1L;
        ItemPedidoToSaveDto itemPedidoToSaveDto = new ItemPedidoToSaveDto(
                null,
                100,
                500.0,
                null,
                null
        );


        when(itemPedidoService.actualizarItemPedido(id, itemPedidoToSaveDto)).thenReturn(itemPedidoDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/order-items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemPedidoToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idItemPedido").value(id));
    }

    @Test
    void deleteItemPedido_thenReturnSuccessMessage() throws Exception {
        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/order-items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Item de pedido eliminado."));
    }

    @Test
    void getItemPedidoByIdPedido_thenReturnListOfItemPedidos() throws Exception {
        long orderId = 1L;
        List<ItemPedidoDto> itemPedidoDtoList = Collections.singletonList(itemPedidoDto);

        when(itemPedidoService.buscarItemPedidoPorIdPedido(orderId)).thenReturn(itemPedidoDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order-items/order/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idItemPedido").value(1));
    }

    @Test
    void getItemPedidoByNameProduct_thenReturnListOfItemPedidos() throws Exception {
        String productName = "Product 1";
        List<ItemPedidoDto> itemPedidoDtoList = Collections.singletonList(itemPedidoDto);

        when(itemPedidoService.buscarItemPedidoPorNombreProducto(productName)).thenReturn(itemPedidoDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order-items/product")
                        .param("nameProduct", productName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idItemPedido").value(1));
    }


    @Test
    void getItemPedidoById_NotFoundException() throws Exception {
        long id = 1L;

        when(itemPedidoService.buscarItemPedidoPorId(id)).thenThrow(new NotFoundExceptionEntity("Item de pedido no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order-items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void actualizarItemPedido_NotFoundException() throws Exception {
        long id = 1L;
        ItemPedidoToSaveDto itemPedidoToSaveDto = new ItemPedidoToSaveDto(
                null,
                100,
                500.0,
                null,
                null
        );

        when(itemPedidoService.actualizarItemPedido(id, itemPedidoToSaveDto)).thenThrow(new NotFoundExceptionEntity("Item de pedido no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/order-items/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemPedidoToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void deleteItemPedido_NotAbleToDeleteException() throws Exception {
        doThrow(new NotAbleToDeleteException("ItemPedido no encontrado."))
                .when(itemPedidoService).removerItemPedido(999l);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/order-items/{id}", 999l)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}