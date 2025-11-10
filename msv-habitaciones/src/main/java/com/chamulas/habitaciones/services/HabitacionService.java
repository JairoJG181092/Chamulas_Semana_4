// HabitacionService.java
package com.chamulas.habitaciones.services;

import com.chamulas.commons.dto.HabitacionRequest;
import com.chamulas.commons.dto.HabitacionResponse;
import com.chamulas.commons.enums.EstadoHabitacion;
import com.chamulas.commons.enums.TipoHabitacion;
import java.util.List;

public interface HabitacionService {
    
    List<HabitacionResponse> findAll();
    HabitacionResponse findById(Long id);
    HabitacionResponse findByNumero(Long numero);
    List<HabitacionResponse> findByTipo(TipoHabitacion tipo);
    List<HabitacionResponse> findByEstado(EstadoHabitacion estado);
    List<HabitacionResponse> findDisponibles();
    HabitacionResponse save(HabitacionRequest request);
    HabitacionResponse update(Long id, HabitacionRequest request);
    void deleteById(Long id);
}