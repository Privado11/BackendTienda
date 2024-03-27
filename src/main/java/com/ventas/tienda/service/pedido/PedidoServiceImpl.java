package com.ventas.tienda.service.pedido;

import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.Entities.Pedido;
import com.ventas.tienda.dto.pedido.PedidoDto;
import com.ventas.tienda.dto.pedido.PedidoToSaveDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Override
    public PedidoDto guardarPedido(PedidoToSaveDto pago) {
        return null;
    }

    @Override
    public PedidoDto actualizarPedido(Long idPedido, PedidoToSaveDto pedido) {
        return null;
    }

    @Override
    public PedidoDto buscarPedidoPorId(Long idPedido) {
        return null;
    }

    @Override
    public void removerPedido(Long idPedido) {

    }

    @Override
    public List<PedidoDto> getAllItemPedidos() {
        return null;
    }

    @Override
    public List<PedidoDto> buscarPedidosEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal) {
        return null;
    }

    @Override
    public List<PedidoDto> buscarPedidoPorClienteYStatus(Cliente cliente, String status) {
        return null;
    }

    @Override
    public List<Pedido> BuscarPedidosyItemsPorCliente(Cliente cliente) {
        return null;
    }
}
