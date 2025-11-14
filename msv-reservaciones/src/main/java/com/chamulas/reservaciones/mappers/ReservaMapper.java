package com.chamulas.reservaciones.mappers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

import com.chamulas.commons.dto.ReservaRequest;
import com.chamulas.commons.dto.ReservaResponse;
import com.chamulas.commons.enums.EstadoReserva;
import com.chamulas.commons.mappers.CommonMapper;
import com.chamulas.reservaciones.entities.Reservacion;

@Component
public class ReservaMapper implements CommonMapper<ReservaRequest, ReservaResponse, Reservacion> {

	@Override
	public ReservaResponse entityToResponse(Reservacion entity) {
		if(entity==null) return null;
		return new ReservaResponse(
				entity.getId(),
				entity.getHuespedId(),
				entity.getHabitacionId(),
				entity.getFechaEntrada(),
				entity.getFechaSalida(),
				entity.getNoches(),
				entity.getTotal(),
				entity.getEstado()
				);
	}

	@Override
	public Reservacion requestToEntity(ReservaRequest request) {
		if(request==null)
			return null;
		Reservacion reservacion=new Reservacion();
		reservacion.setHuespedId(request.huespedId());
		reservacion.setHabitacionId(request.habitacionId());
		reservacion.setFechaEntrada(request.fechaEntrada());
		reservacion.setFechaSalida(request.fechaSalida());
		
		long noches=calcularNoches(request.fechaEntrada(), request.fechaSalida());
		reservacion.setNoches(noches);
		reservacion.setTotal(0.0);
		reservacion.setEstado(EstadoReserva.CONFIRMADA);
		
		return reservacion;
	}
	private int calcularNoches(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
	    if (fechaEntrada == null || fechaSalida == null) {
	        return 0;
	    }
	    return (int) ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
	}
}
