package com.ventas.tienda.service.detalleEnvio;

import com.ventas.tienda.Entities.DetalleEnvio;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioDto;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioMapper;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioToSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.repository.DetalleEnvioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DetalleEnvioServiceImpl implements DetalleEnvioService {
    private final DetalleEnvioRepository detalleEnvioRepository;
    private DetalleEnvioMapper detalleEnvioMapper;

    @Autowired
    public DetalleEnvioServiceImpl(DetalleEnvioRepository detalleEnvioRepository, DetalleEnvioMapper detalleEnvioMapper) {
        this.detalleEnvioRepository = detalleEnvioRepository;
        this.detalleEnvioMapper = detalleEnvioMapper;
    }

    @Override
    public DetalleEnvioDto guardarDetalleEnvio(DetalleEnvioToSaveDto detalleEnvio) {
        DetalleEnvio detalleEnvioG = detalleEnvioMapper.DetalleEnvioToSaveDtoToEntity(detalleEnvio);
        return detalleEnvioMapper.toDto(detalleEnvioRepository.save(detalleEnvioG));
    }

    @Override
    public DetalleEnvioDto actualizarDetalleEnvio(Long idDetalleEnvio, DetalleEnvioToSaveDto detalleEnvio) {
        return detalleEnvioRepository.findById(idDetalleEnvio)
                .map(detalleEnvioE -> {
                            detalleEnvioE.setDireccionEnvio(detalleEnvio.direccionEnvio());
                            detalleEnvioE.setTransportadoraEnvio(detalleEnvio.transportadoraEnvio());
                            detalleEnvioE.setNumeroGuiaEnvio(detalleEnvio.numeroGuiaEnvio());
                            detalleEnvioE.setPedido(detalleEnvio.pedido());

                            DetalleEnvio detalleEnvioG = detalleEnvioRepository.save(detalleEnvioE);

                    return detalleEnvioMapper.toDto(detalleEnvioG);
                }).orElseThrow(() -> new EntityNotFoundException("DetalleEnvio no encontrado."));
    }

    @Override
    public DetalleEnvioDto buscarDetalleEnvioPorId(Long idDetalleEnvio) {
        DetalleEnvio detalleEnvioE = detalleEnvioRepository.findById(idDetalleEnvio)
                .orElseThrow(() -> new EntityNotFoundException("DetalleEnvio no encontrado."));
        return detalleEnvioMapper.toDto(detalleEnvioE);
    }

    @Override
    public void removerDetalleEnvio(Long idDetalleEnvio) {
        DetalleEnvio detalleEnvioE = detalleEnvioRepository.findById(idDetalleEnvio)
                .orElseThrow(() -> new NotAbleToDeleteException("DetalleEnvio no encontrado."));
        detalleEnvioRepository.delete(detalleEnvioE);

    }

    @Override
    public List<DetalleEnvioDto> getAllDetalleEnvio() {
        return detalleEnvioRepository.findAll().stream()
                .map(detalleEnvio -> detalleEnvioMapper.toDto(detalleEnvio))
                .toList();
    }

    @Override
    public List<DetalleEnvioDto> buscarDetallesEnvioPorIdPedido(Long idPedido) {
        return  detalleEnvioRepository.findByPedido_IdPedido(idPedido)
                .stream()
                .map(detalleEnvio -> detalleEnvioMapper.toDto(detalleEnvio))
                .toList();
    }

    @Override
    public List<DetalleEnvioDto> buscarDetallesEnvioPorTransportadora(String transportadora) {
        return  detalleEnvioRepository.findByTransportadoraEnvioLike(transportadora)
                .stream()
                .map(detalleEnvio -> detalleEnvioMapper.toDto(detalleEnvio))
                .toList();
    }

    @Override
    public List<DetalleEnvioDto> buscarDetallesEnvioPorStatus(String status) {
        return  detalleEnvioRepository.findByPedido_Status(status)
                .stream()
                .map(detalleEnvio -> detalleEnvioMapper.toDto(detalleEnvio))
                .toList();
    }
}
