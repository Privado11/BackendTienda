package com.ventas.tienda.service.cliente;

import static org.mockito.ArgumentMatchers.any;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.dto.cliente.ClienteDto;
import com.ventas.tienda.dto.cliente.ClienteMapper;
import com.ventas.tienda.dto.cliente.ClienteToSaveDto;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private  ClienteServiceImpl clienteService;

    Cliente cliente, cliente2, cliente3;
    ClienteDto clienteDto;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .idCliente(1l)
                .nombreCliente("Walter Jiménez")
                .emailCliente("privado@privado.com")
                .direccionCliente("Calle 29")
                .build();


        cliente2 = Cliente.builder()
                .idCliente(2l)
                .nombreCliente("Andrés Licona")
                .emailCliente("privado2@privado.com")
                .direccionCliente("Calle 37")
                .build();

        cliente3 = Cliente.builder()
                .idCliente(3l)
                .nombreCliente("Walter Jiménez2")
                .emailCliente("privado2@privado.com")
                .direccionCliente("Calle 29")
                .build();

        clienteDto = ClienteMapper.INSTANCE.toDto(cliente);

    }

    @Test
    void givenClienteService_whenGuardarCliente_thenReturnClienteGuardado() {
        when(clienteRepository.save(any())).thenReturn(cliente);

        ClienteToSaveDto clienteAGuardar = new ClienteToSaveDto(null,
                "Walter Jiménez",
                "privado@privado.com",
                "Calle 29");


        when(clienteMapper.toDto(any())).thenReturn(clienteDto);

        ClienteDto clienteGuardado = clienteService.guardarCliente(clienteAGuardar);

        assertThat(clienteGuardado).isNotNull();
    }

    @Test
    void givenClienteService_whenActualizarCliente_thenReturnClienteActualizado() throws NotFoundExceptionEntity {
        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));

        ClienteToSaveDto clienteAActualizar= new ClienteToSaveDto(null,
                "Walter Jiménez",
                "privado@privado.com",
                "Calle 29");

        when(clienteRepository.save(any())).thenReturn(cliente);

        when(clienteMapper.toDto(any())).thenReturn(clienteDto);

        ClienteDto clienteActualizado = clienteService.actualizarCliente(1l, clienteAActualizar);

        assertThat(clienteActualizado).isNotNull();
    }

    @Test
    void givenClienteService_whenBuscarClientePorId_thenReturnClienteEncontrado() throws NotFoundExceptionEntity {
        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));

        when(clienteMapper.toDto(any())).thenReturn(clienteDto);

        ClienteDto clienteEncontrado = clienteService.buscarClientePorId(1l);

        assertThat(clienteEncontrado).isNotNull();
    }

    @Test
    void givenClienteService_whenRemoverCliente_thenMenaje() {
        Long idCliente = 1l;
        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));
        clienteService.removercliente(idCliente);

        verify(clienteRepository, times(1)).delete(any());
    }

    @Test
    void givenClienteService_whenGetAllCliente_thenReturnListOfCliente() {
        List<Cliente> clientes = List.of(cliente, cliente2, cliente3);

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.getAllCliente();

        assertThat(clienteDtos).isNotEmpty();
        assertThat(clienteDtos).hasSize(3);
    }

    @Test
    void givenClienteService_whenBuscarClientePorEmail_thenReturnClienteEncontrado() throws NotFoundExceptionEntity {
        String emailCliente = "privado@privado.com";

        when(clienteRepository.findByEmailCliente(any())).thenReturn(cliente);

        when(clienteMapper.toDto(any())).thenReturn(clienteDto);

        ClienteDto clienteDto = clienteService.buscarClientePorEmail(emailCliente);

        assertThat(clienteDto).isNotNull();
    }

    @Test
    void givenClienteService_whenBuscarClientesPorDireccion_thenReturnListOfCliente() throws NotFoundExceptionEntity {
        List<Cliente> clientes = List.of(cliente, cliente3);
        String direccionCliente = "Calle 29";

        when(clienteRepository.findByDireccionCliente(any())).thenReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.buscarClientesPorDireccion(direccionCliente);

        assertThat(clienteDtos).isNotEmpty();
        assertThat(clienteDtos).hasSize(2);
    }

    @Test
    void givenClienteService_whenBuscarClientesQueComiencenPor_thenReturnListOfCliente() throws NotFoundExceptionEntity {
        List<Cliente> clientes = List.of(cliente, cliente3);
        String nombreCliente = "Walter";

        when(clienteRepository.buscarClientesQueComiencenPor(any())).thenReturn(clientes);

        List<ClienteDto> clienteDtos = clienteService.buscarClientesQueComiencenPor(nombreCliente);

        assertThat(clienteDtos).isNotEmpty();
        assertThat(clienteDtos).hasSize(2);
    }
}