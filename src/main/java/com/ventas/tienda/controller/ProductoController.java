package com.ventas.tienda.controller;

import com.ventas.tienda.dto.producto.ProductoDto;
import com.ventas.tienda.dto.producto.ProductoSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.service.producto.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    private final ProductoService productoService;



    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping()
    public ResponseEntity<ProductoDto> saveProducto(@RequestBody ProductoSaveDto productoSaveDto){
        ProductoDto productoCreado = productoService.guardarProducto(productoSaveDto);
        logger.info(productoCreado.toString());
        return ResponseEntity.ok().body(productoCreado);
    }

    @GetMapping()
    public ResponseEntity<List<ProductoDto>> getProductos(){
        List<ProductoDto> productoDtoList = productoService.getAllProductos();
        productoDtoList.forEach((productoDto -> logger.info(productoDto.toString())));
        return ResponseEntity.ok().body(productoDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> getProductobyId(@PathVariable("id") long idProducto){
        try {
            ProductoDto productoDto = productoService.buscarProductoPorId(idProducto);
            return ResponseEntity.ok().body(productoDto);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizarProducto(@PathVariable("id") Long idProducto, @RequestBody ProductoSaveDto productoSaveDto){
        try {
            ProductoDto productoActializado = productoService.actualizarProducto(idProducto, productoSaveDto);
            return ResponseEntity.ok().body(productoActializado);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable("id") Long idProducto){
        try {
            productoService.removerProducto(idProducto);
            return ResponseEntity.ok().body("Producto eliminado.");
        } catch (NotAbleToDeleteException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/instock")
    public ResponseEntity<List<ProductoDto>> enStock(){
        List<ProductoDto> productoDtoList = productoService.productosEnStock();
        return ResponseEntity.ok().body(productoDtoList);
    }


    @GetMapping("/search")
    public ResponseEntity<ProductoDto> buscarProductoPorNombre(@RequestParam("nombreProducto") String nombreProducto){
        try {
            ProductoDto productoEncontrado = productoService.buscarProductoPornombre(nombreProducto);
            return ResponseEntity.ok().body(productoEncontrado);
        }catch (NotFoundExceptionEntity e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/-precio-stock")
    public ResponseEntity<List<ProductoDto>> buscarProductoPorPrecioyStockMenorQue(@RequestParam("precioProducto") Double precioProducto, @RequestParam("stockProducto") Integer stockProducto){
            List<ProductoDto> productosEncontrados = productoService.productosConPrecioyStockMenorQue(precioProducto, stockProducto);
            return ResponseEntity.ok().body(productosEncontrados);
    }

}
