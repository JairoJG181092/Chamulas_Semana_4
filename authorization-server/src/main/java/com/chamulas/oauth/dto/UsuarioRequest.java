package com.chamulas.oauth.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
		@NotBlank(message ="El username es requerido")
		@Size(min = 4, max = 20, message="El username es requerido")
		String username,
		
		@NotBlank(message ="El contraseña es requerido")
		@Size(min = 8, max = 20, message="El contraseña es requerido")
		String password,
		
		@NotNull(message = "los nombre son requeridos")
		@Size(min = 1, message = "el usuario debe tener al menos 1 rol")
		Set<String> roles
) {

}
