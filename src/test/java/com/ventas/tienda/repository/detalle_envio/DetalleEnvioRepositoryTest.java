package com.ventas.tienda.repository.detalle_envio;

import com.ventas.tienda.Entities.DetalleEnvio;
import com.ventas.tienda.Enum.StatusPedido;
import com.ventas.tienda.repository.AbstractIntegrationBDTest;
import com.ventas.tienda.repository.DetalleEnvioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DetalleEnvioRepositoryTest extends AbstractIntegrationBDTest {

    DetalleEnvioRepository detalleEnvioRepository;

    @Autowired
    public DetalleEnvioRepositoryTest(DetalleEnvioRepository detalleEnvioRepository) {
        this.detalleEnvioRepository = detalleEnvioRepository;
    }

    Long idPedido;

    void inictMockDetallesEnvio(){
        DetalleEnvio detalleEnvio = DetalleEnvio.builder()
                .transportadoraEnvio("Coordinadora")
                .numeroGuiaEnvio("ACS3447")
                .direccionEnvio("Calle 29")
                .pedido(pedidosList().get(0))
                .build();
        idPedido = detalleEnvio.getPedido().getIdPedido();
        detalleEnvioRepository.save(detalleEnvio);

        DetalleEnvio detalleEnvio2 = DetalleEnvio.builder()
                .transportadoraEnvio("Envia")
                .numeroGuiaEnvio("Ayh37443")
                .direccionEnvio("Calle 45")
                .pedido(pedidosList().get(1))
                .build();
        detalleEnvioRepository.save(detalleEnvio2);
        detalleEnvioRepository.flush();
    }



    @BeforeEach
    void setUp() {
        detalleEnvioRepository.deleteAll();
    }

    @Test
    void givenDetallesEnvio_whenGuardarDetalleEnvio_thenDetalleEnvioGuardado(){
        DetalleEnvio detalleEnvio = DetalleEnvio.builder()
                .transportadoraEnvio("Coordinadora")
                .numeroGuiaEnvio("ACS34423")
                .direccionEnvio("Calle 26")
                .pedido(pedidosList().get(0))
                .build();
        DetalleEnvio detalleEnvioGuardado = detalleEnvioRepository.save(detalleEnvio);

        System.out.println(detalleEnvioGuardado);

        assertThat(detalleEnvioGuardado).isNotNull();

    }

    @Test
    void givenDetallesEnvio_whenFindByPedido_IdPedido_thenReturnDetalleEnvioForPedido() {
        inictMockDetallesEnvio();

        DetalleEnvio detalleEnvioE = detalleEnvioRepository.findByPedido_IdPedido(idPedido);

        System.out.println(detalleEnvioE);
        assertThat(detalleEnvioE).isNotNull();
    }

    @Test
    void givenDetallesEnvio_whenFindByTransportadoraEnvioLike_thenReturnListOfDetalleEnvio(){
        inictMockDetallesEnvio();
        String transportadoraEnvio = "Coordinadora";

        List<DetalleEnvio> detalleEnvioList = detalleEnvioRepository.findByTransportadoraEnvioLike(transportadoraEnvio);

        System.out.println(detalleEnvioList);
        assertThat(detalleEnvioList).isNotEmpty();
    }

    @Test
    void givenDetallesEnvio_whenFindByPedido_Status_thenReturnListOfDetalleEnvio() {
        inictMockDetallesEnvio();
        StatusPedido statusPedido = StatusPedido.ENVIADO;

        List<DetalleEnvio> detalleEnvioList = detalleEnvioRepository.findByPedido_Status(statusPedido);

        System.out.println(detalleEnvioList);
        assertThat(detalleEnvioList).isNotEmpty();
    }
}