package com.chamulas.habitaciones.controllers;

import com.chamulas.habitaciones.entities.Habitacion;
import com.chamulas.habitaciones.services.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService servicio;

    @GetMapping
    public List<Habitacion> listar() {
        return servicio.obtenerTodas();
    }

    @GetMapping("/estado/{estado}")
    public List<Habitacion> buscarPorEstado(@PathVariable String estado) {
        return servicio.buscarPorEstado(estado);
    }

    @PostMapping
    public Habitacion crear(@RequestBody Habitacion h) {
        return servicio.crear(h);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> actualizar(@PathVariable Long id, @RequestBody Habitacion h) {
        Habitacion actualizada = servicio.actualizar(id, h);
        return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
