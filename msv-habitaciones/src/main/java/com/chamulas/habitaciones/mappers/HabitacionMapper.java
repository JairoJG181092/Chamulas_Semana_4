// HabitacionMapper.java
package com.chamulas.habitaciones.mappers;

import org.springframework.stereotype.Component;

import com.chamulas.commons.dto.HabitacionRequest;
import com.chamulas.commons.dto.HabitacionResponse;
import com.chamulas.commons.enums.EstadoHabitacion;
import com.chamulas.commons.enums.TipoHabitacion;
import com.chamulas.commons.mappers.CommonMapper;
import com.chamulas.habitaciones.entities.Habitacion;

@Component
public class HabitacionMapper implements CommonMapper<HabitacionRequest, HabitacionResponse, Habitacion> {

	@Override
	public HabitacionResponse entityToResponse(Habitacion entity) {
		if(entity == null) return null;
		
		return new HabitacionResponse(entity.getId(),
				entity.getNumero(),
				entity.getTipo().getCodigo(),
				entity.getDescripcion(),
				entity.getPrecio(),
				entity.getCapacidad(),
				entity.getEstado().getCodigo()
				);
	}

	@Override
	public Habitacion requestToEntity(HabitacionRequest request) {
		if(request == null) return null;
		
		Habitacion habitacion = new Habitacion();
		habitacion.setNumero(request.numero());
		habitacion.setPrecio(request.precio());
		habitacion.setCapacidad(request.capacidad());
		habitacion.setTipo(null);
		habitacion.setEstado(null);
		
		return habitacion;
	
	}
	
	
	public Habitacion requestToEntity(HabitacionRequest request, TipoHabitacion tipoHabitacion, EstadoHabitacion estadoHabitacion) {
		if(request == null) return null;
		Habitacion habitacion = requestToEntity(request);
		habitacion.setTipo(tipoHabitacion);
		habitacion.setEstado(estadoHabitacion);
		return habitacion;
	
	}
	
	

  
}