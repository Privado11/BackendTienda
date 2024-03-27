package com.ventas.tienda.repository.cliente;

import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.repository.AbstractIntegrationBDTest;
import com.ventas.tienda.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteRepositoryTest extends AbstractIntegrationBDTest {

    ClienteRepository clienteRepository;

    @Autowired
    public ClienteRepositoryTest(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    void inictMockCliente(){
        Cliente cliente = Cliente.builder()
                .nombreCliente("Walter Jiménez")
                .emailCliente("privado6@privado.com")
                .direccionCLiente("Calle 29")
                .pedidosCliente(pedidosList())
                .build();
        clienteRepository.save(cliente);

        Cliente cliente2 = Cliente.builder()
                .nombreCliente("Andrés Licona")
                .emailCliente("privado7@privado.com")
                .direccionCLiente("Calle 37")
                .pedidosCliente(List.of(pedidosList().get(0)))
                .build();
        clienteRepository.save(cliente2);
        clienteRepository.flush();
    }

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

    @Test
    void guardarCliente(){
        Cliente cliente = Cliente.builder()
                .nombreCliente("Walter Jiménez")
                .emailCliente("privado3@privado.com")
                .direccionCLiente("Calle 29")
                .pedidosCliente(pedidosList())
                .build();
        Cliente clienteGuardado = clienteRepository.save(cliente);

        System.out.println(clienteGuardado);

        assertThat(clienteGuardado).isNotNull();

    }


    @Test
    void findByEmailCliente() {
        inictMockCliente();
        String clienteEmail = "privado6@privado.com";

        Cliente clienteEncontrado = clienteRepository.findByEmailCliente(clienteEmail);

        System.out.println(clienteEncontrado);

        assertThat(clienteEncontrado).isNotNull();
        assertThat(clienteEncontrado.getNombreCliente()).isEqualTo("Walter Jiménez");
    }

    @Test
    void findByDireccionCLiente() {
        inictMockCliente();
        String direccionCliente = "Calle 37";

        List<Cliente> clientes = clienteRepository.findByDireccionCLiente(direccionCliente);

        System.out.println(clientes);

        assertThat(clientes).isNotEmpty();
    }

    @Test
    void buscarClientesQueComiencenPor() {
        inictMockCliente();
        String nombreCliente = "Walter";

        List<Cliente> clientes = clienteRepository.buscarClientesQueComiencenPor(nombreCliente);

        System.out.println(clientes);

        assertThat(clientes).isNotEmpty();
    }
}