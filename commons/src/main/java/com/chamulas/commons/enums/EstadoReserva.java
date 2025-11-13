package com.chamulas.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EstadoReserva {
    CONFIRMADA(1L, "Confirmada", "Reserva creada exitosamente"),
    EN_CURSO(2L, "En curso", "Huésped actualmente hospedado"),
    FINALIZADA(3L, "Finalizada", "Estadía completada exitosamente"),
    CANCELADA(4L, "Cancelada", "Reserva cancelada antes del check-in"),
    PENDIENTE(5L, "Pendiente", "Reserva en proceso de confirmación");
    
	private final Long codigo;
    private final String descripcion;
    private final String detalles;
    
    public static EstadoReserva fromCodigo(Long codigo) {
        for (EstadoReserva e : values()) {
            if (e.codigo == codigo) {
                return e;
            }
        }
        throw new IllegalArgumentException("Estado reserva no válido: " + codigo);
    }
    
    
    public static EstadoReserva fromDescripcion(String descripcion) {
        for (EstadoReserva estadoReserva: values()) {
            if (estadoReserva.descripcion.equalsIgnoreCase(descripcion)) {
                return estadoReserva;
            }
        }
        throw new IllegalArgumentException("Estado reserva no encontrada: " + descripcion);
    }
    
   
}
