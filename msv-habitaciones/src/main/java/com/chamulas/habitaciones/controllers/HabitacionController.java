// HabitacionController.java
package com.chamulas.habitaciones.controllers;

import com.chamulas.habitaciones.services.HabitacionService;
import com.chamulas.commons.dto.HabitacionRequest;
import com.chamulas.commons.dto.HabitacionResponse;
import com.chamulas.commons.controllers.CommonController;
import com.chamulas.commons.enums.EstadoHabitacion;
import com.chamulas.commons.enums.TipoHabitacion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//@RequestMapping("/api/habitaciones")
@Slf4j
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {

	public HabitacionController(HabitacionService service) {
        super(service);
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<HabitacionResponse> findByNumero(@PathVariable Long numero) {
        log.info("Buscando habitación con número: {}", numero);
        HabitacionResponse habitacion = service.findByNumero(numero);
        return ResponseEntity.ok(habitacion);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<HabitacionResponse>> findByTipo(@PathVariable TipoHabitacion tipo) {
        log.info("Buscando habitaciones por tipo: {}", tipo);
        List<HabitacionResponse> habitaciones = service.findByTipo(tipo);
        return ResponseEntity.ok(habitaciones);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<HabitacionResponse>> findByEstado(@PathVariable EstadoHabitacion estado) {
        log.info("Buscando habitaciones por estado: {}", estado);
        List<HabitacionResponse> habitaciones = service.findByEstado(estado);
        return ResponseEntity.ok(habitaciones);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<HabitacionResponse>> findDisponibles() {
        log.info("Buscando habitaciones disponibles");
        List<HabitacionResponse> habitaciones = service.findDisponibles();
        return ResponseEntity.ok(habitaciones);
    }
}