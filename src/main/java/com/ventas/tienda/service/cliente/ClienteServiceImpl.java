package com.ventas.tienda.service.cliente;

import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.dto.cliente.ClienteDto;
import com.ventas.tienda.dto.cliente.ClienteMapper;
import com.ventas.tienda.dto.cliente.ClienteToSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private ClienteMapper clienteMapper;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public ClienteDto guardarCliente(ClienteToSaveDto clienteToSave) {
        Cliente cliente = clienteMapper.clienteToSaveDtotoEntity(clienteToSave);
        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    @Override
    public ClienteDto actualizarCliente(Long idCliente, ClienteToSaveDto clienteDto) throws NotFoundExceptionEntity {
        return clienteRepository.findById(idCliente).map(cliente ->{
            cliente.setNombreCliente(clienteDto.nombreCliente());
            cliente.setEmailCliente(clienteDto.emailCliente());
            cliente.setDireccionCLiente(clienteDto.direccionCLiente());

            Cliente clienteGuardado = clienteRepository.save(cliente);

            return clienteMapper.toDto(clienteGuardado);
        }).orElseThrow(()->new NotFoundExceptionEntity("Cliente no encontrado,"));
    }

    @Override
    public ClienteDto buscarClientePorId(Long idCliente) throws NotFoundExceptionEntity {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new NotFoundExceptionEntity("Cliente no encontrado."));

        return clienteMapper.toDto(cliente);
    }

    @Override
    public void removercliente(Long idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new NotAbleToDeleteException("CLiente no encontrado."));

        clienteRepository.delete(cliente);

    }

    @Override
    public List<ClienteDto> getAllCliente() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> clienteMapper.toDto(cliente))
                .toList();
    }

    @Override
    public ClienteDto buscarClientePorEmail(String emailCliente) throws  NotFoundExceptionEntity {
        Cliente cliente = clienteRepository.findByEmailCliente(emailCliente);

        if(Objects.isNull(cliente)){
            throw new NotFoundExceptionEntity("Cliente no encontrado.");
        }
        return clienteMapper.toDto(cliente);
    }

    @Override
    public List<ClienteDto> buscarClientesPorDireccion(String direccioncliente) throws NotFoundExceptionEntity {
        List<Cliente> clientes = clienteRepository.findByDireccionCLiente(direccioncliente);

        if(clientes.isEmpty()){
            throw new NotFoundExceptionEntity("Clientes no encontrado.");
        }
        return clientes.stream()
                .map(cliente -> clienteMapper.toDto(cliente))
                .toList();
    }

    @Override
    public List<ClienteDto> buscarClientesQueComiencenPor(String nombreCliente) throws NotFoundExceptionEntity {
        List<Cliente> clientes = clienteRepository.buscarClientesQueComiencenPor(nombreCliente);

        if(clientes.isEmpty()){
            throw new NotFoundExceptionEntity("Clientes no encontrado.");
        }
        return clientes.stream()
                .map(cliente -> clienteMapper.toDto(cliente))
                .toList();
    }
}
