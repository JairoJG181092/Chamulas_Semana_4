package com.chamulas.oauth.services;

import java.util.Set;

import com.chamulas.oauth.dto.UsuarioRequest;
import com.chamulas.oauth.dto.UsuarioResponse;

public interface UsuarioService {
	Set<UsuarioResponse> listar();
	UsuarioResponse registrar(UsuarioRequest request);
	UsuarioResponse eliminar(String username);
}
