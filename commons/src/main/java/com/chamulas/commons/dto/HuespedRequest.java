package com.chamulas.commons.dto;

import com.chamulas.commons.enums.Documento;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record HuespedRequest(
		
		@NotBlank(message = "El nombre es requerido")
		@Size(min=10,max=50, message="El nombre debe tener minimo 10 caracteres")
		String nombre,
		
		@NotBlank(message = "El apellido es requerido")
		@Size(min=10,max=70, message="El apellido debe tener minimo 10 caracteres, Pa que se apellidan Peréz!!")
		String apellido,
		
		@NotBlank(message = "El email es requerido")
		@Size(min=1, max=10, message="El email debe tener entre 1 y 100 caracteres")
		@Email(message = "El email debe tener el formato correcto (correo@dominio)")
		String email,
		

		@NotBlank(message = "El telefono es requerido")
		@Size(min = 10, max = 10, message = "El teléfono debe tener exactamente 10 dígitos")
		@Pattern(regexp = "^[0-9]{10}$", message = "Solo 10 dígitos númericos")
		String telefono,
		
		@NotNull(message= "El documento es requerido")
        @Positive(message = "El documento debe ser positivo")
		Documento documento,
		
		@NotNull(message= "La nacionalidad es requerida")
		String nacionalidad
		) {

}
