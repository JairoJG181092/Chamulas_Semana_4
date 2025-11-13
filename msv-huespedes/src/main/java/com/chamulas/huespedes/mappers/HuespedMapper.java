// HuespedMapper.java
package com.chamulas.huespedes.mappers;

import com.chamulas.huespedes.entities.Huesped;
import com.chamulas.commons.dto.HuespedRequest;
import com.chamulas.commons.dto.HuespedResponse;
import com.chamulas.commons.enums.Nacionalidad;
import com.chamulas.commons.enums.TipoDocumento;
import com.chamulas.commons.mappers.CommonMapper;
import org.springframework.stereotype.Component;


@Component
public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped>{

	@Override
	public HuespedResponse entityToResponse(Huesped entity) {
		if (entity == null)  return null;
        
		return new HuespedResponse(
	            entity.getId(),
	            entity.getNombre(),
	            entity.getApellido(),
	            entity.getEmail(),
	            entity.getTelefono(),
	            entity.getTipoDocumento().getDescripcion(),
	            entity.getNacionalidad().getDescripcion()
	        );
	}

	@Override
	public Huesped requestToEntity(HuespedRequest request) {
		if(request == null) return null;
		
		Huesped huesped = new Huesped();
		
		huesped.setNombre(request.nombre());
        huesped.setApellido(request.apellido());
        huesped.setEmail(request.email());
        huesped.setTelefono(request.telefono());
        huesped.setTipoDocumento(null);
        huesped.setNacionalidad(null);
		
		
		return huesped;
	}
	
	
	public Huesped requestToEntity(HuespedRequest request, TipoDocumento tipoDocumento, Nacionalidad nacionalidad) {
		if(request == null) return null;
		
		Huesped huesped = requestToEntity(request);
		huesped.setTipoDocumento(tipoDocumento);
		huesped.setNacionalidad(nacionalidad);
		return huesped;
	}
	
}