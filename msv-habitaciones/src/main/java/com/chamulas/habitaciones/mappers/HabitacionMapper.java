// HabitacionMapper.java
package com.chamulas.habitaciones.mappers;

import com.chamulas.habitaciones.entities.Habitacion;
import com.chamulas.commons.dto.HabitacionRequest;
import com.chamulas.commons.dto.HabitacionResponse;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HabitacionMapper {

    public HabitacionResponse toResponse(Habitacion entity) {
        if (entity == null) {
            return null;
        }
        
        return new HabitacionResponse(
            entity.getId(),
            entity.getNumero(),
            entity.getTipo(),
            entity.getDescripcion(),
            entity.getPrecio(),
            entity.getCapacidad(),
            entity.getEstado()
        );
    }

    public Habitacion toEntity(HabitacionResponse response) {
        if (response == null) {
            return null;
        }
        
        return new Habitacion(
            response.getId(),
            response.getNumero(),
            response.getTipo(),
            response.getDescripcion(),
            response.getPrecio(),
            response.getCapacidad(),
            response.getEstado()
        );
    }

    public Habitacion toEntity(HabitacionRequest request) {
        if (request == null) {
            return null;
        }
        
        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(request.getNumero());
        habitacion.setTipo(request.getTipo());
        habitacion.setDescripcion(request.getDescripcion());
        habitacion.setPrecio(request.getPrecio());
        habitacion.setCapacidad(request.getCapacidad());
        habitacion.setEstado(request.getEstado());
        
        return habitacion;
    }

    public Habitacion updateEntityFromRequest(HabitacionRequest request, Habitacion entity) {
        if (request == null || entity == null) {
            return entity;
        }
        
        entity.setNumero(request.getNumero());
        entity.setTipo(request.getTipo());
        entity.setDescripcion(request.getDescripcion());
        entity.setPrecio(request.getPrecio());
        entity.setCapacidad(request.getCapacidad());
        entity.setEstado(request.getEstado());
        
        return entity;
    }

    public List<HabitacionResponse> toResponseList(List<Habitacion> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<Habitacion> toEntityList(List<HabitacionResponse> responses) {
        return responses.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}