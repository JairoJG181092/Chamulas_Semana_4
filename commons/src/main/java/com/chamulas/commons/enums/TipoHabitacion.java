package com.chamulas.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
	
@AllArgsConstructor
@Getter
public enum TipoHabitacion {
    INDIVIDUAL(1L, "Individual", "Habitación para una persona"),
    DOBLE(2L, "Doble", "Habitación para dos personas"),
    SUITE(3L, "Suite", "Suite con sala independiente"),
    MATRIMONIAL(4L, "Matrimonial", "Habitación con cama matrimonial"),
    FAMILIAR(5L, "Familiar", "Habitación para familia");
    
	private final Long codigo;
    private final String descripcion;
    private final String detalles;
    
    
    public static TipoHabitacion fromCodigo(Long codigo) {
        for (TipoHabitacion e : values()) {
            if (e.codigo == codigo) {
                return e;
            }
        }
        throw new IllegalArgumentException("Código de tipo de habitación no válido: " + codigo);
    }
    
    public static TipoHabitacion fromDescripcion(String descripcion) {
        for (TipoHabitacion tipoHabitacion: values()) {
            if (tipoHabitacion.descripcion.equalsIgnoreCase(descripcion)) {
                return tipoHabitacion;
            }
        }
        throw new IllegalArgumentException("Tipo habitación no encontrada: " + descripcion);
    }
    
    
    
    
}
