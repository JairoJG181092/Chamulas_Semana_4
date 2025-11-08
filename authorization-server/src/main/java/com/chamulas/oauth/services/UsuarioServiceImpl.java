package com.chamulas.oauth.services;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chamulas.oauth.dto.UsuarioRequest;
import com.chamulas.oauth.dto.UsuarioResponse;
import com.chamulas.oauth.entities.Rol;
import com.chamulas.oauth.entities.Usuario;
import com.chamulas.oauth.mappers.UsuarioMapper;
import com.chamulas.oauth.repositories.RolRepositry;
import com.chamulas.oauth.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService{

	private final UsuarioRepository usuarioRepository;
	private final RolRepositry rolRepository;
	private final UsuarioMapper usuarioMapper;
	private final PasswordEncoder passworEncoder;
	
	@Override
	@Transactional(readOnly = true)
	public Set<UsuarioResponse> listar() {
		log.info("Listado de todos los usarios solicitados");
		return usuarioRepository.findAll().stream()
				.map(usuarioMapper::entityResponse).collect(Collectors.toSet());
	}

	@Override
	public UsuarioResponse registrar(UsuarioRequest request) {
		if(usuarioRepository.findByUsername(request.username()).isPresent()) {
			throw new IllegalArgumentException("El usuario " + request.username() + " ya esta registrado");
		}
		Set<Rol> roles = request.roles().stream().map(rol -> 
		rolRepository.findByNombre(rol).orElseThrow(() ->
		new NoSuchElementException("Rol "+ rol + " no encontrado"))
		).collect(Collectors.toSet()); 
		Usuario usuario = usuarioMapper.requestToEntity(request, passworEncoder.encode(request.password()), roles);
		
		usuario = usuarioRepository.save(usuario);
		log.info("Usuario registrado {}", usuario.getUsername());
		
		return usuarioMapper.entityResponse(usuario);
	}

	@Override
	public UsuarioResponse eliminar(String username) {
		log.info("buscando usuario {} ", username);
		Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() ->
			new NoSuchElementException("Rol " + username));
		usuarioRepository.delete(usuario);
		log.info("usuario {} Eliminado", usuario.getUsername());
		return usuarioMapper.entityResponse(usuario);
	}

}
