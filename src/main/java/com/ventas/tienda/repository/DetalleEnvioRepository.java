package com.ventas.tienda.repository;

import com.ventas.tienda.Entities.DetalleEnvio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio, Long> {
    List<DetalleEnvio> findByPedido_IdPedido(Long idPedido);

    List<DetalleEnvio> findByTransportadoraEnvioLike(String transportadora);

    List<DetalleEnvio> findByPedido_Status(String status);
}
