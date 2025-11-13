package com.chamulas.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDocumento {
	  	INE(1L, "INE", "Credencial para votar"),
	    PASAPORTE(2L, "Pasaporte", "Documento de viaje internacional");
	    
		private final Long codigo;
	    private final String descripcion;
	    private final String detalles;
	    
	    public static TipoDocumento fromCodigo(Long codigo) {
	        for (TipoDocumento e : values()) {
	            if (e.codigo == codigo) {
	                return e;
	            }
	        }
	        throw new IllegalArgumentException("Código de documento no válido: " + codigo);
	    }
	    
	    public static TipoDocumento fromDescripcion(String descripcion) {
	        for (TipoDocumento tipo : values()) {
	            if (tipo.descripcion.equalsIgnoreCase(descripcion)) {
	                return tipo;
	            }
	        }
	        throw new IllegalArgumentException("Tipo de documento no encontrado: " + descripcion);
	    }
}