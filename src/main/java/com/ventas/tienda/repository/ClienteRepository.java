package com.ventas.tienda.repository;

import com.ventas.tienda.Entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByEmailCliente(String email);

    List<Cliente> findByDireccionCLiente(String direccion);

    @Query("select c from Cliente c where c.nombreCliente like :nombre%")
    List<Cliente> buscarClientesQueComiencenPor(@Param("nombre") String nombre);
}
