package com.chamulas.commons.dto;

import com.chamulas.commons.enums.Documento;
import com.chamulas.commons.enums.Nacionalidad;

public record HuespedResponse(
		Long id,
		String nombre,
		String apellido,
		String email,
		String telefono,
		Documento documento,
		Nacionalidad nacionalidad
		) {

}
