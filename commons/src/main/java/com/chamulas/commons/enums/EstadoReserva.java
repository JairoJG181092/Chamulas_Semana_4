package com.chamulas.commons.enums;

public enum EstadoReserva {
    CONFIRMADA("Confirmada", "Reserva creada exitosamente"),
    EN_CURSO("En curso", "Huésped actualmente hospedado"),
    FINALIZADA("Finalizada", "Estadía completada exitosamente"),
    CANCELADA("Cancelada", "Reserva cancelada antes del check-in"),
    PENDIENTE("Pendiente", "Reserva en proceso de confirmación");
    
    private final String descripcion;
    private final String detalles;
    
    EstadoReserva(String descripcion, String detalles) {
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