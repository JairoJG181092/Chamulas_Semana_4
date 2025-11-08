package com.chamulas.oauth.services;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chamulas.oauth.entities.Usuario;
import com.chamulas.oauth.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class CustomsUserDetails implements UserDetailsService{

	private final UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Buscar usuario {}", username);
		Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(()->
		new UsernameNotFoundException("Usuario " + username + " no encontrado en la base de datos"));
		
		return new User(
				usuario.getUsername(),
				usuario.getPassword(),
				usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toSet())
				);
	}
	
}
