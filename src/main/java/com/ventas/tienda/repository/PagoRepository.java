package com.ventas.tienda.repository;

import com.ventas.tienda.Entities.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    @Query("select p from Pago p where p.fechaPago between ?1 and ?2")
    List<Pago> pagosEntreFechas(LocalDateTime fechaInicial, LocalDateTime fechaFinal);
    Pago findByPedido_IdPedido(Long idPedido);
}
