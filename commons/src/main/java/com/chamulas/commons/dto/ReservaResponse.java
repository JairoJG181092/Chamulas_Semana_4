package com.chamulas.commons.dto;

import java.time.LocalDate;

import com.chamulas.commons.enums.EstadoReserva;



public record ReservaResponse(
		Long id,
		Long idhuesped,
		Long idhabitacion,
		LocalDate fechaEntrada,
		LocalDate fechaSalida,
		Integer noches,
		Double total,
		EstadoReserva estado
		) {

}
