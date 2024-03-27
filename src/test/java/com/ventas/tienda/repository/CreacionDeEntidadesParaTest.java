package com.ventas.tienda.repository;

import com.ventas.tienda.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public abstract class CreacionDeEntidadesParaTest{

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private DetalleEnvioRepository detalleEnvioRepository;
    
    public List<Producto> productosList(){

        Producto producto = Producto.builder()
                .nombreProducto("Laptop Hp Victus")
                .precioProducto(2000.0)
                .stockProducto(20)
                .build();
        productoRepository.save(producto);

        Producto producto2 = Producto.builder()
                .nombreProducto("Teclado Mecanico")
                .precioProducto(300.0)
                .stockProducto(15)
                .build();
        productoRepository.save(producto2);

        return List.of(producto, producto2);
    }

    public List<Pedido> pedidosList(){
        List<Cliente> clientes = clienteList();
        Pedido pedido = Pedido.builder()
                .fechaPedido(LocalDateTime.now())
                .status("Enviado")
                .cliente(clientes.get(0))
                .detalleEnvio(detalleEnvioList().get(0))
                .build();
        pedidoRepository.save(pedido);

        Pedido pedido2 = Pedido.builder()
                .fechaPedido(LocalDateTime.now())
                .status("Pendiente")
                .cliente(clientes.get(1))
                .detalleEnvio(detalleEnvioList().get(1))
                .build();
        pedidoRepository.save(pedido2);

        return List.of(pedido, pedido2);
    }

    public List<Cliente> clienteList(){
        Cliente cliente = Cliente.builder()
                .nombreCliente("Walter Jiménez")
                .emailCliente("privado@privado.com")
                .direccionCLiente("Calle 29")
                .build();
        clienteRepository.save(cliente);

        Cliente cliente2 = Cliente.builder()
                .nombreCliente("Andrés Licona")
                .emailCliente("privado2@privado.com")
                .direccionCLiente("Calle 37")
                .build();
        clienteRepository.save(cliente2);

        return List.of(cliente, cliente2);
    }

    public List<ItemPedido> itemPedidoList(){

        ItemPedido itemPedido = ItemPedido.builder()
                .producto(productosList().get(0))
                .cantidadItem(4)
                .precio(productosList().get(0).getPrecioProducto())
                .build();

        itemPedidoRepository.save(itemPedido);

        ItemPedido itemPedido2 = ItemPedido.builder()
                .producto(productosList().get(0))
                .cantidadItem(4)
                .precio(productosList().get(0).getPrecioProducto())
                .build();

        itemPedidoRepository.save(itemPedido2);

        ItemPedido itemPedido3 = ItemPedido.builder()
                .producto(productosList().get(0))
                .cantidadItem(6)
                .precio(productosList().get(0).getPrecioProducto())
                .build();

        itemPedidoRepository.save(itemPedido3);
        
        return List.of(itemPedido, itemPedido2, itemPedido3);
    }
    
    public List<Pago> pagoList(){
        Pago pago = Pago.builder()
                .fechaPago(LocalDateTime.now())
                .metodoPago("Efectivo")
                .build();

        Pago pago2 = Pago.builder()
                .fechaPago(LocalDateTime.now())
                .metodoPago("Tarjeta")
                .build();

        return  pagoRepository.saveAll(List.of(pago, pago2));
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

        return  detalleEnvioRepository.saveAll(List.of(detalleEnvio, detalleEnvio2));
    }

}
