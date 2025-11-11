package com.chamulas.gateway.configuration;

import java.util.List;
import java.util.concurrent.Exchanger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.nimbusds.jwt.JWT;

@Configuration
public class SecurityConfig {
	@Bean
	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.csrf(csrf -> csrf.disable()
			.cors(cors -> cors.configurationSource(request ->{
				CorsConfiguration corsConfigurationSource = new CorsConfiguration();
				corsConfigurationSource.setAllowedOrigins(List.of("http://localhost:4200"));
				corsConfigurationSource.setAllowedHeaders(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
				corsConfigurationSource.setAllowCredentials(true);
				return corsConfigurationSource;
			}))	.authorizeExchange(exchange -> exchange
//					.pathMatchers(HttpMethod.OPTIONS,"/**").permitAll()
//					.pathMatchers(HttpMethod.GET,"/**").hasAnyRole("ADMIN","USER")
//					.pathMatchers(HttpMethod.POST,"/**").hasAnyRole("ADMIN","USER")
//					.pathMatchers(HttpMethod.PATCH,"/**").hasAnyRole("ADMIN","USER")
//					.pathMatchers(HttpMethod.PUT,"/**").hasRole("ADMIN")
//					.pathMatchers(HttpMethod.DELETE,"/**").hasRole("ADMIN")
					.anyExchange().permitAll()
					).oauth2ResourceServer(oauth2 -> oauth2
							.jwt(jwt->
							jwt.jwtAuthenticationConverter(reactiveJwtAuthenticationConverterAdapter())))
				);
		return http.build();
	}
	
	ReactiveJwtAuthenticationConverterAdapter reactiveJwtAuthenticationConverterAdapter() {
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
		grantedAuthoritiesConverter.setAuthorityPrefix("");
		
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
	}
}
