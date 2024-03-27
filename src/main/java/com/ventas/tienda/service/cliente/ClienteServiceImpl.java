package com.ventas.tienda.service.cliente;

import com.ventas.tienda.dto.cliente.ClienteDto;
import com.ventas.tienda.dto.cliente.ClienteToSaveDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Override
    public ClienteDto guardarCliente(ClienteToSaveDto cliente) {
        return null;
    }

    @Override
    public ClienteDto actualizarCliente(Long idCliente, ClienteToSaveDto cliente) {
        return null;
    }

    @Override
    public ClienteDto buscarClientePorId(Long idCliente) {
        return null;
    }

    @Override
    public void removercliente(Long idCliente) {

    }

    @Override
    public List<ClienteDto> getAllCliente() {
        return null;
    }

    @Override
    public ClienteDto buscarClientePorEmail(String emailCliente) {
        return null;
    }

    @Override
    public List<ClienteDto> buscarClientesPorDireccion(String direccioncliente) {
        return null;
    }

    @Override
    public List<ClienteDto> buscarClientesQueComiencenPor(String nombreCliente) {
        return null;
    }
}
