package com.chamulas.reservaciones.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chamulas.commons.dto.ReservaRequest;
import com.chamulas.commons.dto.ReservaResponse;
import com.chamulas.reservaciones.services.ReservaService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
//@RequestMapping("/api/reservas")
@AllArgsConstructor
@Slf4j
public class ReservaController {

	private final ReservaService reservaService;
	
	@GetMapping
	public ResponseEntity<List<ReservaResponse>>listar(){
		log.info("Obteniendo todas las reservas");
		return ResponseEntity.ok(reservaService.listar());
	}
	
	@PostMapping
    public ResponseEntity<ReservaResponse> crearReserva(@RequestBody ReservaRequest request) {
        log.info("Creando nueva reserva: {}", request);
        ReservaResponse reserva = reservaService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }
	
	@GetMapping("/{id}")
	    public ResponseEntity<ReservaResponse> obtenerPorId(@PathVariable Long id){
	        log.info("Obteniendo reserva con ID: {}", id);
	        return ResponseEntity.ok(reservaService.obtenerPorId(id));
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<ReservaResponse> registrar(@Valid @RequestBody ReservaRequest request){
		log.info("Registrando nueva reserva");
		ReservaResponse response=reservaService.registrar(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ReservaResponse>actualizar(
			@PathVariable Long id,
			@Valid @RequestBody ReservaRequest request){
		log.info("Actualizando reserva con ID: {}", id);
		return ResponseEntity.ok(reservaService.actualizar(request, id));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void>eliminar(@PathVariable Long id){
		log.info("Eliminando reserva con ID: {}", id);
		reservaService.eliminar(id);
		return 	ResponseEntity.noContent().build();
	}
	@PostMapping("/{id}/checkin")
	public ResponseEntity<ReservaResponse>realizarCheckin(@PathVariable Long id){
		log.info("Realizando check in para ID:{}", id);
		return ResponseEntity.ok(reservaService.realizarAcceso(id));
	}
	@PostMapping("/{id}/checkout")
	public ResponseEntity<ReservaResponse>realizarCheckout(@PathVariable Long id){
		log.info("Realizando check out para ID:{}", id);
		return ResponseEntity.ok(reservaService.realizarSalida(id));
	}
	@PostMapping("/{id}/cancelar")
	public ResponseEntity<ReservaResponse>cancelarReserva(@PathVariable Long id){
		log.info("Cancelar reserva para ID:{}", id);
		return ResponseEntity.ok(reservaService.cancelarReserva(id));
	}
}
