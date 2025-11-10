// HuespedController.java
package com.chamulas.huespedes.controllers;

import com.chamulas.huespedes.services.HuespedService;
import com.chamulas.commons.dto.HuespedRequest;
import com.chamulas.commons.dto.HuespedResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/huespedes")
@Slf4j
public class HuespedController {

    @Autowired
    private HuespedService huespedService;

    @GetMapping
    public ResponseEntity<List<HuespedResponse>> findAll() {
        log.info("Obteniendo todos los huéspedes");
        List<HuespedResponse> huespedes = huespedService.findAll();
        return ResponseEntity.ok(huespedes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HuespedResponse> findById(@PathVariable Long id) {
        log.info("Buscando huésped con ID: {}", id);
        HuespedResponse huesped = huespedService.findById(id);
        return ResponseEntity.ok(huesped);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<HuespedResponse> findByEmail(@PathVariable String email) {
        log.info("Buscando huésped con email: {}", email);
        HuespedResponse huesped = huespedService.findByEmail(email);
        return ResponseEntity.ok(huesped);
    }

    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<HuespedResponse> findByTelefono(@PathVariable String telefono) {
        log.info("Buscando huésped con teléfono: {}", telefono);
        HuespedResponse huesped = huespedService.findByTelefono(telefono);
        return ResponseEntity.ok(huesped);
    }

    @PostMapping
    public ResponseEntity<HuespedResponse> save(@Valid @RequestBody HuespedRequest request) {
        log.info("Creando nuevo huésped con email: {}", request.getEmail());
        HuespedResponse huesped = huespedService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(huesped);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HuespedResponse> update(
            @PathVariable Long id, 
            @Valid @RequestBody HuespedRequest request) {
        log.info("Actualizando huésped con ID: {}", id);
        HuespedResponse huesped = huespedService.update(id, request);
        return ResponseEntity.ok(huesped);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Eliminando huésped con ID: {}", id);
        huespedService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}