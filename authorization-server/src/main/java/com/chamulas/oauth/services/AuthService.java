package com.chamulas.oauth.services;

import com.chamulas.oauth.dto.LoginRequest;

public interface AuthService {
	String autenticar(LoginRequest request) throws Exception;
	
}
