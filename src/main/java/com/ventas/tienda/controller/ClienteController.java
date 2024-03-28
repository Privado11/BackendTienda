package com.ventas.tienda.controller;

import com.ventas.tienda.dto.cliente.ClienteDto;
import com.ventas.tienda.dto.cliente.ClienteToSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.service.cliente.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping()
    public ResponseEntity<ClienteDto> saveCliente(@RequestBody ClienteToSaveDto clienteSaveDto){
        ClienteDto clienteCreado = clienteService.guardarCliente(clienteSaveDto);
        logger.info(clienteCreado.toString());
        return ResponseEntity.ok().body(clienteCreado);
    }

    @GetMapping()
    public ResponseEntity<List<ClienteDto>> getClientes(){
        List<ClienteDto> clienteDtoList = clienteService.getAllCliente();
        clienteDtoList.forEach((clienteDto -> logger.info(clienteDto.toString())));
        return ResponseEntity.ok().body(clienteDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> getClienteById(@PathVariable("id") long idCliente){
        try {
            ClienteDto clienteDto = clienteService.buscarClientePorId(idCliente);
            return ResponseEntity.ok().body(clienteDto);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizarCliente(@PathVariable("id") Long idCliente, @RequestBody ClienteToSaveDto clienteSaveDto){
        try {
            ClienteDto clienteActualizado = clienteService.actualizarCliente(idCliente, clienteSaveDto);
            return ResponseEntity.ok().body(clienteActualizado);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable("id") Long idClienteo){
        try {
            clienteService.removercliente(idClienteo);
            return ResponseEntity.ok().body("Cliente eliminado.");
        } catch (NotAbleToDeleteException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteDto> getClienteByEmail(@PathVariable("email") String emailCliente){
        try {
            ClienteDto clienteDto = clienteService.buscarClientePorEmail(emailCliente);
            return ResponseEntity.ok().body(clienteDto);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/city")
    public ResponseEntity<List<ClienteDto>> getClienteByCity(@RequestParam("city") String ciudadCliente){
        try {
            List<ClienteDto> clienteDto = clienteService.buscarClientesPorDireccion(ciudadCliente);
            return ResponseEntity.ok().body(clienteDto);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name")
    public ResponseEntity<List<ClienteDto>> getClienteQueComiencenPor(@RequestParam("name") String nombreCliente){
        try {
            List<ClienteDto> clienteDto = clienteService.buscarClientesQueComiencenPor(nombreCliente);
            return ResponseEntity.ok().body(clienteDto);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

}
