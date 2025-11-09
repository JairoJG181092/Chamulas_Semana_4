package com.chamulas.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.chamulas.commons.dto.HuespedResponse;

@FeignClient(name="msv-huesped")
public interface HuespedClient {

	@GetMapping("/{id}")
	HuespedResponse obtenerHuespedPorId (@PathVariable Long id);
}

