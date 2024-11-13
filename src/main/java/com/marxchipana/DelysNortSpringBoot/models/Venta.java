package com.marxchipana.DelysNortSpringBoot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nombre;
    private String email;
    private String celular;
    private int cantidad; // Puedes mantener esta propiedad si la necesitas
    private BigDecimal total;

    @Column(name = "nombre_producto")
    private String nombresProductos;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

}

