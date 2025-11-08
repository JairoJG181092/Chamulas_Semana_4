package com.chamulas.oauth.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chamulas.oauth.dto.LoginRequest;
import com.chamulas.oauth.dto.TokenResponse;
import com.chamulas.oauth.dto.UsuarioRequest;
import com.chamulas.oauth.dto.UsuarioResponse;
import com.chamulas.oauth.services.AuthService;
import com.chamulas.oauth.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final UsuarioService usuarioService;
	
	 
	@PostMapping("/api/login")
	public ResponseEntity<TokenResponse> generarToken(@Valid @RequestBody LoginRequest loginRequest) throws Exception{
		TokenResponse token = new TokenResponse(authService.autenticar(loginRequest));
		return ResponseEntity.ok(token);
	}
	
	@GetMapping("/admin/usuarios")
	public ResponseEntity<Set<UsuarioResponse>> listarUsuarios()
	{
		return ResponseEntity.ok(usuarioService.listar());
	}
	
	@PostMapping("/admin/usuarios")
	public ResponseEntity<UsuarioResponse> registrarUsuario(@Valid @RequestBody UsuarioRequest request)
	{
		return ResponseEntity.ok(usuarioService.registrar(request));
	}
	
	@DeleteMapping("/admin/usuarios/{username}")
	public ResponseEntity<UsuarioResponse> eliminarUsuario(@PathVariable String username)
	{
		return ResponseEntity.ok(usuarioService.eliminar(username));
	}
}
