// HabitacionController.java
package com.chamulas.habitaciones.controllers;

import com.chamulas.habitaciones.services.HabitacionService;
import com.chamulas.commons.dto.HabitacionRequest;
import com.chamulas.commons.dto.HabitacionResponse;
import com.chamulas.commons.enums.EstadoHabitacion;
import com.chamulas.commons.enums.TipoHabitacion;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@Slf4j
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @GetMapping
    public ResponseEntity<List<HabitacionResponse>> findAll() {
        log.info("Obteniendo todas las habitaciones");
        List<HabitacionResponse> habitaciones = habitacionService.findAll();
        return ResponseEntity.ok(habitaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitacionResponse> findById(@PathVariable Long id) {
        log.info("Buscando habitación con ID: {}", id);
        HabitacionResponse habitacion = habitacionService.findById(id);
        return ResponseEntity.ok(habitacion);
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<HabitacionResponse> findByNumero(@PathVariable Long numero) {
        log.info("Buscando habitación con número: {}", numero);
        HabitacionResponse habitacion = habitacionService.findByNumero(numero);
        return ResponseEntity.ok(habitacion);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<HabitacionResponse>> findByTipo(@PathVariable TipoHabitacion tipo) {
        log.info("Buscando habitaciones por tipo: {}", tipo);
        List<HabitacionResponse> habitaciones = habitacionService.findByTipo(tipo);
        return ResponseEntity.ok(habitaciones);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<HabitacionResponse>> findByEstado(@PathVariable EstadoHabitacion estado) {
        log.info("Buscando habitaciones por estado: {}", estado);
        List<HabitacionResponse> habitaciones = habitacionService.findByEstado(estado);
        return ResponseEntity.ok(habitaciones);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<HabitacionResponse>> findDisponibles() {
        log.info("Buscando habitaciones disponibles");
        List<HabitacionResponse> habitaciones = habitacionService.findDisponibles();
        return ResponseEntity.ok(habitaciones);
    }

    @PostMapping
    public ResponseEntity<HabitacionResponse> save(@Valid @RequestBody HabitacionRequest request) {
        log.info("Creando nueva habitación con número: {}", request.getNumero());
        HabitacionResponse habitacion = habitacionService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(habitacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitacionResponse> update(
            @PathVariable Long id, 
            @Valid @RequestBody HabitacionRequest request) {
        log.info("Actualizando habitación con ID: {}", id);
        HabitacionResponse habitacion = habitacionService.update(id, request);
        return ResponseEntity.ok(habitacion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Eliminando habitación con ID: {}", id);
        habitacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}