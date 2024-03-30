package com.ventas.tienda.controller;

import com.ventas.tienda.Enum.StatusPedido;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioDto;
import com.ventas.tienda.dto.detalleEnvio.DetalleEnvioToSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.service.detalleEnvio.DetalleEnvioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipping")
public class DetalleEnvioController {

    private static  final Logger logger = LoggerFactory.getLogger(DetalleEnvioController.class);

    private final DetalleEnvioService detalleEnvioService;

    @Autowired
    public DetalleEnvioController(DetalleEnvioService detalleEnvioService) {
        this.detalleEnvioService = detalleEnvioService;
    }

    @PostMapping()
    public ResponseEntity<DetalleEnvioDto> saveDetalleEnvio(@RequestBody DetalleEnvioToSaveDto detalleEnvioSaveDto){
        DetalleEnvioDto detalleEnvioCreado = detalleEnvioService.guardarDetalleEnvio(detalleEnvioSaveDto);
        logger.info(detalleEnvioCreado.toString());
        return ResponseEntity.ok().body(detalleEnvioCreado);
    }

    @GetMapping()
    public ResponseEntity<List<DetalleEnvioDto>> getDetallesEnvio(){
        List<DetalleEnvioDto> detalleEnvioDtoList = detalleEnvioService.getAllDetalleEnvio();
        detalleEnvioDtoList.forEach((detalleEnvioDto -> logger.info(detalleEnvioDto.toString())));
        return ResponseEntity.ok().body(detalleEnvioDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleEnvioDto> getDetalleEnvioById(@PathVariable("id") long idDetalleEnvio){
        try {
            DetalleEnvioDto detalleEnvioDto = detalleEnvioService.buscarDetalleEnvioPorId(idDetalleEnvio);
            return ResponseEntity.ok().body(detalleEnvioDto);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleEnvioDto> actualizarDetalleEnvio(@PathVariable("id") Long idDetalleEnvio, @RequestBody DetalleEnvioToSaveDto detalleEnvioSaveDto){
        try {
            DetalleEnvioDto detalleEnvioActualizado = detalleEnvioService.actualizarDetalleEnvio(idDetalleEnvio, detalleEnvioSaveDto);
            return ResponseEntity.ok().body(detalleEnvioActualizado);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDetalleEnvio(@PathVariable("id") Long idDetalleEnvio){
        try {
            detalleEnvioService.removerDetalleEnvio(idDetalleEnvio);
            return ResponseEntity.ok().body("Detalle de envío eliminado.");
        } catch (NotAbleToDeleteException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/carrier")
    public ResponseEntity<List<DetalleEnvioDto>> getDetallesEnvioBycarrier(@RequestParam("name") String nombreTransportadora){
        List<DetalleEnvioDto> detalleEnvioDtoList = detalleEnvioService.buscarDetallesEnvioPorTransportadora(nombreTransportadora);
        detalleEnvioDtoList.forEach((detalleEnvioDto -> logger.info(detalleEnvioDto.toString())));
        return ResponseEntity.ok().body(detalleEnvioDtoList);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<DetalleEnvioDto> getDetallesEnvioByidPedido(@PathVariable("orderId") Long idPedido){
        try {
            DetalleEnvioDto detalleEnvioDto = detalleEnvioService.buscarDetallesEnvioPorIdPedido(idPedido);
            logger.info(detalleEnvioDto.toString());
            return ResponseEntity.ok().body(detalleEnvioDto);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status")
    public ResponseEntity<List<DetalleEnvioDto>> getDetallesenvioByStatusOrder(@RequestParam("statusOrder") StatusPedido status){
        List<DetalleEnvioDto> detalleEnvioDtoList = detalleEnvioService.buscarDetallesEnvioPorStatus(status);
        detalleEnvioDtoList.forEach(detalleEnvioDto -> logger.info(detalleEnvioDto.toString()));
        return ResponseEntity.ok().body(detalleEnvioDtoList);
    }

}
