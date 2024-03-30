package com.ventas.tienda.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;
    private Double totalPago;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaPago;
    private String metodoPago;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    private  Pedido pedido;

}
