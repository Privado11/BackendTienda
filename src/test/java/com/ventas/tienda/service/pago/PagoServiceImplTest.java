package com.ventas.tienda.service.pago;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.ventas.tienda.Entities.Pago;
import com.ventas.tienda.Enum.MetodoPago;
import com.ventas.tienda.dto.pago.PagoDto;
import com.ventas.tienda.dto.pago.PagoMapper;
import com.ventas.tienda.dto.pago.PagoToSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.repository.PagoRepository;
import com.ventas.tienda.service.CreateEntytiesForTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceImplTest extends CreateEntytiesForTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PagoMapper pagoMapper;

    @InjectMocks
    private PagoServiceImpl pagoService;

    Pago pago, pago1;
    PagoDto pagoDto;

    @BeforeEach
    void setUp() {
        pago = pagoList().get(0);
        pago1 = pagoList().get(1);

        pagoDto = PagoMapper.INSTANCE.toDto(pago);
    }

    @Test
    void guardarPago() {
        PagoToSaveDto pagoToSaveDto = new PagoToSaveDto(
                null,
                50000.0,
                LocalDateTime.now(),
                MetodoPago.EFECTIVO,
                pedidosList().get(0)
                );
        when(pagoRepository.save(any())).thenReturn(pago);

       when(pagoMapper.toDto(any())).thenReturn(pagoDto);

        PagoDto pagoDtoG = pagoService.guardarPago(pagoToSaveDto);

        assertThat(pagoDtoG).isNotNull();
    }

    @Test
    void actualizarPago() throws NotFoundExceptionEntity {
        Long idPago = 1l;
        PagoToSaveDto pagoToSaveDto = new PagoToSaveDto(
                null,
                50000.0,
                LocalDateTime.now(),
                MetodoPago.EFECTIVO,
                pedidosList().get(0)
        );
        when(pagoRepository.findById(idPago)).thenReturn(Optional.of(pago));
        when(pagoRepository.save(any())).thenReturn(pago);

        when(pagoMapper.toDto(any())).thenReturn(pagoDto);

        PagoDto pagoDtoG = pagoService.actualizarPago(idPago, pagoToSaveDto);

        assertThat(pagoDtoG).isNotNull();

    }

    @Test
    void buscarPagoPorId() throws NotFoundExceptionEntity {
        Long idPago = 1l;
        when(pagoRepository.findById(idPago)).thenReturn(Optional.of(pago));

        when(pagoMapper.toDto(any())).thenReturn(pagoDto);

        PagoDto pagoDtoG = pagoService.buscarPagoPorId(idPago);

        assertThat(pagoDtoG).isNotNull();
    }

    @Test
    void removerPago() throws NotAbleToDeleteException {
        Long idPago = 1l;

        when(pagoRepository.findById(idPago)).thenReturn(Optional.of(pago));

        pagoService.removerPago(idPago);

        verify(pagoRepository, times(1)).delete(pago);
    }

    @Test
    void getAllItemPagos() {
        List<Pago> pagos = List.of(pago, pago1);

        when(pagoRepository.findAll()).thenReturn(pagos);

        List<PagoDto> pagoDtoList = pagoService.getAllPagos();

        assertThat(pagoDtoList).isNotEmpty();
        assertThat(pagoDtoList).hasSize(2);
    }

    @Test
    void buscarpagosEntreFechas() {
        LocalDateTime fechaInicial = LocalDateTime.of(2023, 12, 31,4 ,0);
        LocalDateTime fechaFinal = LocalDateTime.now();

        List<Pago> pagos = List.of(pago, pago1);

        when(pagoRepository.pagosEntreFechas(any(), any())).thenReturn(pagos);

        List<PagoDto> pagoDtoList = pagoService.buscarpagosEntreFechas(any(), any());

        assertThat(pagoDtoList).isNotEmpty();
        assertThat(pagoDtoList).hasSize(2);
    }

    @Test
    void buscarPagosPorIdPedido() throws NotFoundExceptionEntity {
        Long idPedido = 1l;

        when(pagoRepository.findByPedido_IdPedido(idPedido)).thenReturn(pago);

        when(pagoMapper.toDto(any())).thenReturn(pagoDto);

        PagoDto pagoDto = pagoService.buscarPagosPorIdPedido(idPedido);

        assertThat(pagoDto).isNotNull();
    }
}