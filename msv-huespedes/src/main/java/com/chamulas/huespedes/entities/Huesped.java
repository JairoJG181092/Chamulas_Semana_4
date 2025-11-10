package com.chamulas.huespedes.entities;

import com.chamulas.commons.enums.Nacionalidad;
import com.chamulas.commons.enums.TipoDocumento;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity
@Table(name = "huespedes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Huesped {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String apellido;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9\\-\\s]{10,20}$", message = "El formato del teléfono no es válido")
    @Column(nullable = false, unique = true, length = 20)
    private String telefono;
    
    @NotNull(message = "El tipo de documento es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoDocumento", nullable = false, length = 30)
    private TipoDocumento tipoDocumento;
    
    @NotNull(message = "La nacionalidad es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Nacionalidad nacionalidad;

}
