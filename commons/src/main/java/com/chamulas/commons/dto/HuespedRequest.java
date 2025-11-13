// HuespedRequest.java
package com.chamulas.commons.dto;

import jakarta.validation.constraints.*;

public record HuespedRequest(
		
		@NotBlank(message = "El nombre es obligatorio")
	    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
	    String nombre,
	    
	    @NotBlank(message = "El apellido es obligatorio")
	    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
	    String apellido,
	    
	    @NotBlank(message = "El email es obligatorio")
	    @Email(message = "El formato del email no es válido")
	    String email,
	    
	    @NotBlank(message = "El teléfono es obligatorio")
	    @Pattern(regexp = "^\\+?[0-9\\-\\s]{10,20}$", message = "El formato del teléfono no es válido")
	    String telefono,
	    
	    
	    @NotNull(message = "El tipo de documento es obligatorio")
        @Positive(message = "El id del tipo de documento debe ser positivo")
	    Long IdTipoDocumento,  // ENUM
	    
	    @NotNull(message = "La nacionalidad es obligatoria")
        @Positive(message = "El id de la nacionalidad debe ser positivo")
	    Long idNacionalidad    // ENUM
		
		
		) {
    
    
}