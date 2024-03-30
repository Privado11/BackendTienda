package com.ventas.tienda.service.detalleEnvio;

import com.ventas.tienda.Enum.StatusPedido;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioDto;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioToSaveDto;
import com.ventas.tienda.exception.NotFoundExceptionEntity;

import java.util.List;

public interface DetalleEnvioService {

    DetalleEnvioDto guardarDetalleEnvio(DetalleEnvioToSaveDto detalleEnvio);
    DetalleEnvioDto actualizarDetalleEnvio(Long idDetalleEnvio, DetalleEnvioToSaveDto detalleEnvio) throws NotFoundExceptionEntity;
    DetalleEnvioDto buscarDetalleEnvioPorId(Long idDetalleEnvio) throws NotFoundExceptionEntity;
    void removerDetalleEnvio(Long idDetalleEnvio);
    List<DetalleEnvioDto> getAllDetalleEnvio();

    DetalleEnvioDto buscarDetallesEnvioPorIdPedido(Long idPedido) throws NotFoundExceptionEntity;
    List<DetalleEnvioDto> buscarDetallesEnvioPorTransportadora(String transportadora);
    List<DetalleEnvioDto> buscarDetallesEnvioPorStatus(StatusPedido status);
}
