package com.ventas.tienda.dto.detalleEnvio;

import static org.assertj.core.api.Assertions.assertThat;

import com.ventas.tienda.Entities.DetalleEnvio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DetalleEnvioMapperTest {

    @Autowired
    private DetalleEnvioMapper detalleEnvioMapper;

    @Test
    void toEntity() {
        DetalleEnvioDto detalleEnvioDto = new DetalleEnvioDto(1L, "Santa Marta", "Transportadora", "Número de guía");
        DetalleEnvio detalleEnvio = detalleEnvioMapper.toEntity(detalleEnvioDto);

        assertThat(detalleEnvio).isNotNull();
        assertThat(detalleEnvioDto.idDetalleEnvio()).isEqualTo(detalleEnvio.getIdDetalleEnvio());
        assertThat(detalleEnvioDto.direccionEnvio()).isEqualTo(detalleEnvio.getDireccionEnvio());
        assertThat(detalleEnvioDto.transportadoraEnvio()).isEqualTo(detalleEnvio.getTransportadoraEnvio());
        assertThat(detalleEnvioDto.numeroGuiaEnvio()).isEqualTo(detalleEnvio.getNumeroGuiaEnvio());
    }

    @Test
    void detalleEnvioToSaveDtoToEntity() {
        DetalleEnvioToSaveDto detalleEnvioToSaveDto = new DetalleEnvioToSaveDto(1L, "Dirección de envío", "Transportadora", "Número de guía", null);
        DetalleEnvio detalleEnvio = detalleEnvioMapper.DetalleEnvioToSaveDtoToEntity(detalleEnvioToSaveDto);

        assertThat(detalleEnvio).isNotNull();
        assertThat(detalleEnvioToSaveDto.direccionEnvio()).isEqualTo(detalleEnvio.getDireccionEnvio());
        assertThat(detalleEnvioToSaveDto.transportadoraEnvio()).isEqualTo(detalleEnvio.getTransportadoraEnvio());
        assertThat(detalleEnvioToSaveDto.numeroGuiaEnvio()).isEqualTo(detalleEnvio.getNumeroGuiaEnvio());
    }

    @Test
    void toDto() {
        DetalleEnvio detalleEnvio = new DetalleEnvio(1L, "Dirección de envío", "Transportadora", "Número de guía", null);
        DetalleEnvioDto detalleEnvioDto = detalleEnvioMapper.toDto(detalleEnvio);

        assertThat(detalleEnvioDto).isNotNull();

        assertThat(detalleEnvio.getDireccionEnvio()).isEqualTo(detalleEnvioDto.direccionEnvio());
        assertThat(detalleEnvio.getTransportadoraEnvio()).isEqualTo(detalleEnvioDto.transportadoraEnvio());
        assertThat(detalleEnvio.getNumeroGuiaEnvio()).isEqualTo(detalleEnvioDto.numeroGuiaEnvio());
    }
}