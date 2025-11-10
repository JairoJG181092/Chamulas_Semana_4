// HuespedMapper.java
package com.chamulas.huespedes.mappers;

import com.chamulas.huespedes.entities.Huesped;
import com.chamulas.commons.dto.HuespedRequest;
import com.chamulas.commons.dto.HuespedResponse;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HuespedMapper {

    public HuespedResponse toResponse(Huesped entity) {
        if (entity == null) {
            return null;
        }
        
        return new HuespedResponse(
            entity.getId(),
            entity.getNombre(),
            entity.getApellido(),
            entity.getEmail(),
            entity.getTelefono(),
            entity.getTipoDocumento(),
            entity.getNacionalidad()
        );
    }

    public Huesped toEntity(HuespedRequest request) {
        if (request == null) {
            return null;
        }
        
        Huesped huesped = new Huesped();
        huesped.setNombre(request.getNombre());
        huesped.setApellido(request.getApellido());
        huesped.setEmail(request.getEmail());
        huesped.setTelefono(request.getTelefono());
        huesped.setTipoDocumento(request.getTipoDocumento());
        huesped.setNacionalidad(request.getNacionalidad());
        
        return huesped;
    }

    public List<HuespedResponse> toResponseList(List<Huesped> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}