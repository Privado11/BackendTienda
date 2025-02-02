package com.ventas.tienda.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clientes")
@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    private String nombreCliente;
    @Column(unique = true)
    private String emailCliente;
    private String direccionCLiente;

    @OneToMany(mappedBy = "cliente")
    List<Pedido> pedidosCliente;
}
