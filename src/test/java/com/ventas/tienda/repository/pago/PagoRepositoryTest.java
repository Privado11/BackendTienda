package com.ventas.tienda.repository.pago;

import com.ventas.tienda.Entities.Pago;
import com.ventas.tienda.repository.AbstractIntegrationBDTest;
import com.ventas.tienda.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PagoRepositoryTest extends AbstractIntegrationBDTest {

    PagoRepository pagoRepository;

    @Autowired
    public PagoRepositoryTest(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    Long idPedido;
    void initcMockPagos(){
        Pago pago = Pago.builder()
                .fechaPago(LocalDateTime.of(2023, 04, 10, 4, 12))
                .metodoPago("Tarjeta de credito")
                .pedido(pedidosList().get(0))
                .build();
        idPedido = pago.getPedido().getIdPedido();
        pagoRepository.save(pago);

        Pago pago2 = Pago.builder()
                .fechaPago(LocalDateTime.of(2024, 04, 10, 4, 12))
                .metodoPago("Tarjeta debito")
                .pedido(pedidosList().get(1))
                .build();

        pagoRepository.save(pago2);
        pagoRepository.flush();
    }

    @BeforeEach
    void setUp() {
        pagoRepository.deleteAll();
    }

    @Test
    void guardarPago(){
        Pago pago = Pago.builder()
                .fechaPago(LocalDateTime.of(2022, 04, 10, 4, 12))
                .metodoPago("Efectivo")
                .build();

        Pago pagoGuardado = pagoRepository.save(pago);

        System.out.println(pagoGuardado);

        assertThat(pagoGuardado).isNotNull();
    }

    @Test
    void pagosEntreFechas() {
        initcMockPagos();
        LocalDateTime fechaI = LocalDateTime.of(2023, 01, 10, 4, 12);
        LocalDateTime fechaF = LocalDateTime.of(2023, 12, 10, 4, 12);

        List<Pago> pagos = pagoRepository.pagosEntreFechas(fechaI, fechaF);

        System.out.println(pagos);
        assertThat(pagos).isNotEmpty();
        assertThat(pagos).hasSize(1);
    }

    @Test
    void pagosPorIdPedido() {
        initcMockPagos();

        Pago pago = pagoRepository.findByPedido_IdPedido(idPedido);

        System.out.println(pago);
        assertThat(pago).isNotNull();
    }
}