package com.ventas.tienda.controller;

import com.ventas.tienda.dto.pedido.PedidoDto;
import com.ventas.tienda.dto.pedido.PedidoToSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.service.pedido.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class PedidoController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping()
    public ResponseEntity<PedidoDto> savePedido(@RequestBody PedidoToSaveDto pedidoSaveDto){
        PedidoDto pedidoCreado = pedidoService.guardarPedido(pedidoSaveDto);
        logger.info(pedidoCreado.toString());
        return ResponseEntity.ok().body(pedidoCreado);
    }

    @GetMapping()
    public ResponseEntity<List<PedidoDto>> getPedidos(){
        List<PedidoDto> pedidoDtoList = pedidoService.getAllPedidos();
        pedidoDtoList.forEach((pedidoDto -> logger.info(pedidoDto.toString())));
        return ResponseEntity.ok().body(pedidoDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> getPedidoById(@PathVariable("id") long idPedido){
        try {
            PedidoDto pedidoDto = pedidoService.buscarPedidoPorId(idPedido);
            return ResponseEntity.ok().body(pedidoDto);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> actualizarPedido(@PathVariable("id") Long idPedido, @RequestBody PedidoToSaveDto pedidoSaveDto){
        try {
            PedidoDto pedidoActualizado = pedidoService.actualizarPedido(idPedido, pedidoSaveDto);
            return ResponseEntity.ok().body(pedidoActualizado);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable("id") Long idPedido){
        try {
            pedidoService.removerPedido(idPedido);
            return ResponseEntity.ok().body("Pedido eliminado.");
        } catch (NotAbleToDeleteException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customers/{customerId}")
    public  ResponseEntity<List<PedidoDto>> buscarPedidoPorIdCliente(@PathVariable("customerId") Long idCliente){
        List<PedidoDto> pedidoDtoList = pedidoService.BuscarPedidosyItemsPorCliente(idCliente);
        return ResponseEntity.ok().body(pedidoDtoList);
    }

    @GetMapping("/date-range")
    public  ResponseEntity<List<PedidoDto>> buscarPedidosEntreFechas(@RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate){
        List<PedidoDto> pedidoDtoList = pedidoService.buscarPedidosEntreFechas(startDate, endDate);
        return ResponseEntity.ok().body(pedidoDtoList);
    }
}
