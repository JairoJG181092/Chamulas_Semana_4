package com.chamulas.oauth.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.chamulas.oauth.dto.UsuarioRequest;
import com.chamulas.oauth.dto.UsuarioResponse;
import com.chamulas.oauth.entities.Rol;
import com.chamulas.oauth.entities.Usuario;

@Component
public class UsuarioMapper {
	
	public UsuarioResponse entityResponse(Usuario usuario) {
		if (usuario == null) return null;
		return new UsuarioResponse(
				usuario.getUsername(),
				usuario.getRoles().stream()
				.map(Rol::getNombre).collect(Collectors.toSet())
				);
	}
	
	public Usuario requestToEntity(UsuarioRequest request, String password, Set<Rol> roles) {
		if (request == null) return null;
		Usuario usuario = new Usuario();
		usuario.setUsername(request.username());
		usuario.setPassword(password);
		usuario.setRoles(roles);
		return usuario;
	}
}
