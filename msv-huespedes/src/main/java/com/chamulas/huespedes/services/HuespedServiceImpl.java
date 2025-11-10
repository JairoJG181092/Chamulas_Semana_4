// HuespedServiceImpl.java
package com.chamulas.huespedes.services;

import com.chamulas.huespedes.entities.Huesped;
import com.chamulas.huespedes.repositories.HuespedRepository;
import com.chamulas.huespedes.mappers.HuespedMapper;
import com.chamulas.commons.dto.HuespedRequest;
import com.chamulas.commons.dto.HuespedResponse;
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
        return huespedMapper.toResponseList(huespedRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse obtenerPorId(Long id) {
        log.info("Obteniendo huésped con ID: {}", id);
        Huesped huesped = huespedRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Huésped no encontrado con ID: " + id));
        return huespedMapper.toResponse(huesped);
    }

    @Override
    @Transactional
    public HuespedResponse registrar(HuespedRequest request) {
        log.info("Registrando nuevo huésped con email: {}", request.getEmail());
        
        if (huespedRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Ya existe un huésped con el email: " + request.getEmail());
        }
        
        if (huespedRepository.existsByTelefono(request.getTelefono())) {
            throw new IllegalArgumentException("Ya existe un huésped con el teléfono: " + request.getTelefono());
        }
        
        Huesped huesped = huespedMapper.toEntity(request);
        Huesped savedHuesped = huespedRepository.save(huesped);
        log.info("Huésped registrado exitosamente con ID: {}", savedHuesped.getId());
        
        return huespedMapper.toResponse(savedHuesped);
    }

    @Override
    @Transactional
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        log.info("Actualizando huésped con ID: {}", id);
        
        Huesped existingHuesped = huespedRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Huésped no encontrado con ID: " + id));
        
        if (huespedRepository.existsByEmail(request.getEmail()) && 
            !existingHuesped.getEmail().equals(request.getEmail())) {
            throw new IllegalArgumentException("Ya existe otro huésped con el email: " + request.getEmail());
        }
        
        if (huespedRepository.existsByTelefono(request.getTelefono()) && 
            !existingHuesped.getTelefono().equals(request.getTelefono())) {
            throw new IllegalArgumentException("Ya existe otro huésped con el teléfono: " + request.getTelefono());
        }
        
        // Actualizar campos
        existingHuesped.setNombre(request.getNombre());
        existingHuesped.setApellido(request.getApellido());
        existingHuesped.setEmail(request.getEmail());
        existingHuesped.setTelefono(request.getTelefono());
        existingHuesped.setTipoDocumento(request.getTipoDocumento());
        existingHuesped.setNacionalidad(request.getNacionalidad());
        
        Huesped updatedHuesped = huespedRepository.save(existingHuesped);
        log.info("Huésped actualizado exitosamente con ID: {}", updatedHuesped.getId());
        
        return huespedMapper.toResponse(updatedHuesped);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando huésped con ID: {}", id);
        
        if (!huespedRepository.existsById(id)) {
            throw new NoSuchElementException("Huésped no encontrado con ID: " + id);
        }
        
        huespedRepository.deleteById(id);
        log.info("Huésped eliminado exitosamente con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse findByEmail(String email) {
        log.info("Buscando huésped con email: {}", email);
        Huesped huesped = huespedRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Huésped no encontrado con email: " + email));
        return huespedMapper.toResponse(huesped);
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponse findByTelefono(String telefono) {
        log.info("Buscando huésped con teléfono: {}", telefono);
        Huesped huesped = huespedRepository.findByTelefono(telefono)
                .orElseThrow(() -> new NoSuchElementException("Huésped no encontrado con teléfono: " + telefono));
        return huespedMapper.toResponse(huesped);
    }
}