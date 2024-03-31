package com.ventas.tienda.service.pedido;


import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.Entities.Pedido;
import com.ventas.tienda.Enum.StatusPedido;
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
                .status(StatusPedido.PENDIENTE)
                .idPedido(2L)
                .cliente(clienteList().get(0))
                .build();

        pedido2 = Pedido.builder()
                .status(StatusPedido.ENVIADO)
                .cliente(clienteList().get(1))
                .idPedido(2L)
                .build();

        pedido3 = Pedido.builder()
                .status(StatusPedido.ENTREGADO)
                .idPedido(2L)
                .cliente(clienteList().get(0))
                .build();

        pedidoDto = PedidoMapper.INSTANCE.toDto(pedido);
    }

    @Test
    void givenPedidoService_whenGuardarPedido_thenReturnPedidoGuardado(){
        when(pedidoRepository.save(any())).thenReturn(pedido);

        PedidoToSaveDto pedidoAGuardar = new PedidoToSaveDto(null,
                LocalDateTime.of(2023, 04, 10, 4, 12),
                StatusPedido.ENVIADO,
                clienteList().get(0));

        when(pedidoMapper.pedidoToSaveDtoToEntity(pedidoAGuardar)).thenReturn(pedido);

        when(pedidoMapper.toDto(any())).thenReturn(pedidoDto);

        PedidoDto pedidoGuardado = pedidoService.guardarPedido(pedidoAGuardar);

        assertThat(pedidoGuardado).isNotNull();
    }

    @Test
    void givenPedidoService_whenActualizarPedido_thenReturnPedidoActualizado() throws NotFoundExceptionEntity {
        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido2));

        PedidoToSaveDto pedidoA= new PedidoToSaveDto(null,
                LocalDateTime.of(2023, 04, 10, 4, 12),
                StatusPedido.ENVIADO,
                clienteList().get(1));

        when(pedidoMapper.toDto(any())).thenReturn(pedidoDto);;

        PedidoDto pedidoActualizado = pedidoService.actualizarPedido(1l, pedidoA);

        assertThat(pedidoActualizado).isNotNull();
    }

    @Test
    void givenPedidoService_whenBuscarPedidoPorId_thenReturnPedidoEncontrado() throws NotFoundExceptionEntity{
        when(pedidoRepository.findById(any())).thenReturn(Optional.of(pedido3));

        when(pedidoMapper.toDto(any())).thenReturn(pedidoDto);

        PedidoDto pedidoEncontrado = pedidoService.buscarPedidoPorId(1l);

        assertThat(pedidoEncontrado).isNotNull();
    }

    @Test
    void givenPedidoService_whenRemoverPedido_thenReturnPedidoRemovido() throws NotAbleToDeleteException {
        Long idPedido = 1l;
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        pedidoService.removerPedido(idPedido);

        verify(pedidoRepository, times(1)).delete(any());
    }

    @Test
    void givenPedidoService_whenGetAllPedidos_thenReturnListOfPedidos() {
        List<Pedido> pedidos = List.of(pedido, pedido2, pedido3);

        when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<PedidoDto> pedidosDto = pedidoService.getAllPedidos();

        assertThat(pedidosDto).isNotEmpty();
        assertThat(pedidosDto).hasSize(3);
    }

    @Test
    void givenPedidoService_whenBuscarPedidosEntreFechas_thenReturnListOfPedidos(){
        List<Pedido> pedidos = List.of(pedido, pedido);

        when(pedidoRepository.pedidosEntreFechas(any(), any())).thenReturn(pedidos);

        List<PedidoDto> pedidoDtoList = pedidoService.buscarPedidosEntreFechas(any(), any());

        assertThat(pedidoDtoList).hasSize(2);

    }

    @Test
    void givenPedidoService_whenBuscarPedidoPorClienteYStatus_thenReturnListOfPedidos() {
        Cliente cliente = clienteList().get(0);
        StatusPedido statusPedido = StatusPedido.PENDIENTE;
        List<Pedido> pedidos = List.of(pedido);

        when(pedidoRepository.findByClienteAndStatus(cliente, statusPedido)).thenReturn(pedidos);

        List<PedidoDto> pedidoDtoList = pedidoService.buscarPedidoPorClienteYStatus(cliente, statusPedido);

        assertThat(pedidoDtoList).hasSize(1);
    }

    @Test
    void givenPedidoService_whenBuscarPedidosyItemsPorCliente_thenReturnListOfPedidos() {
        Long idCliente = clienteList().get(0).getIdCliente();

        List<Pedido> pedidos = List.of(pedido);

        when(pedidoRepository.pedidosyItemsPorCliente(idCliente)).thenReturn(pedidos);

        List<PedidoDto> pedidoDtoList = pedidoService.BuscarPedidosyItemsPorCliente(idCliente);

        assertThat(pedidoDtoList).hasSize(1);

    }
}