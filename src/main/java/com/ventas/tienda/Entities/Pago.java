package com.ventas.tienda.Entities;

import com.ventas.tienda.Enum.MetodoPago;
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

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    private  Pedido pedido;

}
