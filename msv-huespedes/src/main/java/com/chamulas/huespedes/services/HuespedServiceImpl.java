// HuespedServiceImpl.java
package com.chamulas.huespedes.services;

import com.chamulas.huespedes.entities.Huesped;
import com.chamulas.huespedes.repositories.HuespedRepository;
import com.chamulas.huespedes.mappers.HuespedMapper;
import com.chamulas.commons.dto.HuespedRequest;
import com.chamulas.commons.dto.HuespedResponse;
import com.chamulas.commons.enums.EstadoRegistro;
import com.chamulas.commons.enums.Nacionalidad;
import com.chamulas.commons.enums.TipoDocumento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class HuespedServiceImpl implements HuespedService {

	private final HuespedRepository huespedRepository;
    private final HuespedMapper huespedMapper;

    public HuespedServiceImpl(HuespedRepository huespedRepository, HuespedMapper huespedMapper) {
        this.huespedRepository = huespedRepository;
        this.huespedMapper = huespedMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HuespedResponse> listar() {
        log.info("Listando todos los huéspedes");
        return huespedRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
        		.stream()
        		.map(huespedMapper::entityToResponse)
        		.toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse obtenerPorId(Long id) {
        log.info("Obteniendo huésped con ID: {}", id);
        Huesped huesped = getHuespedOrThrow(id);
        
        return huespedMapper.entityToResponse(huesped);
    }

    @Override
    @Transactional
    public HuespedResponse registrar(HuespedRequest request) {
        log.info("Registrando nuevo huésped con email: {}", request.email());
        
        if (huespedRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Ya existe un huésped con el email: " + request.email());
        }
        
        if (huespedRepository.existsByTelefono(request.telefono())) {
            throw new IllegalArgumentException("Ya existe un huésped con el teléfono: " + request.telefono());
        }
        
        TipoDocumento tipoDocumento = TipoDocumento.fromCodigo(request.IdTipoDocumento());
        Nacionalidad nacionalidad = Nacionalidad.fromCodigo(request.idNacionalidad());
         
        Huesped huesped = huespedMapper.requestToEntity(request, tipoDocumento, nacionalidad);
        huesped.setEstadoRegistro(EstadoRegistro.ACTIVO);
        Huesped savedHuesped = huespedRepository.save(huesped);
        log.info("Huésped registrado exitosamente con ID: {}", savedHuesped.getId());
        
        return huespedMapper.entityToResponse(savedHuesped);
    }

    @Override
    @Transactional
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        log.info("Actualizando huésped con ID: {}", id);
        
        Huesped existingHuesped = getHuespedOrThrow(id);
        
        if (huespedRepository.existsByEmail(request.email()) && 
            !existingHuesped.getEmail().equals(request.email())) {
            throw new IllegalArgumentException("Ya existe otro huésped con el email: " + request.email());
        }
        
        if (huespedRepository.existsByTelefono(request.telefono()) && 
            !existingHuesped.getTelefono().equals(request.telefono())) {
            throw new IllegalArgumentException("Ya existe otro huésped con el teléfono: " + request.telefono());
        }
        
        boolean existingDocumento = !existingHuesped.getTipoDocumento().getCodigo().equals(request.IdTipoDocumento());
        boolean existingNacionalidad = !existingHuesped.getNacionalidad().getCodigo().equals(request.idNacionalidad());
        
        if(existingDocumento) {
        	TipoDocumento tipoDocumento = TipoDocumento.fromCodigo(request.IdTipoDocumento());
        	existingHuesped.setTipoDocumento(tipoDocumento);
        }
        
        if(existingNacionalidad) {
        	Nacionalidad nacionalidad = Nacionalidad.fromCodigo(request.idNacionalidad());
        	existingHuesped.setNacionalidad(nacionalidad);
        }
        
        
        // Actualizar campos
        existingHuesped.setNombre(request.nombre());
        existingHuesped.setApellido(request.apellido());
        existingHuesped.setEmail(request.email());
        existingHuesped.setTelefono(request.telefono());
        
        Huesped updatedHuesped = huespedRepository.save(existingHuesped);
        log.info("Huésped actualizado exitosamente con ID: {}", updatedHuesped.getId());
        
        return huespedMapper.entityToResponse(updatedHuesped);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
    	Huesped huesped = getHuespedOrThrow(id);
    	
        log.info("Eliminando huésped con ID: {}", id);
        
        if (!huespedRepository.existsById(id)) {
            throw new NoSuchElementException("Huésped no encontrado con ID: " + id);
        }
        
        huesped.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        log.info("Huésped eliminado exitosamente con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse findByEmail(String email) {
        log.info("Buscando huésped con email: {}", email);
        Huesped huesped = huespedRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Huésped no encontrado con email: " + email));
        return huespedMapper.entityToResponse(huesped);
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse findByTelefono(String telefono) {
        log.info("Buscando huésped con teléfono: {}", telefono);
        Huesped huesped = huespedRepository.findByTelefono(telefono)
                .orElseThrow(() -> new NoSuchElementException("Huésped no encontrado con teléfono: " + telefono));
        return huespedMapper.entityToResponse(huesped);
    }
    
    
    
    @Transactional(readOnly = true)
   	private Huesped getHuespedOrThrow(Long id) {
   		log.info("Buscando Huesped con el id: ", id);
   		return huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
   				.orElseThrow(()-> new NoSuchElementException("Huesped no encontrada con el id: " + id));
   	}

}