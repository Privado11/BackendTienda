package com.ventas.tienda.service.detalleEnvio;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.ventas.tienda.Entities.DetalleEnvio;
import com.ventas.tienda.Enum.StatusPedido;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioDto;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioMapper;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioToSaveDto;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.repository.DetalleEnvioRepository;
import com.ventas.tienda.service.CreateEntytiesForTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DetalleEnvioServiceImplTest extends CreateEntytiesForTest {

    @Mock
    private DetalleEnvioRepository detalleEnvioRepository;

    @Mock
    private DetalleEnvioMapper detalleEnvioMapper;

    @InjectMocks
    private DetalleEnvioServiceImpl detalleEnvioService;

    DetalleEnvio detalleEnvio, detalleEnvio2;
    DetalleEnvioDto detalleEnvioDto;

    @BeforeEach
    void setUp() {
        detalleEnvio = detalleEnvioList().get(0);
        detalleEnvio2 = detalleEnvioList().get(1);

        detalleEnvioDto = DetalleEnvioMapper.INSTANCE.toDto(detalleEnvio);
    }

    @Test
    void givenDetalleEnvioService_whenGuardarDetalleEnvio_thenReturnDetalleEnvioGuardado() {
        when(detalleEnvioRepository.save(any())).thenReturn(detalleEnvio);

        DetalleEnvioToSaveDto detalleEnvioToSaveDto = new DetalleEnvioToSaveDto(
                null,
                "Calle 29",
                "Coordinadora",
                "GAF52623",
                null
        );

        when(detalleEnvioMapper.toDto(any())).thenReturn(detalleEnvioDto);

        DetalleEnvioDto detalleDtoG = detalleEnvioService.guardarDetalleEnvio(detalleEnvioToSaveDto);

        assertThat(detalleDtoG).isNotNull();
    }

    @Test
    void givenDetalleEnvioService_whenActualizarDetalleEnvio_thenReturnDetalleEnvioActualizado() throws NotFoundExceptionEntity {
        Long idDetalle = 1l;

        DetalleEnvioToSaveDto detalleEnvioToSaveDto = new DetalleEnvioToSaveDto(
                null,
                "Calle 29",
                "Coordinadora",
                "GAF52623",
                null
        );

        when(detalleEnvioRepository.findById(idDetalle)).thenReturn(Optional.of(detalleEnvio));
        when(detalleEnvioRepository.save(any())).thenReturn(detalleEnvio);

        when(detalleEnvioMapper.toDto(any())).thenReturn(detalleEnvioDto);

        DetalleEnvioDto detalleDtoG = detalleEnvioService.actualizarDetalleEnvio(idDetalle, detalleEnvioToSaveDto);

        assertThat(detalleDtoG).isNotNull();
    }

    @Test
    void givenDetalleEnvioService_whenBuscarDetalleEnvioPorId_thenReturnDetalleEnvioEncontrado() throws NotFoundExceptionEntity {
        Long idDetalle = 1l;

        when(detalleEnvioRepository.findById(idDetalle)).thenReturn(Optional.of(detalleEnvio));

        when(detalleEnvioMapper.toDto(any())).thenReturn(detalleEnvioDto);

        DetalleEnvioDto detalleDtoG = detalleEnvioService.buscarDetalleEnvioPorId(idDetalle);

        assertThat(detalleDtoG).isNotNull();
    }

    @Test
    void givenDetalleEnvioService_whenRemoverDetalleEnvio_thenMensaje(){
        Long idDetalle = 1l;

        when(detalleEnvioRepository.findById(idDetalle)).thenReturn(Optional.of(detalleEnvio));

        detalleEnvioService.removerDetalleEnvio(idDetalle);

        verify(detalleEnvioRepository, times(1)).delete(detalleEnvio);
    }

    @Test
    void givenDetalleEnvioService_whenGetAllDetalleEnvio_thenReturnListOfDetalleEnvio() {
        List<DetalleEnvio> detalleEnvioList = List.of(detalleEnvio, detalleEnvio2);

        when(detalleEnvioRepository.findAll()).thenReturn(detalleEnvioList);

        List<DetalleEnvioDto> detalleEnvioDtos = detalleEnvioService.getAllDetalleEnvio();

        assertThat(detalleEnvioDtos).isNotEmpty();
        assertThat(detalleEnvioDtos).hasSize(2);
    }

    @Test
    void givenDetalleEnvioService_whenBuscarDetallesEnvioPorIdPedido_thenReturnDetalleEnvioEncontrado() throws NotFoundExceptionEntity {
        Long idPedido = 1l;

        when(detalleEnvioRepository.findByPedido_IdPedido(idPedido)).thenReturn(detalleEnvio);

        when(detalleEnvioMapper.toDto(any())).thenReturn(detalleEnvioDto);

        DetalleEnvioDto detalleEnvioDtos = detalleEnvioService.buscarDetallesEnvioPorIdPedido(idPedido);

        assertThat(detalleEnvioDtos).isNotNull();
    }

    @Test
    void givenDetalleEnvioService_whenBuscarDetallesEnvioPorTransportadora_thenReturnListOfDetalleEnvio() {
        String transportadora = "coordinadora";

        List<DetalleEnvio> detalleEnvioList = List.of(detalleEnvio);

        when(detalleEnvioRepository.findByTransportadoraEnvioLike(transportadora)).thenReturn(detalleEnvioList);

        when(detalleEnvioMapper.toDto(any())).thenReturn(detalleEnvioDto);

        List<DetalleEnvioDto> detalleEnvioDtos = detalleEnvioService.buscarDetallesEnvioPorTransportadora(transportadora);

        assertThat(detalleEnvioDtos).isNotEmpty();
        assertThat(detalleEnvioDtos).hasSize(1);
    }

    @Test
    void givenDetalleEnvioService_whenBuscarDetallesEnvioPorStatus_thenReturnListOfDetalleEnvio() {
        StatusPedido statusPedido = StatusPedido.ENVIADO;

        List<DetalleEnvio> detalleEnvioList = List.of(detalleEnvio);

        when(detalleEnvioRepository.findByPedido_Status(statusPedido)).thenReturn(detalleEnvioList);

        List<DetalleEnvioDto> detalleEnvioDtos = detalleEnvioService.buscarDetallesEnvioPorStatus(statusPedido);

        assertThat(detalleEnvioDtos).isNotEmpty();
        assertThat(detalleEnvioDtos).hasSize(1);
    }
}