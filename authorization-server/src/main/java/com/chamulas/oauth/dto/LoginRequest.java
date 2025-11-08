package com.chamulas.oauth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
		@NotBlank(message = "el username es requerido")
		String username,
		
		@NotBlank(message = "La contraseña es requerida")
		String password)
{}
