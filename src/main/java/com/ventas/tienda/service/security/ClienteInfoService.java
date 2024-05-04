package com.ventas.tienda.service.security;


import com.ventas.tienda.Entities.Cliente;
import com.ventas.tienda.dto.cliente.ClienteDto;
import com.ventas.tienda.dto.cliente.ClienteMapper;
import com.ventas.tienda.dto.cliente.ClienteToSaveDto;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.repository.ClienteRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteInfoService implements UserDetailsService{
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder;

    public ClienteInfoService(ClienteRepository userRepository, PasswordEncoder passwordEncoder, ClienteMapper clienteMapper) {
        this.clienteRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Cliente> clienteDetail = Optional.of(clienteRepository.findByEmailCliente(email));
        try {
            return clienteDetail.map(ClienteInfoDetail::new)
                    .orElseThrow(()-> new NotFoundExceptionEntity("User not found"));
        } catch (NotFoundExceptionEntity e) {
            throw new RuntimeException(e);
        }
    }

    public ClienteDto addUser(ClienteToSaveDto clienteDto) {
        Cliente cliente = clienteMapper.clienteToSaveDtotoEntity(clienteDto);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);

    }
}
