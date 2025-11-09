package com.chamulas.commons.dto;


import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ReservaRequest(
		
		
		@NotNull(message = "El huesped es requerido")
		@Positive(message = "El ID del huesped debe ser positivo")
		Long huespedId,
		
		@NotNull(message = "La habitacion es requerida")
		@Positive(message = "El ID  de la habitación debe ser positivo")
		Long habitacionId, 
		
		@NotNull(message = "La fecha de entrada es requerida")
		@DateTimeFormat(pattern = "dd/MM/yyyy")
		//@DateNow(message="La fecha de entrada no puede ser anterior al dia de hoy")
		@FutureOrPresent(message="La fecha de entrada no puede ser anterior al dia de hoy")
		LocalDate fechaEntrada,
		
		@NotNull(message = "La fecha de salida es requerida")		
		@DateTimeFormat(pattern = "dd/MM/yyyy")
//  	@DateNow(message="La fecha de entrada no puede ser anterior al dia de hoy")
		@FutureOrPresent(message="La fecha de salida debe ser hoy o en el futuro")
		LocalDate fechaSalida
		
	
		) {

}
