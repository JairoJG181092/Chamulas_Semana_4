// HabitacionResponse.java
package com.chamulas.commons.dto;

import com.chamulas.commons.enums.EstadoHabitacion;
import com.chamulas.commons.enums.TipoHabitacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HabitacionResponse {
    
    private Long id;
    private Long numero;
    private TipoHabitacion tipo;
    private String descripcion;
    private Double precio;
    private Long capacidad;
    private EstadoHabitacion estado;
    private LocalDateTime fechaCreacion;
}