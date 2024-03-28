package com.ventas.tienda.service.cliente;

import com.ventas.tienda.dto.cliente.ClienteDto;
import com.ventas.tienda.dto.cliente.ClienteToSaveDto;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ClienteService {
    ClienteDto guardarCliente(ClienteToSaveDto cliente);
    ClienteDto actualizarCliente(Long idCliente, ClienteToSaveDto cliente) throws NotFoundExceptionEntity;
    ClienteDto buscarClientePorId(Long idCliente) throws NotFoundExceptionEntity;
    void removercliente(Long idCliente);
    List<ClienteDto> getAllCliente();

    ClienteDto buscarClientePorEmail(String emailCliente) throws EntityNotFoundException, NotFoundExceptionEntity;

    List<ClienteDto> buscarClientesPorDireccion(String direccioncliente) throws NotFoundExceptionEntity;

    List<ClienteDto> buscarClientesQueComiencenPor(String nombreCliente) throws NotFoundExceptionEntity;
}
