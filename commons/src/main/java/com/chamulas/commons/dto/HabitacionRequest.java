package com.chamulas.commons.dto;

import com.chamulas.commons.enums.TipoHabitacion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record HabitacionRequest(
		
		@NotNull(message = "El número de habitación es requerido")
		@Min(value=1, message = "El número debe ser mayor o igua a 1")
		Short numero,
		
		@NotNull(message = "El tipo de habitación es requerido")
		TipoHabitacion tipo,
		
		
		String descripcion,
		
		@NotNull(message = "El precio es requerido")
		@Positive(message="El precio debe ser positivo")
		Double precio,
		
		@NotNull(message ="Es necesario otorgar la capacidad de la recamara")
		@Min(value=1, message="La capacidad mínima de la habitación es 1")
		Short capacidad,
		
		@NotNull(message = "El  estado de la habitación es necesario")
		Long estado
		
		) {

}
