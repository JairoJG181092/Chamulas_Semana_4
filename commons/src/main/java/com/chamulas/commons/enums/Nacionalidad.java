package com.chamulas.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Nacionalidad {
    MEXICANA(1L, "Mexicana", "MX"),
    ESTADOUNIDENSE(2L, "Estadounidense", "US"),
    CANADIENSE(3L, "Canadiense", "CA"),
    ESPANOLA(4L, "Española", "ES"),
    ARGENTINA(5L, "Argentina", "AR"),	
    COLOMBIANA(6L, "Colombiana", "CO"),
    BRASILENA(7L, "Brasileña", "BR"),
    FRANCESA(8L, "Francesa", "FR"),
    ALEMANA(9L, "Alemana", "DE"),
    ITALIANA(10L, "Italiana", "IT"),
    BRITANICA(11L, "Británica", "GB"),
    CHINA(12L, "China", "CN"),
    JAPONESA(13L, "Japonesa", "JP"),
    AUSTRALIANA(14L, "Australiana", "AU"),
    RUSA(15L, "Rusa", "RU"),
    INDIA(16L, "India", "IN"),
    CUBANA(17L,"Cubana", "CU"),
    VENEZOLANA(18L, "Venezolana", "VE"),
    PERUANA(19L, "Peruana", "PE"),
    CHILENA(20L, "Chilena", "CL");
    
	private final Long codigo;
    private final String descripcion;
    private final String codigoPais;
    
    
    
    public static Nacionalidad fromCodigo(Long codigo) {
        for (Nacionalidad e : values()) {
            if (e.codigo == codigo) {
                return e;
            }
        }
        throw new IllegalArgumentException("Código de nacionalidad no válido: " + codigo);
    }
    
   
    public static Nacionalidad fromDescripcion(String descripcion) {
        for (Nacionalidad nacionalidad : values()) {
            if (nacionalidad.descripcion.equalsIgnoreCase(descripcion)) {
                return nacionalidad;
            }
        }
        throw new IllegalArgumentException("Nacionalidad no encontrada: " + descripcion);
    }
    
    public static Nacionalidad fromCodigoPais(String codigoPais) {
        for (Nacionalidad nacionalidad : values()) {
            if (nacionalidad.codigoPais.equalsIgnoreCase(codigoPais)) {
                return nacionalidad;
            }
        }
        throw new IllegalArgumentException("Código de país no encontrado: " + codigoPais);
    }
}
