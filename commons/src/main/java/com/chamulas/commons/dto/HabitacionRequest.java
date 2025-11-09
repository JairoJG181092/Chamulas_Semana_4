// UpdateHabitacionRequest.java
package com.chamulas.commons.dto;

import com.chamulas.commons.enums.EstadoHabitacion;
import com.chamulas.commons.enums.TipoHabitacion;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HabitacionRequest {
    
    @NotNull(message = "El número de habitación es obligatorio")
    @Positive(message = "El número de habitación debe ser mayor a 0")
    private Long numero;
    
    @NotNull(message = "El tipo de habitación es obligatorio")
    private TipoHabitacion tipo;
    
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private Double precio;
    
    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad mínima es 1")
    @Max(value = 10, message = "La capacidad máxima es 10")
    private Long capacidad;
    
    @NotNull(message = "El estado es obligatorio")
    private EstadoHabitacion estado;
}