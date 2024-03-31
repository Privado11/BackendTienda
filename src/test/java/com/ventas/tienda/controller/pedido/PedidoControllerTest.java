package com.ventas.tienda.controller.pedido;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.Entities.Pedido;
import com.ventas.tienda.Enum.StatusPedido;
import com.ventas.tienda.controller.CreateEntytiesForTest;
import com.ventas.tienda.controller.PedidoController;
import com.ventas.tienda.dto.pedido.PedidoDto;
import com.ventas.tienda.dto.pedido.PedidoMapper;
import com.ventas.tienda.dto.pedido.PedidoToSaveDto;
import com.ventas.tienda.service.pedido.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
@ExtendWith(MockitoExtension.class)
class PedidoControllerTest extends CreateEntytiesForTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    Pedido pedido, pedido2;

    PedidoDto pedidoDto;

    List<Cliente> clientes;

    @BeforeEach
    void setUp() {
        clientes = clienteList();
        pedido = Pedido.builder()
                .idPedido(1l)
                .fechaPedido(LocalDateTime.now())
                .status(StatusPedido.ENVIADO)
                .cliente(clientes.get(0))
                .detalleEnvio(detalleEnvioList().get(0))
                .build();

        pedido2 = Pedido.builder()
                .fechaPedido(LocalDateTime.now())
                .status(StatusPedido.PENDIENTE)
                .cliente(clientes.get(1))
                .detalleEnvio(detalleEnvioList().get(1))
                .build();

        pedidoDto = PedidoMapper.INSTANCE.toDto(pedido);
    }

    @Test
    void savePedido_thenReturnPedidoCreado() throws Exception {
        PedidoToSaveDto pedidoSaveDto = new PedidoToSaveDto(
                null,
                LocalDateTime.now(),
                StatusPedido.ENVIADO,
                null);

        when(pedidoService.guardarPedido(pedidoSaveDto)).thenReturn(pedidoDto);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoSaveDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(1));
    }

    @Test
    void getPedidos_thenReturnListaDePedidos() throws Exception {
        List<PedidoDto> pedidos = Collections.singletonList(pedidoDto);

        when(pedidoService.getAllPedidos()).thenReturn(pedidos);

        mockMvc.perform(get("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPedido").value(1));
    }

    @Test
    void getPedidoById_thenReturnPedidoExistente() throws Exception {
        when(pedidoService.buscarPedidoPorId(1L)).thenReturn(pedidoDto);

        System.out.println(pedidoDto);

        mockMvc.perform(get("/api/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(1));
    }

    @Test
    void getPedidoById_NotFoundException() throws Exception {
        when(pedidoService.buscarPedidoPorId(999L)).thenThrow(new NotFoundExceptionEntity("Pedido no encontrado"));

        mockMvc.perform(get("/api/v1/orders/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void actualizarPedido_thenReturnPedidoActualizado() throws Exception {
        PedidoToSaveDto pedidoSaveDto = new PedidoToSaveDto(
                null,
                LocalDateTime.now(),
                StatusPedido.ENVIADO,
                null);

        when(pedidoService.actualizarPedido(1L, pedidoSaveDto)).thenReturn(pedidoDto);

        mockMvc.perform(put("/api/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoSaveDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(1));
    }

    @Test
    void actualizarPedido_NotFoundException() throws Exception {
        PedidoToSaveDto pedidoSaveDto = new PedidoToSaveDto(
                null,
                LocalDateTime.now(),
                StatusPedido.ENVIADO,
                null);

        when(pedidoService.actualizarPedido(999L, pedidoSaveDto)).thenThrow(new NotFoundExceptionEntity("Pedido no encontrado"));

        mockMvc.perform(put("/api/v1/orders/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoSaveDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePedido_thenReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deletePedido_NotAbleToDeleteException() throws Exception {
        doThrow(new NotAbleToDeleteException("Pedido no encontrado."))
                .when(pedidoService).removerPedido(999L);

        mockMvc.perform(delete("/api/v1/orders/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPedidoPorIdCliente_thenReturnPedidosDelCliente() throws Exception {
        List<PedidoDto> pedidos = Collections.singletonList(pedidoDto);

        when(pedidoService.BuscarPedidosyItemsPorCliente(1L)).thenReturn(pedidos);

        mockMvc.perform(get("/api/v1/orders/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPedido").value(1));
    }

    @Test
    void buscarPedidosEntreFechas_thenReturnPedidosEnRango() throws Exception {
        List<PedidoDto> pedidos = List.of(pedidoDto);

        when(pedidoService.buscarPedidosEntreFechas(any(), any())).thenReturn(pedidos);

        mockMvc.perform(get("/api/v1/orders/date-range")
                        .param("startDate", LocalDateTime.now().minusDays(7).toString())
                        .param("endDate", LocalDateTime.now().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPedido").value(1));
    }
}