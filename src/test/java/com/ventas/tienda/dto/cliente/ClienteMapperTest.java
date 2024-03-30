package com.ventas.tienda.dto.cliente;

import static org.assertj.core.api.Assertions.assertThat;

import com.ventas.tienda.Entities.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClienteMapperTest {

    @Autowired
    private ClienteMapper clienteMapper;



    @Test
    void toEntity() {
        ClienteDto clienteDto = new ClienteDto(1L, "Cliente", "cliente@example.com", "Dirección", null);
        Cliente cliente = clienteMapper.toEntity(clienteDto);

        assertThat(cliente).isNotNull();
        assertThat(clienteDto.idCliente()).isEqualTo(cliente.getIdCliente());
        assertThat(clienteDto.nombreCliente()).isEqualTo(cliente.getNombreCliente());
        assertThat(clienteDto.emailCliente()).isEqualTo(cliente.getEmailCliente());
        assertThat(clienteDto.direccionCliente()).isEqualTo(cliente.getDireccionCliente());
    }

    @Test
    void clienteToSaveDtotoEntity() {
        ClienteToSaveDto clienteToSaveDto = new ClienteToSaveDto(1L, "Cliente", "cliente@example.com", "Dirección");
        Cliente cliente = clienteMapper.clienteToSaveDtotoEntity(clienteToSaveDto);

        assertThat(cliente).isNotNull();
        assertThat(clienteToSaveDto.idCliente()).isEqualTo(cliente.getIdCliente());
        assertThat(clienteToSaveDto.nombreCliente()).isEqualTo(cliente.getNombreCliente());
        assertThat(clienteToSaveDto.emailCliente()).isEqualTo(cliente.getEmailCliente());
        assertThat(clienteToSaveDto.direccionCliente()).isEqualTo(cliente.getDireccionCliente());
    }

    @Test
    void toDto() {
        Cliente cliente = new Cliente(1L, "Cliente", "cliente@example.com", "Dirección", Collections.emptyList());
        ClienteDto clienteDto = clienteMapper.toDto(cliente);

        assertThat(clienteDto).isNotNull();
        assertThat(cliente.getIdCliente()).isEqualTo(clienteDto.idCliente());
        assertThat(cliente.getNombreCliente()).isEqualTo(clienteDto.nombreCliente());
        assertThat(cliente.getEmailCliente()).isEqualTo(clienteDto.emailCliente());
        assertThat(cliente.getDireccionCliente()).isEqualTo(clienteDto.direccionCliente());
    }
}