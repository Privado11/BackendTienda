package com.ventas.tienda.service.pago;

import com.ventas.tienda.dto.pago.PagoDto;
import com.ventas.tienda.dto.pago.PagoToSaveDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    @Override
    public PagoDto guardarPago(PagoToSaveDto pago) {
        return null;
    }

    @Override
    public PagoDto actualizarPago(Long idPago, PagoToSaveDto pago) {
        return null;
    }

    @Override
    public PagoDto buscarPagoPorId(Long idPago) {
        return null;
    }

    @Override
    public void removerPago(Long idPago) {

    }

    @Override
    public List<PagoDto> getAllItemPagos() {
        return null;
    }

    @Override
    public List<PagoDto> buscarpagosEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        return null;
    }

    @Override
    public List<PagoDto> buscarPagosPorIdOrdenYMetodoPago(Long idPedido, String metodoPago) {
        return null;
    }
}
