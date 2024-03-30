package com.ventas.tienda.dto.pago;

import static org.assertj.core.api.Assertions.assertThat;

import com.ventas.tienda.Entities.Pago;
import com.ventas.tienda.Enum.MetodoPago;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PagoMapperTest {

    @Autowired
   private PagoMapper pagoMapper;

    @Test
    void toEntity() {
        PagoDto pagoDto = new PagoDto(1L, 100.0, LocalDateTime.now(), "TARJETA_CREDITO");
        Pago pago = pagoMapper.toEntity(pagoDto);

        assertThat(pago).isNotNull();
        assertThat(pagoDto.idPago()).isEqualTo(pago.getIdPago());
        assertThat(pagoDto.totalPago()).isEqualTo(pago.getTotalPago());
        assertThat(pagoDto.fechaPago()).isEqualTo(pago.getFechaPago());
    }

    @Test
    void pagoTosaveDtoToEntity() {
        PagoToSaveDto pagoToSaveDto = new PagoToSaveDto(1L, 100.0, LocalDateTime.now(), MetodoPago.TARJETA_CREDITO, null);
        Pago pago = pagoMapper.pagoTosaveDtoToEntity(pagoToSaveDto);

        assertThat(pago).isNotNull();
        assertThat(pagoToSaveDto.idPago()).isEqualTo(pago.getIdPago());
        assertThat(pagoToSaveDto.totalPago()).isEqualTo(pago.getTotalPago());
        assertThat(pagoToSaveDto.fechaPago()).isEqualTo(pago.getFechaPago());
        assertThat(pagoToSaveDto.metodoPago()).isEqualTo(pago.getMetodoPago());
    }

    @Test
    void toDto() {
        Pago pago = new Pago(1L, 100.0, LocalDateTime.now(), MetodoPago.DAVIPLATA, null);
        PagoDto pagoDto = pagoMapper.toDto(pago);

        assertThat(pagoDto).isNotNull();
        assertThat(pago.getIdPago()).isEqualTo(pagoDto.idPago());
        assertThat(pago.getTotalPago()).isEqualTo(pagoDto.totalPago());
        assertThat(pago.getFechaPago()).isEqualTo(pagoDto.fechaPago());
        assertThat(pago.getMetodoPago().name()).isEqualTo(pagoDto.metodoPago());
    }
}