package com.chamulas.commons.enums;

public enum EstadoHabitacion {
    DISPONIBLE("Disponible", "Habitación lista para ser asignada"),
    OCUPADA("Ocupada", "Huésped actualmente en la habitación"),
    LIMPIEZA("En limpieza", "En proceso de limpieza"),
    MANTENIMIENTO("En mantenimiento", "No disponible por reparaciones"),
    RESERVADA("Reservada", "Habitación asignada a reserva futura");
    
    private final String descripcion;
    private final String detalles;
    
    EstadoHabitacion(String descripcion, String detalles) {
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
