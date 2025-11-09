package com.chamulas.commons.enums;

public enum TipoHabitacion {
    INDIVIDUAL("Individual", "Habitación para una persona"),
    DOBLE("Doble", "Habitación para dos personas"),
    SUITE("Suite", "Suite con sala independiente"),
    MATRIMONIAL("Matrimonial", "Habitación con cama matrimonial"),
    FAMILIAR("Familiar", "Habitación para familia");
    
    private final String descripcion;
    private final String detalles;
    
    TipoHabitacion(String descripcion, String detalles) {
        this.descripcion = descripcion;
        this.detalles = detalles;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public String getDetalles() {
        return detalles;
    }
}
