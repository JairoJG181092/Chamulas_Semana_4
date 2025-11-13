// HuespedController.java
package com.chamulas.huespedes.controllers;

import com.chamulas.huespedes.services.HuespedService;
import com.chamulas.commons.dto.HuespedRequest;
import com.chamulas.commons.dto.HuespedResponse;
import com.chamulas.commons.controllers.CommonController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService> {

	public HuespedController(HuespedService service) {
        super(service);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<HuespedResponse> findByEmail(@PathVariable String email) {
        log.info("Buscando huésped con email: {}", email);
        HuespedResponse huesped = service.findByEmail(email);
        return ResponseEntity.ok(huesped);
    }

    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<HuespedResponse> findByTelefono(@PathVariable String telefono) {
        log.info("Buscando huésped con teléfono: {}", telefono);
        HuespedResponse huesped = service.findByTelefono(telefono);
        return ResponseEntity.ok(huesped);
    }
}