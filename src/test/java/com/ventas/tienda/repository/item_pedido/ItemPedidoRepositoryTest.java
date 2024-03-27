package com.ventas.tienda.repository.item_pedido;

import com.ventas.tienda.Entities.ItemPedido;
import com.ventas.tienda.Entities.Producto;
import com.ventas.tienda.repository.AbstractIntegrationBDTest;
import com.ventas.tienda.repository.ItemPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class ItemPedidoRepositoryTest extends AbstractIntegrationBDTest {

    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public ItemPedidoRepositoryTest(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    Long idPedido;
    Producto producto;
    void initcMockItemPedidos(){
        ItemPedido itemPedido = ItemPedido.builder()
                .producto(productosList().get(0))
                .pedido(pedidosList().get(0))
                .cantidadItem(4)
                .precio(productosList().get(0).getPrecioProducto())
                .build();

        idPedido = itemPedido.getPedido().getIdPedido();
        producto = itemPedido.getProducto();

        itemPedidoRepository.save(itemPedido);

        ItemPedido itemPedido2 = ItemPedido.builder()
                .producto(productosList().get(1))
                .pedido(pedidosList().get(0))
                .cantidadItem(4)
                .precio(productosList().get(1).getPrecioProducto())
                .build();

        itemPedidoRepository.save(itemPedido2);

        ItemPedido itemPedido3 = ItemPedido.builder()
                .producto(productosList().get(1))
                .cantidadItem(6)
                .pedido(pedidosList().get(1))
                .precio(productosList().get(1).getPrecioProducto())
                .build();

        itemPedidoRepository.save(itemPedido3);
        itemPedidoRepository.flush();

    }

    @BeforeEach
    void setUp() {
        itemPedidoRepository.deleteAll();
    }

    @Test
    void guardarItemPedido() {
        ItemPedido itemPedido = ItemPedido.builder()
                .producto(productosList().get(1))
                .cantidadItem(20)
                .precio(productosList().get(1).getPrecioProducto())
                .build();

        ItemPedido itemPedidoGuardado = itemPedidoRepository.save(itemPedido);

        System.out.println(itemPedidoGuardado);

        assertThat(itemPedidoGuardado).isNotNull();
    }

    @Test
    void findByProducto_NombreProductoLike() {
        initcMockItemPedidos();
        String nombreProducto = productosList().get(0).getNombreProducto();

        List<ItemPedido> itemPedidoList = itemPedidoRepository.findByProducto_NombreProductoLike(nombreProducto);

        System.out.println(itemPedidoList);

        assertThat(itemPedidoList).isNotEmpty();

    }

    @Test
    void findByPedido_IdPedido() {
        initcMockItemPedidos();

        List<ItemPedido> itemPedidoList = itemPedidoRepository.findByPedido_IdPedido(idPedido);

        System.out.println(itemPedidoList);

        assertThat(itemPedidoList).isNotEmpty();
    }

    @Test
    void sumaTotalVentasProducto() {
        initcMockItemPedidos();

        Double totalVentasProducto = itemPedidoRepository.sumaTotalVentasProducto(producto);

        System.out.println(totalVentasProducto);

        assertThat(totalVentasProducto).isGreaterThan(0.0);

    }
}