// HabitacionRequest.java
package com.chamulas.commons.dto;


import jakarta.validation.constraints.*;



public record HabitacionRequest(
		 	@NotNull(message = "El número de habitación es obligatorio")
		    @Positive(message = "El número de habitación debe ser mayor a 0")
		    Long numero,
		    
		    @NotNull(message = "El tipo de habitación es obligatorio")
	        @Positive(message = "El id del tipo de habitación debe ser positivo")
		    Long idTipo,
		    
		    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
		    String descripcion,
		    
		    @NotNull(message = "El precio es obligatorio")
		    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
		    Double precio,
		    
		    @NotNull(message = "La capacidad es obligatoria")
		    @Min(value = 1, message = "La capacidad mínima es 1")
		    @Max(value = 10, message = "La capacidad máxima es 10")
		    Long capacidad,
		    
		    @NotNull(message = "El estado de la habitación es obligatorio")
	        @Positive(message = "El id del tipo de habitación debe ser positivo")
		    Long idEstado
		)

{
    
   
}
