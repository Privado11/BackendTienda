package com.ventas.tienda.repository;

import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.Entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("select p from Pedido p where p.fechaPedido between ?1 and ?2")
    List<Pedido> pedidosEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal);

    List<Pedido> findByClienteAndStatus(Cliente cliente, String status);

    @Query("select p from Pedido p join fetch p.itemsPedido where p.cliente.idCliente = :idCliente")
    List<Pedido> pedidosyItemsPorCliente(@Param("idCliente") Long idCliente);

}
