package com.ventas.tienda.repository.pedido;

import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.Entities.Pedido;
import com.ventas.tienda.Enum.StatusPedido;
import com.ventas.tienda.repository.AbstractIntegrationBDTest;
import com.ventas.tienda.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoRepositoryTest extends AbstractIntegrationBDTest {

    PedidoRepository pedidoRepository;

    @Autowired
    public PedidoRepositoryTest(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    } 

    List<Cliente> clientes;
    Pedido pedido, pedido2;

    void inictMockPedidos() {
        clientes = clienteList();
        pedido = Pedido.builder()
                .status(StatusPedido.ENVIADO)
                .cliente(clientes.get(0))
                .itemsPedido(itemPedidoList())
                .fechaPedido(LocalDateTime.of(2023, 04, 10, 4, 12))
                .build();
        pedidoRepository.save(pedido);

        pedido2 = Pedido.builder()
                .status(StatusPedido.PENDIENTE)
                .cliente(clientes.get(0))
                .itemsPedido(List.of(itemPedidoList().get(1),
                        itemPedidoList().get(0)))
                .fechaPedido(LocalDateTime.of(2024, 04, 25, 4, 12))
                .build();
        pedidoRepository.save(pedido2);
        pedidoRepository.flush();

    }

    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
    }

    @Test
    void givenAnPedido_whenSave_thenPedidowithId(){
        Pedido pedido3 = Pedido.builder()
                .status(StatusPedido.PENDIENTE)
                .fechaPedido(LocalDateTime.now())
                .build();
        Pedido pedidoGuardado = pedidoRepository.save(pedido3);
        System.out.println(pedidoGuardado);

        assertThat(pedidoGuardado).isNotNull();


    }

    @Test
    void givenPedidos_whenPedidosEntreFechas_thenReturnListOfPedidos() {
        inictMockPedidos();
        LocalDateTime fechaI = LocalDateTime.of(2023, 01, 10, 4, 12);
        LocalDateTime fechaF = LocalDateTime.of(2023, 12, 10, 4, 12);

        List<Pedido> pedidos = pedidoRepository.pedidosEntreFechas(fechaI, fechaF);

        System.out.println(pedidos);
        assertThat(pedidos).isNotEmpty();
        assertThat(pedidos).hasSize(1);
    }

    @Test
    void givenPedidos_whenFindByClienteAndStatus_thenReturnListOfPedidosMatchingCriterio() {
        inictMockPedidos();
        Cliente cliente = clientes.get(0);
        StatusPedido status = StatusPedido.ENVIADO;

        List<Pedido> pedidos = pedidoRepository.findByClienteAndStatus(cliente, status);

        System.out.println(pedidos);
        assertThat(pedidos).isNotEmpty();
        assertThat(pedidos).hasSize(1);
    }


    @Test
    void givenPedidos_whenPedidosyItemsPorCliente_thenReturnListOfPedidosAndItemsForClient() {
        inictMockPedidos();
        Long idCliente = clientes.get(0).getIdCliente();

        List<Pedido> pedidos = pedidoRepository.pedidosyItemsPorCliente(idCliente);

       // assertThat(pedidos).isNotEmpty();
    }
}