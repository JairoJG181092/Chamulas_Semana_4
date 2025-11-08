package com.chamulas.oauth.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USUARIOS_OAUTH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO")
	private Long id;
	
	@Column(name = "USERNAME", nullable = false,length = 20, unique= true)
	@NotBlank(message = "el username es requerido")
	@Size(min=4, max=20, message="el username debe tener entre 1 y 20 caracteres")
	private String username;
	
	@Column(name = "PASSWORD", nullable = false, unique= true)
	@NotBlank(message = "la contraseña es requerida")
	@Size(min=4, message="el username debe tener entre 4 y 20 caracteres")
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "USUARIOS_ROLES",
			joinColumns = @JoinColumn(name = "ID_USUARIO"),
			inverseJoinColumns = @JoinColumn(name = "ID_ROL")
			)
	@NotNull(message = "los nombre son requeridos")
	@Size(min = 1, message = "el usuario debe tener al menos 1 rol")
	private Set<Rol> roles;
}
