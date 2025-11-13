package com.chamulas.commons.dto;


public record HabitacionResponse(
		
		Long id,
	    Long numero,
	    String tipo,
	    String descripcion,
	    Double precio,
	    Long capacidad,
	    String estado
		
		) { 
}
