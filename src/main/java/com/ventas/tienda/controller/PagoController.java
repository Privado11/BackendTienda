package com.ventas.tienda.controller;

import com.ventas.tienda.dto.pago.PagoDto;
import com.ventas.tienda.dto.pago.PagoToSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.service.pago.PagoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PagoController {

    private static  final Logger logger = LoggerFactory.getLogger(PagoController.class);

    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping()
    public ResponseEntity<PagoDto> savePago(@RequestBody PagoToSaveDto pagoSaveDto){
        PagoDto pagoCreado = pagoService.guardarPago(pagoSaveDto);
        logger.info(pagoCreado.toString());
        return ResponseEntity.ok().body(pagoCreado);
    }

    @GetMapping()
    public ResponseEntity<List<PagoDto>> getPagos(){
        List<PagoDto> pagoDtoList = pagoService.getAllPagos();
        pagoDtoList.forEach((pagoDto -> logger.info(pagoDto.toString())));
        return ResponseEntity.ok().body(pagoDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDto> getPagoById(@PathVariable("id") long idPago){
        try {
            PagoDto pagoDto = pagoService.buscarPagoPorId(idPago);
            return ResponseEntity.ok().body(pagoDto);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDto> actualizarPago(@PathVariable("id") Long idPago, @RequestBody PagoToSaveDto pagoSaveDto){
        try {
            PagoDto pagoActualizado = pagoService.actualizarPago(idPago, pagoSaveDto);
            return ResponseEntity.ok().body(pagoActualizado);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePago(@PathVariable("id") Long idPago){
        try {
            pagoService.removerPago(idPago);
            return ResponseEntity.ok().body("Pago eliminado.");
        } catch (NotAbleToDeleteException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PagoDto> pagoPorIdPedido(@PathVariable("orderId") Long idPedido){
        try {
            PagoDto pagoDto = pagoService.buscarPagosPorIdPedido(idPedido);
            return ResponseEntity.ok().body(pagoDto);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PagoDto>> buscarPagosEntreFechas(@RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate){
        List<PagoDto> pagoDtoList = pagoService.buscarpagosEntreFechas(startDate, endDate);
        return ResponseEntity.ok().body(pagoDtoList);
    }
}
