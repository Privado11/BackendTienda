package com.ventas.tienda.dto;

import com.ventas.tienda.Entities.*;
import com.ventas.tienda.Enum.MetodoPago;
import com.ventas.tienda.Enum.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

public abstract class CreateEntytiesForTest {
    public List<Producto> productosList(){

        Producto producto = Producto.builder()
                .nombreProducto("Laptop Hp Victus")
                .precioProducto(2000.0)
                .stockProducto(20)
                .build();

        Producto producto2 = Producto.builder()
                .nombreProducto("Teclado Mecanico")
                .precioProducto(300.0)
                .stockProducto(15)
                .build();

        return List.of(producto, producto2);
    }

    public List<Pedido> pedidosList(){
        List<Cliente> clientes = clienteList();
        Pedido pedido = Pedido.builder()
                .fechaPedido(LocalDateTime.now())
                .status(StatusPedido.ENVIADO)
                .cliente(clientes.get(0))
                .detalleEnvio(detalleEnvioList().get(0))
                .build();

        Pedido pedido2 = Pedido.builder()
                .fechaPedido(LocalDateTime.now())
                .status(StatusPedido.PENDIENTE)
                .cliente(clientes.get(1))
                .detalleEnvio(detalleEnvioList().get(1))
                .build();

        return List.of(pedido, pedido2);
    }

    public List<Cliente> clienteList(){
        Cliente cliente = Cliente.builder()
                .nombreCliente("Walter Jiménez")
                .emailCliente("privado@privado.com")
                .direccionCliente("Calle 29")
                .build();

        Cliente cliente2 = Cliente.builder()
                .nombreCliente("Andrés Licona")
                .emailCliente("privado2@privado.com")
                .direccionCliente("Calle 37")
                .build();

        return List.of(cliente, cliente2);
    }

    public List<ItemPedido> itemPedidoList(){

        ItemPedido itemPedido = ItemPedido.builder()
                .producto(productosList().get(0))
                .cantidadItem(4)
                .precio(productosList().get(0).getPrecioProducto())
                .build();

        ItemPedido itemPedido2 = ItemPedido.builder()
                .producto(productosList().get(0))
                .cantidadItem(4)
                .precio(productosList().get(0).getPrecioProducto())
                .build();


        ItemPedido itemPedido3 = ItemPedido.builder()
                .producto(productosList().get(0))
                .cantidadItem(6)
                .precio(productosList().get(0).getPrecioProducto())
                .build();


        return List.of(itemPedido, itemPedido2, itemPedido3);
    }

    public List<Pago> pagoList(){
        Pago pago = Pago.builder()
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.EFECTIVO)
                .build();

        Pago pago2 = Pago.builder()
                .fechaPago(LocalDateTime.now())
                .metodoPago(MetodoPago.TARJETA_CREDITO)
                .build();

        return  List.of(pago, pago2);
    }

    public List<DetalleEnvio> detalleEnvioList(){

        DetalleEnvio detalleEnvio = DetalleEnvio.builder()
                .direccionEnvio("Calle 6")
                .transportadoraEnvio("Envia")
                .numeroGuiaEnvio("CSJ8249")
                .build();

        DetalleEnvio detalleEnvio2 = DetalleEnvio.builder()
                .direccionEnvio("Calle 9")
                .transportadoraEnvio("Coordinadora")
                .numeroGuiaEnvio("CSJ8245")
                .build();

        return  List.of(detalleEnvio, detalleEnvio2);
    }
}
