package com.chamulas.commons.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.chamulas.commons.enums.EstadoReserva;



public record ReservaResponse(
		Long id,
		Long idhuesped,
		Long idhabitacion,
		LocalDateTime fechaEntrada,
		LocalDateTime fechaSalida,
		Long noches,
		Double total,
		EstadoReserva estado
		) {

}
