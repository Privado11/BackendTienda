package com.ventas.tienda.controller.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.controller.ClienteController;
import com.ventas.tienda.controller.CreateEntytiesForTest;
import com.ventas.tienda.dto.cliente.ClienteDto;
import com.ventas.tienda.dto.cliente.ClienteMapper;
import com.ventas.tienda.dto.cliente.ClienteToSaveDto;
import com.ventas.tienda.service.cliente.ClienteService;
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

@WebMvcTest(ClienteController.class)
@ExtendWith(MockitoExtension.class)
class ClienteControllerTest extends CreateEntytiesForTest{

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    ObjectMapper objectMapper;

    Cliente cliente, cliente2;
    ClienteDto clienteDto;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .idCliente(1l)
                .nombreCliente("Walter Jiménez")
                .emailCliente("privado@privado.com")
                .direccionCliente("Santa Marta")
                .build();

        cliente2 = Cliente.builder()
                .nombreCliente("Andrés Licona")
                .emailCliente("privado2@privado.com")
                .direccionCliente("Santa Marta")
                .build();

        clienteDto = ClienteMapper.INSTANCE.toDto(cliente);

    }
    @Test
    void saveCliente_thenReturnClienteCreado() throws Exception {
        ClienteToSaveDto clienteToSaveDto = new ClienteToSaveDto(
                null,
                "Walter Jiménez",
                "privado@privado.com",
                "Santa Marta"
        );

        when(clienteService.guardarCliente(clienteToSaveDto)).thenReturn(clienteDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getClientes_thenReturnListOfClientes() throws Exception {
        when(clienteService.getAllCliente()).thenReturn(Collections.singletonList(clienteDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getClienteById_thenReturnCliente() throws Exception {
        long id = 1L;

        when(clienteService.buscarClientePorId(id)).thenReturn(clienteDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void actualizarCliente_thenReturnClienteActualizado() throws Exception {
        long id = 1L;
        ClienteToSaveDto clienteToSaveDto = new ClienteToSaveDto(
                null,
                "Walter Jiménez",
                "privado@privado.com",
                "Santa Marta"
        );

        when(clienteService.actualizarCliente(id, clienteToSaveDto)).thenReturn(clienteDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteCliente_thenReturnSuccessMessage() throws Exception {
        long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getClienteByEmail_thenReturnCliente() throws Exception {
        String email = "privado@privado.com";

        when(clienteService.buscarClientePorEmail(email)).thenReturn(clienteDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/email/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getClienteByCity_thenReturnListOfClientes() throws Exception {
        String city = "Santa Marta";

        when(clienteService.buscarClientesPorDireccion(city)).thenReturn(Collections.singletonList(clienteDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/city")
                        .param("city", city)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getClienteQueComiencenPor_thenReturnListOfClientes() throws Exception {
        String name = "Walter Jiménez";

        when(clienteService.buscarClientesQueComiencenPor(name)).thenReturn(Collections.singletonList(clienteDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/name")
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getClienteById_NotFoundException() throws Exception {
        long id = 1L;

        when(clienteService.buscarClientePorId(id)).thenThrow(new NotFoundExceptionEntity("Cliente no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void actualizarCliente_NotFoundException() throws Exception {
        long id = 1L;
        ClienteToSaveDto clienteToSaveDto = new ClienteToSaveDto(
                null,
                "Walter Jiménez",
                "privado@privado.com",
                "Santa Marta"
        );

        when(clienteService.actualizarCliente(id, clienteToSaveDto)).thenThrow(new NotFoundExceptionEntity("Cliente no encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteCliente_NotAbleToDeleteException() throws Exception {
        long id = 1L;

        doThrow(new NotAbleToDeleteException("Cliente no encontrado."))
                .when(clienteService).removercliente(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}