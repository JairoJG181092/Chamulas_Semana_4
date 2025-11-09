package com.chamulas.commons.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.chamulas.commons.services.CommonService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Validated
@AllArgsConstructor
public class CommonController <RQ, RS,S extends CommonService<RQ,RS>>{

	protected final S service;

    @GetMapping
    public ResponseEntity<List<RS>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RS> obtenerPorId(@PathVariable @Positive(
            message = "El id debe ser positivo") Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
    
    
    @PostMapping
    public ResponseEntity<RS> insertar(@Valid @RequestBody RQ request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RS> actualizar(@PathVariable @Positive(
            message = "El id debe ser positivo") Long id, @Valid @RequestBody
    		RQ request) {
        return ResponseEntity.ok(service.actualizar(request, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<RS> eliminar(@PathVariable @Positive(
            message = "El id debe ser positivo") Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
