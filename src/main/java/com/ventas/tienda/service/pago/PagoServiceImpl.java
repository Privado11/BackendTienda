package com.ventas.tienda.service.pago;

import com.ventas.tienda.Entities.Pago;
import com.ventas.tienda.dto.pago.PagoDto;
import com.ventas.tienda.dto.pago.PagoMapper;
import com.ventas.tienda.dto.pago.PagoToSaveDto;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;

    private PagoMapper pagoMapper;

    @Autowired
    public PagoServiceImpl(PagoRepository pagoRepository, PagoMapper pagoMapper) {
        this.pagoRepository = pagoRepository;
        this.pagoMapper = pagoMapper;
    }

    @Override
    public PagoDto guardarPago(PagoToSaveDto pago) {
        Pago pagoG = pagoMapper.pagoTosaveDtoToEntity(pago);
        return pagoMapper.toDto(pagoG);
    }

    @Override
    public PagoDto actualizarPago(Long idPago, PagoToSaveDto pago) throws NotFoundExceptionEntity {
        return pagoRepository.findById(idPago).
                map( pagoE -> {
                            pagoE.setFechaPago(pago.fechaPago());
                            pagoE.setMetodoPago(pago.metodoPago());
                            pagoE.setPedido(pago.pedido());
                            pagoE.setTotalpago(pago.totalPago());

                            Pago pagoG = pagoRepository.save(pagoE);

                            return  pagoMapper.toDto(pagoE);
                        }
                ).orElseThrow(() -> new NotFoundExceptionEntity("Pago no encontrado."));
    }

    @Override
    public PagoDto buscarPagoPorId(Long idPago) throws NotFoundExceptionEntity {
        Pago pago = pagoRepository.findById(idPago)
                .orElseThrow(() -> new NotFoundExceptionEntity("Pago no encontrado."));

        return pagoMapper.toDto(pago);
    }

    @Override
    public void removerPago(Long idPago) throws NotFoundExceptionEntity {
        Pago pago = pagoRepository.findById(idPago).
                orElseThrow(() -> new NotFoundExceptionEntity("Pago no encontrado."));

        pagoRepository.delete(pago);
    }

    @Override
    public List<PagoDto> getAllItemPagos() {
        return pagoRepository.findAll()
                .stream()
                .map(pago -> pagoMapper.toDto(pago))
                .toList();
    }

    @Override
    public List<PagoDto> buscarpagosEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        return pagoRepository.pagosEntreFechas(fechaInicial, fechaFinal)
                .stream()
                .map(pago -> pagoMapper.toDto(pago))
                .toList();
    }

    @Override
    public List<PagoDto> buscarPagosPorIdOrdenYMetodoPago(Long idPedido, String metodoPago) {
        return pagoRepository.pagosPorIdOrdenYMetodoPago(idPedido, metodoPago)
                .stream()
                .map(pago -> pagoMapper.toDto(pago))
                .toList();
    }
}
