package com.chamulas.commons.dto;


public record HabitacionResponse(
		
		Long id,
	    Long numero,
	    Long idTipo,
	    String descripcion,
	    Double precio,
	    Long capacidad,
	    Long idEstado
		
		) { 
}
