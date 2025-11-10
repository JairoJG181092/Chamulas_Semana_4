// HuespedRequest.java
package com.chamulas.commons.dto;

import com.chamulas.commons.enums.Nacionalidad;
import com.chamulas.commons.enums.TipoDocumento;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HuespedRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String apellido;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9\\-\\s]{10,20}$", message = "El formato del teléfono no es válido")
    private String telefono;
    
    
    @NotNull(message = "El tipo de documento es obligatorio")
    private TipoDocumento tipoDocumento;  // ENUM
    
    @NotNull(message = "La nacionalidad es obligatoria")
    private Nacionalidad nacionalidad;    // ENUM
}