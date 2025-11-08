package com.chamulas.huespedes.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "HUESPEDES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Huesped {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_HUESPED")
	private Long id;
	
	@Column(name = "NOMBRE", length = 50, nullable = false)
	@Size(min = 10, max = 50)
	@NotBlank(message = "El nombre del huesped es requerido")
	private String nombre;
	
	
	@Column(name = "APELLIDO", length = 70, nullable = false)
	@Size(min = 10, max = 70)
	@NotBlank(message = "El apellido huesped es requerido")
	private String apellido;
	
	@Column(name = "EMAIL", length = 100, nullable = false, unique = true)
	@Size(min = 1, max = 100, message = "El email debe tener entre 1 y 100 caracteres")
	@NotBlank(message = "El email es requerido")
	@Email(message = "El email debe de tener un formato válido")
	private String email;
	
	@Column(name = "TELEFONO", length = 10, nullable = false)
	@Size(min = 10, max = 10, message = "El teléfono debe tener 10 dígitos")
	@NotBlank(message = "El teléfono es requerido")
	@Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe contener solo 10 dígitos numéricos")
	private String telefono;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DOCUMENTO", length = 30, nullable = false)
	@NotNull(message = "El documento de identificación es requerido")
	private String documento; // Lo mofifico cuando ya este los enums de DOCUMENTO EN EL COMMONS
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "NACIONALIDAD", length = 80, nullable = false)
	@NotNull(message = "El documento de identificación es requerido")
	private String nacionalidad; // Lo mofifico cuando ya este los enums de NACIONALIDAD EN EL COMMONS

}
