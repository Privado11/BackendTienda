package com.ventas.tienda.service.pedido;


import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.Entities.Pedido;
import com.ventas.tienda.dto.pedido.PedidoDto;
import com.ventas.tienda.dto.pedido.PedidoMapper;
import com.ventas.tienda.dto.pedido.PedidoToSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.repository.PedidoRepository;
import com.ventas.tienda.service.CreateEntytiesForTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest extends CreateEntytiesForTest {
    @Mock
    private PedidoMapper pedidoMapper;
    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    private Pedido pedido, pedido2, pedido3;
    private PedidoDto pedidoDto;

    @BeforeEach
    void setUp() {
        pedido = Pedido.builder()
                .status("pagado")
                .idPedido(2L)
                .cliente(clienteList().get(0))
                .build();

        pedido2 = Pedido.builder()
                .status("pendiente")
                .cliente(clienteList().get(1))
                .idPedido(2L)
                .build();

        pedido3 = Pedido.builder()
                .status("activo")
                .idPedido(2L)
                .cliente(clienteList().get(0))
                .build();

        pedidoDto = PedidoMapper.INSTANCE.toDto(pedido);
    }

    @Test
    void guardarPedido() {
        when(pedidoRepository.save(any())).thenReturn(pedido);

        PedidoToSaveDto pedidoAGuardar = new PedidoToSaveDto(null,
                LocalDateTime.of(2023, 04, 10, 4, 12),
                clienteList().get(0),
                pagoList().get(0),
                detalleEnvioList().get(0));
        when(pedidoMapper.pedidoToSaveDtoToEntity(pedidoAGuardar)).thenReturn(pedido);

        when(pedidoMapper.toDto(any())).thenReturn(pedidoDto);

        PedidoDto pedidoGuardado = pedidoService.guardarPedido(pedidoAGuardar);

        assertThat(pedidoGuardado).isNotNull();
    }

    @Test
    void actualizarPedido() throws NotFoundExceptionEntity {
        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido2));

        PedidoToSaveDto pedidoA= new PedidoToSaveDto(null,
                LocalDateTime.of(2023, 04, 10, 4, 12),
                clienteList().get(1),
                pagoList().get(1),
                detalleEnvioList().get(1));

        when(pedidoMapper.toDto(any())).thenReturn(pedidoDto);;

        PedidoDto pedidoActualizado = pedidoService.actualizarPedido(1l, pedidoA);

        assertThat(pedidoActualizado).isNotNull();
    }

    @Test
    void buscarPedidoPorId() throws NotFoundExceptionEntity{
        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido3));

        when(pedidoMapper.toDto(any())).thenReturn(pedidoDto);

        PedidoDto pedidoEncontrado = pedidoService.buscarPedidoPorId(1l);

        assertThat(pedidoEncontrado).isNotNull();
    }

    @Test
    void removerPedido() throws NotAbleToDeleteException {
        Long idPedido = 1l;
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        pedidoService.removerPedido(idPedido);

        verify(pedidoRepository, times(1)).delete(any());
    }

    @Test
    void getAllItemPedidos() {
        List<Pedido> pedidos = List.of(pedido, pedido2, pedido3);

        when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<PedidoDto> pedidosDto = pedidoService.getAllPedidos();

        assertThat(pedidosDto).isNotEmpty();
        assertThat(pedidosDto).hasSize(3);
    }

    @Test
    void buscarPedidosEntreFechas(){
        List<Pedido> pedidos = List.of(pedido, pedido);

        when(pedidoRepository.pedidosEntreFechas(any(), any())).thenReturn(pedidos);

        List<PedidoDto> pedidoDtoList = pedidoService.buscarPedidosEntreFechas(any(), any());

        assertThat(pedidoDtoList).hasSize(2);

    }

    @Test
    void buscarPedidoPorClienteYStatus() {
        Cliente cliente = clienteList().get(0);
        String statusPedido = "pagado";
        List<Pedido> pedidos = List.of(pedido);

        when(pedidoRepository.findByClienteAndStatus(cliente, statusPedido)).thenReturn(pedidos);

        List<PedidoDto> pedidoDtoList = pedidoService.buscarPedidoPorClienteYStatus(cliente, statusPedido);

        assertThat(pedidoDtoList).hasSize(1);
    }

    @Test
    void buscarPedidosyItemsPorCliente() {
        Long idCliente = clienteList().get(0).getIdCliente();

        List<Pedido> pedidos = List.of(pedido);

        when(pedidoRepository.pedidosyItemsPorCliente(idCliente)).thenReturn(pedidos);

        List<PedidoDto> pedidoDtoList = pedidoService.BuscarPedidosyItemsPorCliente(idCliente);

        assertThat(pedidoDtoList).hasSize(1);

    }
}