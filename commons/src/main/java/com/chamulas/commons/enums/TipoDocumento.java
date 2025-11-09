package com.chamulas.commons.enums;

public enum TipoDocumento {
    INE("INE", "Credencial para votar"),
    PASAPORTE("Pasaporte", "Documento de viaje internacional");
    
    private final String descripcion;
    private final String detalles;
    
    TipoDocumento(String descripcion, String detalles) {
        this.descripcion = descripcion;
        this.detalles = detalles;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getDetalles() {
        return detalles;
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