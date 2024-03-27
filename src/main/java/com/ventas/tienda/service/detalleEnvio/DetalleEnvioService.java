package com.ventas.tienda.service.detalleEnvio;

import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioDto;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioToSaveDto;

import java.util.List;

public interface DetalleEnvioService {

    DetalleEnvioDto guardarDetalleEnvio(DetalleEnvioToSaveDto detalleEnvio);
    DetalleEnvioDto actualizarDetalleEnvio(Long idDetalleEnvio, DetalleEnvioToSaveDto detalleEnvio);
    DetalleEnvioDto buscarDetalleEnvioPorId(Long idDetalleEnvio);
    void removerDetalleEnvio(Long idDetalleEnvio);
    List<DetalleEnvioDto> getAllDetalleEnvio();

    List<DetalleEnvioDto> buscarDetallesEnvioPorIdPedido(Long idPedido);
    List<DetalleEnvioDto> buscarDetallesEnvioPorTransportadora(String transportadora);
    List<DetalleEnvioDto> buscarDetallesEnvioPorStatus(String status);
}
