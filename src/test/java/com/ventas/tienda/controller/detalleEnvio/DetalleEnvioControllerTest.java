package com.ventas.tienda.controller.detalleEnvio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventas.tienda.Entities.DetalleEnvio;
import com.ventas.tienda.Enum.StatusPedido;
import com.ventas.tienda.controller.CreateEntytiesForTest;
import com.ventas.tienda.controller.DetalleEnvioController;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioDto;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioMapper;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioToSaveDto;
import com.ventas.tienda.service.detalleEnvio.DetalleEnvioService;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doThrow;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(DetalleEnvioController.class)
@ExtendWith(MockitoExtension.class)
class DetalleEnvioControllerTest extends CreateEntytiesForTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private DetalleEnvioService detalleEnvioService;

    @Autowired
    ObjectMapper objectMapper;

    DetalleEnvio detalleEnvio, detalleEnvio2;

    DetalleEnvioDto detalleEnvioDto;

    @BeforeEach
    void setUp() {
        detalleEnvio = DetalleEnvio.builder()
                .idDetalleEnvio(1l)
                .direccionEnvio("Santa Marta")
                .transportadoraEnvio("Envia")
                .numeroGuiaEnvio("CSJ8249")
                .build();

        detalleEnvio2 = DetalleEnvio.builder()
                .direccionEnvio("Santa Marta")
                .transportadoraEnvio("Coordinadora")
                .numeroGuiaEnvio("CSJ8245")
                .build();

        detalleEnvioDto = DetalleEnvioMapper.INSTANCE.toDto(detalleEnvio);
    }

    @Test
    void saveDetalleEnvio_thenReturnDetalleEnvioCreado() throws Exception {
        DetalleEnvioToSaveDto detalleEnvioToSaveDto = new DetalleEnvioToSaveDto(
                null,
                "Santa Marta",
                "Envia",
                "CSJ8249",
                null
        );

        when(detalleEnvioService.guardarDetalleEnvio(detalleEnvioToSaveDto)).thenReturn(detalleEnvioDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shipping")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalleEnvioToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idDetalleEnvio").value(1));
    }

    @Test
    void getDetallesEnvio_thenReturnListOfDetallesEnvio() throws Exception {
        when(detalleEnvioService.getAllDetalleEnvio()).thenReturn(Collections.singletonList(detalleEnvioDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shipping")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getDetalleEnvioById_thenReturnDetalleEnvio() throws Exception {
        long id = 1L;

        when(detalleEnvioService.buscarDetalleEnvioPorId(id)).thenReturn(detalleEnvioDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shipping/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void actualizarDetalleEnvio_thenReturnDetalleEnvioActualizado() throws Exception {
        long id = 1L;
        DetalleEnvioToSaveDto detalleEnvioToSaveDto = new DetalleEnvioToSaveDto(
                null,
                "Santa Marta",
                "Envia",
                "CSJ8249",
                null
        );

        when(detalleEnvioService.actualizarDetalleEnvio(id, detalleEnvioToSaveDto)).thenReturn(detalleEnvioDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/shipping/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalleEnvioToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteDetalleEnvio_thenReturnSuccessMessage() throws Exception {
        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/shipping/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getDetallesEnvioByCarrier_thenReturnListOfDetallesEnvio() throws Exception {
        String nombreTransportadora = "Transportadora";


        when(detalleEnvioService.buscarDetallesEnvioPorTransportadora(nombreTransportadora)).thenReturn(Collections.singletonList(detalleEnvioDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shipping/carrier")
                        .param("name", nombreTransportadora)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getDetallesEnvioByIdPedido_thenReturnDetalleEnvio() throws Exception {
        long idPedido = 1L;


        when(detalleEnvioService.buscarDetallesEnvioPorIdPedido(idPedido)).thenReturn(detalleEnvioDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shipping/order/{orderId}", idPedido)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getDetallesenvioByStatusOrder_thenReturnListOfDetallesEnvio() throws Exception {
        StatusPedido status = StatusPedido.ENTREGADO;


        when(detalleEnvioService.buscarDetallesEnvioPorStatus(status)).thenReturn(Collections.singletonList(detalleEnvioDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shipping/status")
                        .param("statusOrder", "ENTREGADO")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getDetalleEnvioById_NotFoundException() throws Exception {
        long id = 1L;


        when(detalleEnvioService.buscarDetalleEnvioPorId(id)).thenThrow(new NotFoundExceptionEntity("Detalle de envío no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/shipping/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void actualizarDetalleEnvio_NotFoundException() throws Exception {
        long id = 1L;
        DetalleEnvioToSaveDto detalleEnvioToSaveDto = new DetalleEnvioToSaveDto(
                null,
                "Santa Marta",
                "Envia",
                "CSJ8249",
                null
        );

        when(detalleEnvioService.actualizarDetalleEnvio(id, detalleEnvioToSaveDto)).thenThrow(new NotFoundExceptionEntity("Detalle de envío no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/shipping/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detalleEnvioToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteDetalleEnvio_NotAbleToDeleteException() throws Exception {
        long id = 1L;

        doThrow(new NotAbleToDeleteException("Detalle de envío no encontrado."))
                .when(detalleEnvioService).removerDetalleEnvio(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/shipping/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}