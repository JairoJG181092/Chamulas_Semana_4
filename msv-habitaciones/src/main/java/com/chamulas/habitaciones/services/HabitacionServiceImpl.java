package com.chamulas.habitaciones.services;

import com.chamulas.habitaciones.entities.Habitacion;
import com.chamulas.habitaciones.repositories.HabitacionRepository;
import com.chamulas.habitaciones.mappers.HabitacionMapper;
import com.chamulas.commons.dto.HabitacionRequest;
import com.chamulas.commons.dto.HabitacionResponse;
import com.chamulas.commons.enums.EstadoHabitacion;
import com.chamulas.commons.enums.TipoHabitacion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class HabitacionServiceImpl implements HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HabitacionMapper habitacionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> findAll() {
        log.info("Buscando todas las habitaciones");
        return habitacionMapper.toResponseList(habitacionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse findById(Long id) {
        log.info("Buscando habitación con ID: {}", id);
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Habitación no encontrada con ID: " + id));
        return habitacionMapper.toResponse(habitacion);
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse findByNumero(Long numero) {
        log.info("Buscando habitación con número: {}", numero);
        Habitacion habitacion = habitacionRepository.findByNumero(numero)
                .orElseThrow(() -> new NoSuchElementException("Habitación no encontrada con número: " + numero));
        return habitacionMapper.toResponse(habitacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> findByTipo(TipoHabitacion tipo) {
        log.info("Buscando habitaciones por tipo: {}", tipo);
        return habitacionMapper.toResponseList(habitacionRepository.findByTipo(tipo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> findByEstado(EstadoHabitacion estado) {
        log.info("Buscando habitaciones por estado: {}", estado);
        return habitacionMapper.toResponseList(habitacionRepository.findByEstado(estado));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> findDisponibles() {
        log.info("Buscando habitaciones disponibles");
        return habitacionMapper.toResponseList(habitacionRepository.findByEstado(EstadoHabitacion.DISPONIBLE));
    }

    @Override
    @Transactional
    public HabitacionResponse save(HabitacionRequest request) {
        log.info("Creando nueva habitación con número: {}", request.getNumero());
        
        if (habitacionRepository.existsByNumero(request.getNumero())) {
            throw new IllegalArgumentException("Ya existe una habitación con el número: " + request.getNumero());
        }
        
        Habitacion habitacion = habitacionMapper.toEntity(request);
        Habitacion savedHabitacion = habitacionRepository.save(habitacion);
        log.info("Habitación creada exitosamente con ID: {}", savedHabitacion.getId());
        
        return habitacionMapper.toResponse(savedHabitacion);
    }

    @Override
    @Transactional
    public HabitacionResponse update(Long id, HabitacionRequest request) {
        log.info("Actualizando habitación con ID: {}", id);
        
        Habitacion existingHabitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Habitación no encontrada con ID: " + id));
        
        if (habitacionRepository.existsByNumero(request.getNumero()) && 
            !existingHabitacion.getNumero().equals(request.getNumero())) {
            throw new IllegalArgumentException("Ya existe otra habitación con el número: " + request.getNumero());
        }
        
        existingHabitacion.setNumero(request.getNumero());
        existingHabitacion.setTipo(request.getTipo());
        existingHabitacion.setDescripcion(request.getDescripcion());
        existingHabitacion.setPrecio(request.getPrecio());
        existingHabitacion.setCapacidad(request.getCapacidad());
        existingHabitacion.setEstado(request.getEstado());
        
        Habitacion updatedHabitacion = habitacionRepository.save(existingHabitacion);
        log.info("Habitación actualizada exitosamente con ID: {}", updatedHabitacion.getId());
        
        return habitacionMapper.toResponse(updatedHabitacion);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Eliminando habitación con ID: {}", id);
        
        if (!habitacionRepository.existsById(id)) {
            throw new NoSuchElementException("Habitación no encontrada con ID: " + id);
        }
        
        habitacionRepository.deleteById(id);
        log.info("Habitación eliminada exitosamente con ID: {}", id);
    }
}

//actualizado 10-11-25