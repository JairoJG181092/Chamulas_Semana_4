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
@Table(name = "RESERVAS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
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
    private LocalDateTime fechaEntrada;
    
    @NotNull(message = "La fecha de salida es obligatoria")
    @Future(message = "La fecha de salida debe ser en el futuro")
    @Column(name = "fecha_salida", nullable = false)
    private LocalDateTime fechaSalida;
    
    @NotNull(message = "El número de noches es obligatorio")
    @Min(value = 1, message = "El número mínimo de noches es 1")
    @Max(value = 365, message = "El número máximo de noches es 365")
    @Column(nullable = false)
    private Long noches;
    
    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    @Column(nullable = false, columnDefinition = "NUMBER")
    private Double total;
    
    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoReserva estado = EstadoReserva.CONFIRMADA;
    

    @AssertTrue(message = "La fecha de salida debe ser posterior a la fecha de entrada")
    public boolean isFechasValidas() {
        if (fechaEntrada == null || fechaSalida == null) {
            return true;
        }
        return fechaSalida.isAfter(fechaEntrada);
    }

}