package com.chamulas.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.chamulas.commons.dto.HabitacionResponse;
import com.chamulas.commons.dto.HuespedResponse;

@FeignClient(name="msv-habitacion")
public interface HabitacionClient {

	@GetMapping("/{id}")
	HabitacionResponse obtenerHabitacionPorId(@PathVariable Long id);
	
	@PutMapping("/{idHabitacion}/estado/{idEstado}")
	HabitacionResponse actualizarEstadoHabitacion(
			@PathVariable Long idHabitacion,
			@PathVariable Long idEstado
			);
}
