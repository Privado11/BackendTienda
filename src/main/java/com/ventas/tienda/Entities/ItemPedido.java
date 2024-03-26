package com.ventas.tienda.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_pedidos")
@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemPedido;
    private Integer cantidadItem;
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;
}
