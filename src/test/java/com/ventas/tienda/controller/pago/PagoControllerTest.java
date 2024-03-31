package com.ventas.tienda.controller.pago;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventas.tienda.Entities.Pago;
import com.ventas.tienda.Enum.MetodoPago;
import com.ventas.tienda.controller.CreateEntytiesForTest;
import com.ventas.tienda.controller.PagoController;
import com.ventas.tienda.dto.pago.PagoDto;
import com.ventas.tienda.dto.pago.PagoMapper;
import com.ventas.tienda.dto.pago.PagoToSaveDto;
import com.ventas.tienda.service.pago.PagoService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PagoController.class)
@ExtendWith(MockitoExtension.class)
class PagoControllerTest extends CreateEntytiesForTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    ObjectMapper objectMapper;

    Pago pago, pago2;

    PagoDto pagoDto;

    @BeforeEach
    void setUp() {
        pago = Pago.builder()
                .idPago(1l)
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.EFECTIVO)
                .build();

        pago2 = Pago.builder()
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.TARJETA_CREDITO)
                .build();

        pagoDto = PagoMapper.INSTANCE.toDto(pago);
    }

    @Test
    void savePago_thenReturnPagoCreado() throws Exception {
        PagoToSaveDto pagoToSaveDto = new PagoToSaveDto(
                null,
                5000.0,
                LocalDateTime.now(),
                MetodoPago.EFECTIVO,
                null
        );

        when(pagoService.guardarPago(pagoToSaveDto)).thenReturn(pagoDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.idPago").value(1));
    }

    @Test
    void getPagos_thenReturnListaDePagos() throws Exception {
        List<PagoDto> pagos = Collections.singletonList(pagoDto);

        when(pagoService.getAllPagos()).thenReturn(pagos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].idPago").value(1));
    }

    @Test
    void getPagoById_thenReturnPagoExistente() throws Exception {

        when(pagoService.buscarPagoPorId(anyLong())).thenReturn(pagoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.idPago").value(1));
    }

    @Test
    void getPagoById_NotFoundException() throws Exception {
        when(pagoService.buscarPagoPorId(anyLong())).thenThrow(new NotFoundExceptionEntity("Pago no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void actualizarPago_thenReturnPagoActualizado() throws Exception {
        PagoToSaveDto pagoToSaveDto = new PagoToSaveDto(
                null,
                5000.0,
                LocalDateTime.now(),
                MetodoPago.EFECTIVO,
                null
        );

        when(pagoService.actualizarPago(anyLong(), any())).thenReturn(pagoDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/payments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.idPago").value(1));
    }

    @Test
    void actualizarPago_NotFoundException() throws Exception {
        PagoToSaveDto pagoToSaveDto = new PagoToSaveDto(
                null,
                5000.0,
                LocalDateTime.now(),
                MetodoPago.EFECTIVO,
                null
        );

        when(pagoService.actualizarPago(any(), any())).thenThrow(new NotFoundExceptionEntity("Pago no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/payments/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deletePago_thenReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/payments/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deletePago_NotAbleToDeleteException() throws Exception {
        doThrow(new NotAbleToDeleteException("Pago no encontrado."))
                .when(pagoService).removerPago(999L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/payments/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void pagoPorIdPedido_thenReturnPago() throws Exception {
        when(pagoService.buscarPagosPorIdPedido(anyLong())).thenReturn(pagoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/order/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.idPago").value(1));
    }

    @Test
    void buscarPagosEntreFechas_thenReturnPagosEnRango() throws Exception {
        List<PagoDto> pagos = Collections.singletonList(pagoDto);

        when(pagoService.buscarpagosEntreFechas(any(), any())).thenReturn(pagos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/date-range")
                        .param("startDate", LocalDateTime.now().minusDays(7).toString())
                        .param("endDate", LocalDateTime.now().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].idPago").value(1));
    }
}