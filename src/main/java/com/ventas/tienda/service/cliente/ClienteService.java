package com.ventas.tienda.service.cliente;

import com.ventas.tienda.dto.cliente.ClienteDto;
import com.ventas.tienda.dto.cliente.ClienteToSaveDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ClienteService {
    ClienteDto guardarCliente(ClienteToSaveDto cliente);
    ClienteDto actualizarCliente(Long idCliente, ClienteToSaveDto cliente);
    ClienteDto buscarClientePorId(Long idCliente);
    void removercliente(Long idCliente);
    List<ClienteDto> getAllCliente();

    ClienteDto buscarClientePorEmail(String emailCliente) throws EntityNotFoundException;

    List<ClienteDto> buscarClientesPorDireccion(String direccioncliente);

    List<ClienteDto> buscarClientesQueComiencenPor(String nombreCliente);
}
