package com.ventas.tienda.repository.pedido;

import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.Entities.Pedido;
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

    void inictMockPedidos() {
        clientes = clienteList();
        Pedido pedido = Pedido.builder()
                .status("Enviado")
                .cliente(clientes.get(0))
                .itemsPedidos(itemPedidoList())
                .fechaPedido(LocalDateTime.of(2023, 04, 10, 4, 12))
                .build();
        pedidoRepository.save(pedido);

        Pedido pedido2 = Pedido.builder()
                .status("Pendiente")
                .cliente(clientes.get(0))
                .itemsPedidos(List.of(itemPedidoList().get(1),
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
    void guardarPedido(){
        Pedido pedido3 = Pedido.builder()
                .status("Pendiente")
                .fechaPedido(LocalDateTime.now())
                .build();
        Pedido pedidoGuardado = pedidoRepository.save(pedido3);
        System.out.println(pedidoGuardado);

        assertThat(pedidoGuardado).isNotNull();


    }

    @Test
    void pedidosEntreFechas() {
        inictMockPedidos();
        LocalDateTime fechaI = LocalDateTime.of(2023, 01, 10, 4, 12);
        LocalDateTime fechaF = LocalDateTime.of(2023, 12, 10, 4, 12);

        List<Pedido> pedidos = pedidoRepository.pedidosEntreFechas(fechaI, fechaF);

        System.out.println(pedidos);
        assertThat(pedidos).isNotEmpty();
        assertThat(pedidos).hasSize(1);
    }

    @Test
    void findByClienteAndStatus() {
        inictMockPedidos();
        Cliente cliente = clientes.get(0);
        String status = "Enviado";

        List<Pedido> pedidos = pedidoRepository.findByClienteAndStatus(cliente, status);

        System.out.println(pedidos);
        assertThat(pedidos).isNotEmpty();
        assertThat(pedidos).hasSize(1);
    }


    @Test
    void pedidosyItemsPorCliente() {
        inictMockPedidos();
        Long idCliente = clientes.get(0).getIdCliente();

        List<Pedido> pedidos = pedidoRepository.pedidosyItemsPorCliente(idCliente);

        System.out.println(pedidos);
        assertThat(pedidos).isNotEmpty();
    }
}