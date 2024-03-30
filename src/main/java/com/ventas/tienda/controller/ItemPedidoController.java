package com.ventas.tienda.controller;

import com.ventas.tienda.dto.itemPedido.ItemPedidoDto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoToSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.service.itemPedido.ItemPedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-items")
public class ItemPedidoController {

    private static  final Logger logger = LoggerFactory.getLogger(ItemPedidoController.class);

    private final ItemPedidoService itemPedidoService;

    @Autowired
    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping()
    public ResponseEntity<ItemPedidoDto> saveItemPedido(@RequestBody ItemPedidoToSaveDto itemPedidoSaveDto){
        ItemPedidoDto itemPedidoCreado = itemPedidoService.guardarItemPedido(itemPedidoSaveDto);
        logger.info(itemPedidoCreado.toString());
        return ResponseEntity.ok().body(itemPedidoCreado);
    }

    @GetMapping()
    public ResponseEntity<List<ItemPedidoDto>> getItemPedidos(){
        List<ItemPedidoDto> itemPedidoDtoList = itemPedidoService.getAllItemPedidos();
        itemPedidoDtoList.forEach((itemPedidoDto -> logger.info(itemPedidoDto.toString())));
        return ResponseEntity.ok().body(itemPedidoDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoDto> getItemPedidoById(@PathVariable("id") long idItemPedido){
        try {
            ItemPedidoDto itemPedidoDto = itemPedidoService.buscarItemPedidoPorId(idItemPedido);
            return ResponseEntity.ok().body(itemPedidoDto);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoDto> actualizarItemPedido(@PathVariable("id") Long idItemPedido, @RequestBody ItemPedidoToSaveDto itemPedidoSaveDto){
        try {
            ItemPedidoDto itemPedidoActualizado = itemPedidoService.actualizarItemPedido(idItemPedido, itemPedidoSaveDto);
            return ResponseEntity.ok().body(itemPedidoActualizado);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItemPedido(@PathVariable("id") Long idItemPedido){
        try {
            itemPedidoService.removerItemPedido(idItemPedido);
            return ResponseEntity.ok().body("Item de pedido eliminado.");
        } catch (NotAbleToDeleteException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ItemPedidoDto>> getItemPedidoByIdPedido(@PathVariable("orderId") long idPedido){
            List<ItemPedidoDto> itemPedidoDto = itemPedidoService.buscarItemPedidoPorIdPedido(idPedido);
            return ResponseEntity.ok().body(itemPedidoDto);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ItemPedidoDto>> getItemPedidoByIdProduct(@RequestParam("nameProduct") String nameProduct){
            List<ItemPedidoDto> itemPedidoDtoList = itemPedidoService.buscarItemPedidoPorNombreProducto(nameProduct);
            return ResponseEntity.ok().body(itemPedidoDtoList);
    }


}
