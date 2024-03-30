package com.ventas.tienda.service.pago;

import com.ventas.tienda.dto.pago.PagoDto;
import com.ventas.tienda.dto.pago.PagoToSaveDto;
import com.ventas.tienda.exception.EntityNotFoundException;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoService {
    PagoDto guardarPago(PagoToSaveDto pago);
    PagoDto actualizarPago(Long idPago, PagoToSaveDto pago) throws NotFoundExceptionEntity;
    PagoDto buscarPagoPorId(Long idPago) throws NotFoundExceptionEntity;
    void removerPago(Long idPago) throws NotAbleToDeleteException;
    List<PagoDto> getAllPagos();

    List<PagoDto> buscarpagosEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal);

    PagoDto buscarPagosPorIdPedido(Long idPedido) throws NotFoundExceptionEntity;
}
