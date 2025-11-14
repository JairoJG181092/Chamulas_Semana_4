package com.chamulas.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EstadoHabitacion {
    DISPONIBLE(1L, "Disponible", "Habitación lista para ser asignada"),
    OCUPADA(2L, "Ocupada", "Huésped actualmente en la habitación"),
    LIMPIEZA(3L, "En limpieza", "En proceso de limpieza"),
    MANTENIMIENTO(4L, "En mantenimiento", "No disponible por reparaciones"),
    RESERVADA(5L, "Reservada", "Habitación asignada a reserva futura");
    
	private final Long codigo;
    private final String descripcion;
    private final String detalles;
    
    
    
    public static EstadoHabitacion fromCodigo(Long codigo) {
        for (EstadoHabitacion e : values()) {
            if (e.codigo == codigo) {
                return e;
            }
        }
        throw new IllegalArgumentException("Estado habitación no válido: " + codigo);
    }
    
    
    public static EstadoHabitacion fromDescripcion(String descripcion) {
        for (EstadoHabitacion estadoHabitacion: values()) {
            if (estadoHabitacion.descripcion.equalsIgnoreCase(descripcion)) {
                return estadoHabitacion;
            }
        }
        throw new IllegalArgumentException("Estado habitación no encontrada: " + descripcion);
    }
    
  
}
