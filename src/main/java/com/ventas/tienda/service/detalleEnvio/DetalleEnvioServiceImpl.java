package com.ventas.tienda.service.detalleEnvio;

import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioDto;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioToSaveDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleEnvioServiceImpl implements DetalleEnvioService {

    @Override
    public DetalleEnvioDto guardarDetalleEnvio(DetalleEnvioToSaveDto detalleEnvio) {
        return null;
    }

    @Override
    public DetalleEnvioDto actualizarDetalleEnvio(Long idDetalleEnvio, DetalleEnvioToSaveDto detalleEnvio) {
        return null;
    }

    @Override
    public DetalleEnvioDto buscarDetalleEnvioPorId(Long idDetalleEnvio) {
        return null;
    }

    @Override
    public void removerDetalleEnvio(Long idDetalleEnvio) {

    }

    @Override
    public List<DetalleEnvioDto> getAllDetalleEnvio() {
        return null;
    }

    @Override
    public List<DetalleEnvioDto> buscarDetallesEnvioPorIdPedido(Long idPedido) {
        return null;
    }

    @Override
    public List<DetalleEnvioDto> buscarDetallesEnvioPorTransportadora(String transportadora) {
        return null;
    }

    @Override
    public List<DetalleEnvioDto> buscarDetallesEnvioPorStatus(String status) {
        return null;
    }
}
