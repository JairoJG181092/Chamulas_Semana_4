package com.chamulas.reservaciones.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chamulas.commons.controllers.CommonController;
import com.chamulas.commons.dto.ReservaRequest;
import com.chamulas.commons.dto.ReservaResponse;
import com.chamulas.reservaciones.services.ReservaService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ReservaController extends CommonController<ReservaRequest, ReservaResponse, ReservaService> {

	public ReservaController(ReservaService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}	

	
	// CAMBIAR EL ESTADO DE LA RESERVACION A EN_CURSO, MEDIANTE EN CURSO
	@PutMapping("/{id}/checkin")
	public ResponseEntity<ReservaResponse>realizarCheckin(@PathVariable Long id){
		log.info("Realizando check in para ID:{}", id);
		return ResponseEntity.ok(service.realizarAcceso(id));
	}
	
	
	// CAMBIAR EL ESTADO DE LA RESERVACION A LIMPIEZA
	@PutMapping("/{id}/checkout")
	public ResponseEntity<ReservaResponse>realizarCheckout(@PathVariable Long id){
		log.info("Realizando check out para ID:{}", id);
		return ResponseEntity.ok(service.realizarSalida(id));
	}
	
	
	
	
	@PutMapping("/{id}/cancelar")
	public ResponseEntity<ReservaResponse>cancelarReserva(@PathVariable Long id){
		log.info("Cancelar reserva para ID:{}", id);
		return ResponseEntity.ok(service.cancelarReserva(id));
	}
	
	
	@GetMapping("/existe/habitacion/{id}")
	public Boolean hasHabitacion(@PathVariable Long id){
		return service.hasHabitacion(id);
	}
	
	
	@GetMapping("/existe/huesped/{id}")
	public Boolean hasHuesped(@PathVariable Long id){
		return service.hasHuesped(id);
	}
	
}
