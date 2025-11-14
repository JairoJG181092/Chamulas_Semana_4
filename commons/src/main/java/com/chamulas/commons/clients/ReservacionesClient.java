package com.chamulas.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msv-reservaciones")
public interface ReservacionesClient {
	
	// HABITACION
	@GetMapping("/existe/habitacion/{id}")
	boolean hasHabitacion(@PathVariable Long id);
	
	
	// HUESPED
	@GetMapping("/existe/huesped/{id}")
	boolean hasHuesped(@PathVariable Long id);
	
	
	
	// COMPROBAR SI LA HABITACION TIENE UNA RESERVACION EN CURSO
	

}
