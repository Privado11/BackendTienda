package com.ventas.tienda.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "pedidos")
@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaPedido;
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itemsPedido;

    @OneToOne(mappedBy = "pedido")
    private Pago pago;

    @OneToOne(mappedBy = "pedido")
    private DetalleEnvio detalleEnvio;
}
