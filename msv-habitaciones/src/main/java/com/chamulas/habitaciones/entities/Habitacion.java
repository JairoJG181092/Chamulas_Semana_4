package com.chamulas.habitaciones.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "HABITACIONES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HABITACION")
    private Long id;

    @Column(name = "NUMERO", nullable = false, unique = true)
    @Min(value = 1, message = "El número de habitación debe ser mayor a 0")
    private int numero;

    @Column(name = "TIPO", nullable = false)
    @NotBlank(message = "El tipo de habitación es obligatorio")
    private String tipo;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "PRECIO", nullable = false)
    @DecimalMin(value = "0.1", inclusive = true, message = "El precio debe ser mayor que 0")
    private double precio;

    @Column(name = "CAPACIDAD", nullable = false)
    @Min(value = 1, message = "La capacidad mínima debe ser 1 persona")
    private int capacidad;

    @Column(name = "ESTADO", nullable = false)
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
        regexp = "Disponible|Ocupada|Limpieza|Mantenimiento",
        message = "El estado debe ser: Disponible, Ocupada, Limpieza o Mantenimiento"
    )
    private String estado;
}
