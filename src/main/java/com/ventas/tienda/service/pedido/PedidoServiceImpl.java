package com.ventas.tienda.service.pedido;

import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.Entities.Pago;
import com.ventas.tienda.Entities.Pedido;

import com.ventas.tienda.Enum.StatusPedido;
import com.ventas.tienda.dto.pedido.PedidoDto;
import com.ventas.tienda.dto.pedido.PedidoMapper;
import com.ventas.tienda.dto.pedido.PedidoToSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
    private final PedidoRepository pedidoRepository;

    private PedidoMapper pedidoMapper;
    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }
    @Override
    public PedidoDto guardarPedido(PedidoToSaveDto pedidoDto) {
        Pedido pedido = pedidoMapper.pedidoToSaveDtoToEntity(pedidoDto);
        return pedidoMapper.toDto(pedidoRepository.save(pedido));
    }


    @Override
    public PedidoDto actualizarPedido(Long idPedido, PedidoToSaveDto pedido) throws  NotFoundExceptionEntity {
        return pedidoRepository.findById(idPedido)
                .map(pedidoE->{
                        pedidoE.setFechaPedido(pedido.fechaPedido());
                        pedidoE.setCliente(pedido.cliente());
                        Pedido pedidoNew = pedidoRepository.save(pedidoE);

                        return pedidoMapper.toDto(pedidoNew);
                    }
                ).orElseThrow(() -> new NotFoundExceptionEntity("Pedido no encontrado."));
    }


    @Override
    public PedidoDto buscarPedidoPorId(Long idPedido) throws  NotFoundExceptionEntity {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(()->new NotFoundExceptionEntity("Pedido no encontrado"));
        return pedidoMapper.toDto(pedido);
    }

    @Override
    public void removerPedido(Long idPedido) throws NotAbleToDeleteException {
        Pedido pedido = pedidoRepository.findById((idPedido))
                .orElseThrow(() -> new NotAbleToDeleteException("Pedido no encontrado."));
        pedidoRepository.delete(pedido);
    }

    @Override
    public List<PedidoDto> getAllPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedido -> pedidoMapper.toDto(pedido))
                .toList();
    }

    @Override
    public List<PedidoDto> buscarPedidosEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        return pedidoRepository.pedidosEntreFechas(fechaInicial,fechaFinal)
                .stream()
                .map(pedido->pedidoMapper.toDto(pedido))
                .toList();
    }

    @Override
    public List<PedidoDto> buscarPedidoPorClienteYStatus(Cliente cliente, StatusPedido status) {
        return pedidoRepository.findByClienteAndStatus(cliente, status)
                .stream()
                .map(pedido -> pedidoMapper.toDto(pedido))
                .toList();
    }

    @Override
    public List<PedidoDto> BuscarPedidosyItemsPorCliente(Long idCliente) {
        return pedidoRepository.pedidosyItemsPorCliente(idCliente)
                .stream()
                .map(pedido -> pedidoMapper.toDto(pedido))
                .toList();
    }
}
