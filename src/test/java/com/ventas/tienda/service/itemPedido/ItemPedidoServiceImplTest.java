package com.ventas.tienda.service.itemPedido;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.ventas.tienda.Entities.ItemPedido;
import com.ventas.tienda.Entities.Producto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoDto;
import com.ventas.tienda.dto.itemPedido.ItemPedidoMapper;
import com.ventas.tienda.dto.itemPedido.ItemPedidoToSaveDto;
import com.ventas.tienda.exception.NotAbleToDeleteException;
import com.ventas.tienda.exception.NotFoundExceptionEntity;
import com.ventas.tienda.repository.ItemPedidoRepository;
import com.ventas.tienda.service.CreateEntytiesForTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemPedidoServiceImplTest extends CreateEntytiesForTest {

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private ItemPedidoMapper itemPedidoMapper;

    @InjectMocks
    private ItemPedidoServiceImpl itemPedidoService;

    ItemPedido itemPedido, itemPedido2;
    ItemPedidoDto itemPedidoDto;

    @BeforeEach
    void setUp() {
        itemPedido = itemPedidoList().get(0);
        itemPedido2 = itemPedidoList().get(1);

        itemPedidoDto = ItemPedidoMapper.INSTANCE.toDto(itemPedido);
    }

    @Test
    void givenItemPedidoService_whenGuardarItemPedido_thenReturnItemPedidoGuardado() {
        when(itemPedidoRepository.save(any())).thenReturn(itemPedido);

        ItemPedidoToSaveDto itemPedidoToSaveDto = new ItemPedidoToSaveDto(
                null,
                20,
                3000.0,
                null,
                null
        );

        when(itemPedidoMapper.toDto(any())).thenReturn(itemPedidoDto);

        ItemPedidoDto itemPedidoDto1 = itemPedidoService.guardarItemPedido(itemPedidoToSaveDto);

        assertThat(itemPedidoDto1).isNotNull();
    }

    @Test
    void givenItemPedidoService_whenActualizarItemPedido_thenReturnItemPedidoActualizado() throws NotFoundExceptionEntity {
        Long idItem = 1l;

        ItemPedidoToSaveDto itemPedidoToSaveDto = new ItemPedidoToSaveDto(
                null,
                20,
                3000.0,
                null,
                null
        );

        when(itemPedidoRepository.findById(idItem)).thenReturn(Optional.of(itemPedido));
        when(itemPedidoRepository.save(any())).thenReturn(itemPedido);

        when(itemPedidoMapper.toDto(any())).thenReturn(itemPedidoDto);

        ItemPedidoDto itemDtoG = itemPedidoService.actualizarItemPedido(idItem, itemPedidoToSaveDto);

        assertThat(itemDtoG).isNotNull();
    }

    @Test
    void givenItemPedidoService_whenBuscarItemPedidoPorId_thenReturnItemPedidoEncontrado() throws NotFoundExceptionEntity {
        Long idItem = 1l;

        when(itemPedidoRepository.findById(idItem)).thenReturn(Optional.of(itemPedido));

        when(itemPedidoMapper.toDto(any())).thenReturn(itemPedidoDto);

        ItemPedidoDto itemDtoG = itemPedidoService.buscarItemPedidoPorId(idItem);

        assertThat(itemDtoG).isNotNull();
    }

    @Test
    void givenItemPedidoService_whenRemoverItemPedido_thenItemPedidoMensaje() throws NotAbleToDeleteException {
        Long idItem = 1l;

        when(itemPedidoRepository.findById(idItem)).thenReturn(Optional.of(itemPedido));

        itemPedidoService.removerItemPedido(idItem);

        verify(itemPedidoRepository, times(1)).delete(itemPedido);
    }

    @Test
    void givenItemPedidoService_whenGetAllItemPedidos_thenReturnListOfItemPedidos() {
        List<ItemPedido> itemPedidoList = List.of(itemPedido, itemPedido2);

        when(itemPedidoRepository.findAll()).thenReturn(itemPedidoList);

        List<ItemPedidoDto> itemPedidoDtoList = itemPedidoService.getAllItemPedidos();

        assertThat(itemPedidoDtoList).isNotEmpty();
        assertThat(itemPedidoDtoList).hasSize(2);
    }

    @Test
    void givenItemPedidoService_whenBuscarItemPedidoPorNombreProducto_thenReturnListOfItemPedidos() {
        String nombreProducto = "Laptop Hp Victus";
        List<ItemPedido> itemPedidoList = List.of(itemPedido);

        when(itemPedidoRepository.findByProducto_NombreProductoLike(nombreProducto)).thenReturn(itemPedidoList);

        List<ItemPedidoDto> itemPedidoDtoList = itemPedidoService.buscarItemPedidoPorNombreProducto(nombreProducto);

        assertThat(itemPedidoDtoList).isNotEmpty();
        assertThat(itemPedidoDtoList).hasSize(1);

    }

    @Test
    void givenItemPedidoService_whenBuscarItemPedidoPorIdPedido_thenReturnListOfItemPedidos(){
        Long idPedido = 1l;
        List<ItemPedido> itemPedidoList = List.of(itemPedido, itemPedido2);

        when(itemPedidoRepository.findByPedido_IdPedido(idPedido)).thenReturn(itemPedidoList);


        List<ItemPedidoDto> itemPedidoDtoE = itemPedidoService.buscarItemPedidoPorIdPedido(idPedido);

        assertThat(itemPedidoDtoE).isNotEmpty();
    }

    @Test
    void givenItemPedidoService_whenSumaVentasProducto_thenReturnTotalVenta() {
        Producto producto = productosList().get(0);

        when(itemPedidoRepository.sumaTotalVentasProducto(producto)).thenReturn(itemPedido.getPrecio() * 10);
        Double total = itemPedidoService.sumaVentasProducto(producto);
        assertThat(total).isEqualTo(20000.0);
    }
}