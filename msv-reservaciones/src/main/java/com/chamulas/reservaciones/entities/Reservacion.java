package com.chamulas.reservaciones.entities;

import com.chamulas.commons.enums.EstadoReserva;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El huésped es obligatorio")
    @Column(name = "huesped_id", nullable = false)
    private Long huespedId;
    
    @NotNull(message = "La habitación es obligatoria")
    @Column(name = "habitacion_id", nullable = false)
    private Long habitacionId;
    
    @NotNull(message = "La fecha de entrada es obligatoria")
    @FutureOrPresent(message = "La fecha de entrada debe ser hoy o en el futuro")
    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;
    
    @NotNull(message = "La fecha de salida es obligatoria")
    @Future(message = "La fecha de salida debe ser en el futuro")
    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;
    
    @NotNull(message = "El número de noches es obligatorio")
    @Min(value = 1, message = "El número mínimo de noches es 1")
    @Max(value = 365, message = "El número máximo de noches es 365")
    @Column(nullable = false)
    private Integer noches;
    
    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private Double total;
    
    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoReserva estado = EstadoReserva.CONFIRMADA;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    // Método de validación personalizada
    @AssertTrue(message = "La fecha de salida debe ser posterior a la fecha de entrada")
    public boolean isFechasValidas() {
        if (fechaEntrada == null || fechaSalida == null) {
            return true; // La validación @NotNull ya maneja esto
        }
        return fechaSalida.isAfter(fechaEntrada);
    }

    // Setter personalizado para estado que actualiza fechaActualizacion
    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
        this.fechaActualizacion = LocalDateTime.now();
    }
}