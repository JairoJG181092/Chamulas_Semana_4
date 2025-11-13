// HabitacionServiceImpl.java
package com.chamulas.habitaciones.services;

import com.chamulas.habitaciones.entities.Habitacion;
import com.chamulas.habitaciones.repositories.HabitacionRepository;
import com.chamulas.habitaciones.mappers.HabitacionMapper;
import com.chamulas.commons.dto.HabitacionRequest;
import com.chamulas.commons.dto.HabitacionResponse;
import com.chamulas.commons.enums.EstadoHabitacion;
import com.chamulas.commons.enums.EstadoRegistro;
import com.chamulas.commons.enums.TipoHabitacion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class HabitacionServiceImpl implements HabitacionService {

	private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    public HabitacionServiceImpl(HabitacionRepository habitacionRepository, HabitacionMapper habitacionMapper) {
        this.habitacionRepository = habitacionRepository;
        this.habitacionMapper = habitacionMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> listar() {
        log.info("Listando todas las habitaciones");
        return habitacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream().map(habitacionMapper::entityToResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorId(Long id) {
        log.info("Obteniendo habitación con ID: {}", id);
        Habitacion habitacion = getHabitacionOrThrow(id);
        return habitacionMapper.entityToResponse(habitacion);
    }

    @Override
    @Transactional
    public HabitacionResponse registrar(HabitacionRequest request) {
        log.info("Registrando nueva habitación con número: {}", request.numero());
        
        if (habitacionRepository.existsByNumero(request.numero())) {
            throw new IllegalArgumentException("Ya existe una habitación con el número: " + request.numero());
        }
        
        TipoHabitacion tipoHabitacion = TipoHabitacion.fromCodigo(request.idTipo());
        EstadoHabitacion estadoHabitacion = EstadoHabitacion.fromCodigo(request.idEstado());

        Habitacion habitacion = habitacionMapper.requestToEntity(request, tipoHabitacion, estadoHabitacion);
        habitacion.setEstadoRegistro(EstadoRegistro.ACTIVO);
        Habitacion savedHabitacion = habitacionRepository.save(habitacion);
        log.info("Habitación registrada exitosamente con ID: {}", savedHabitacion.getId());
        
        return habitacionMapper.entityToResponse(savedHabitacion);
    }

    @Override
    @Transactional
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        
    	log.info("Actualizando habitación con ID: {}", id);
        Habitacion existingHabitacion = getHabitacionOrThrow(id);
        
        
        if (habitacionRepository.existsByNumero(request.numero()) && 
            !existingHabitacion.getNumero().equals(request.numero())) {
            throw new IllegalArgumentException("Ya existe otra habitación con el número: " + request.numero());
        }
        
        // Verificando si el tipo de habitacion cambio
		boolean tipoHabitacionCambio = !existingHabitacion.getTipo().getCodigo().equals(request.idTipo());
		boolean estadoHabitacionCambio = !existingHabitacion.getEstado().getCodigo().equals(request.idEstado());
		
		 // Actualizar campos
		
		if(tipoHabitacionCambio) {
			TipoHabitacion tipoHabitacion = TipoHabitacion.fromCodigo(request.idTipo());
	        existingHabitacion.setTipo(tipoHabitacion);
		}
		
		if(estadoHabitacionCambio) {
			EstadoHabitacion estadoHabitacion = EstadoHabitacion.fromCodigo(request.idEstado());
			existingHabitacion.setEstado(estadoHabitacion);
		}
	      
        existingHabitacion.setNumero(request.numero());
        existingHabitacion.setDescripcion(request.descripcion());
        existingHabitacion.setPrecio(request.precio());
        existingHabitacion.setCapacidad(request.capacidad());
        
        Habitacion updatedHabitacion = habitacionRepository.save(existingHabitacion);
        log.info("Habitación actualizada exitosamente con ID: {}", updatedHabitacion.getId());
        
        return habitacionMapper.entityToResponse(updatedHabitacion);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
    	Habitacion habitacion = getHabitacionOrThrow(id);
    	
        log.info("Eliminando habitación con ID: {}", id);
        
        if (!habitacionRepository.existsById(id)) {
            throw new NoSuchElementException("Habitación no encontrada con ID: " + id);
        }
        
        habitacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        log.info("Habitación eliminada exitosamente con ID: {}", id);
    }
    
    @Transactional(readOnly = true)
	private Habitacion getHabitacionOrThrow(Long id) {
		log.info("Buscando Habitación con el id: ", id);
		return habitacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
				.orElseThrow(()-> new NoSuchElementException("Habitación no encontrada con el id: " + id));
	}

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse findByNumero(Long numero) {
        log.info("Buscando habitación con número: {}", numero);
        Habitacion habitacion = habitacionRepository.findByNumero(numero)
                .orElseThrow(() -> new NoSuchElementException("Habitación no encontrada con número: " + numero));
        return habitacionMapper.entityToResponse(habitacion);
    }

	@Override
	public List<HabitacionResponse> findByTipo(TipoHabitacion tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HabitacionResponse> findByEstado(EstadoHabitacion estado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HabitacionResponse> findDisponibles() {
		// TODO Auto-generated method stub
		return null;
	}


}