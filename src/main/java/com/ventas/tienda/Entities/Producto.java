package com.ventas.tienda.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "productos")
@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    private String nombreProducto;
    private Double precioProducto;
    private Integer stockProducto;

    @OneToMany(mappedBy = "producto")
    private List<ItemPedido> itemsPedidos;
}
