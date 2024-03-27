package com.ventas.tienda.service.pago;

import com.ventas.tienda.dto.pago.PagoDto;
import com.ventas.tienda.dto.pago.PagoToSaveDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoService {
    PagoDto guardarPago(PagoToSaveDto pago);
    PagoDto actualizarPago(Long idPago, PagoToSaveDto pago);
    PagoDto buscarPagoPorId(Long idPago);
    void removerPago(Long idPago);
    List<PagoDto> getAllItemPagos();

    List<PagoDto> buscarpagosEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal);

    List<PagoDto> buscarPagosPorIdOrdenYMetodoPago(Long idPedido, String metodoPago);
}
