package com.chamulas.commons.dto;

import com.chamulas.commons.enums.EstadoHabitacion;
import com.chamulas.commons.enums.TipoHabitacion;

public record HabitacionResponse(
		Long id,
		Short numero,
		TipoHabitacion tipo,
		String descripcion,
		Double precio,
		Short capacidad,
		EstadoHabitacion estado
		) {
}
