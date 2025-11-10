// HabitacionService.java
package com.chamulas.habitaciones.services;

import com.chamulas.commons.dto.HabitacionRequest;
import com.chamulas.commons.dto.HabitacionResponse;
import com.chamulas.commons.enums.EstadoHabitacion;
import com.chamulas.commons.enums.TipoHabitacion;
import com.chamulas.commons.services.CommonService;

import java.util.List;

public interface HabitacionService extends CommonService<HabitacionRequest, HabitacionResponse>{
    
	HabitacionResponse findByNumero(Long numero);
    List<HabitacionResponse> findByTipo(TipoHabitacion tipo);
    List<HabitacionResponse> findByEstado(EstadoHabitacion estado);
    List<HabitacionResponse> findDisponibles();
}