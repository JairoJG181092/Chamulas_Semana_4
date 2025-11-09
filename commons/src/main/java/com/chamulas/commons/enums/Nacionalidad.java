package com.chamulas.commons.enums;

public enum Nacionalidad {
    MEXICANA("Mexicana", "MX"),
    ESTADOUNIDENSE("Estadounidense", "US"),
    CANADIENSE("Canadiense", "CA"),
    ESPANOLA("Española", "ES"),
    ARGENTINA("Argentina", "AR"),
    COLOMBIANA("Colombiana", "CO"),
    BRASILENA("Brasileña", "BR"),
    FRANCESA("Francesa", "FR"),
    ALEMANA("Alemana", "DE"),
    ITALIANA("Italiana", "IT"),
    BRITANICA("Británica", "GB"),
    CHINA("China", "CN"),
    JAPONESA("Japonesa", "JP"),
    AUSTRALIANA("Australiana", "AU"),
    RUSA("Rusa", "RU"),
    INDIA("India", "IN"),
    CUBANA("Cubana", "CU"),
    VENEZOLANA("Venezolana", "VE"),
    PERUANA("Peruana", "PE"),
    CHILENA("Chilena", "CL");
    
    private final String descripcion;
    private final String codigoPais;
    
    Nacionalidad(String descripcion, String codigoPais) {
        this.descripcion = descripcion;
        this.codigoPais = codigoPais;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getCodigoPais() {
        return codigoPais;
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