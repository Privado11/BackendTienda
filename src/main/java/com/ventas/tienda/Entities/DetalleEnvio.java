package com.ventas.tienda.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalles_envio")
@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleEnvio;
    private String direccionEnvio;
    private String transportadoraEnvio;
    private String numeroGuiaEnvio;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
}
