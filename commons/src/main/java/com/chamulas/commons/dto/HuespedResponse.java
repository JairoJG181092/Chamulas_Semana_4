package com.chamulas.commons.dto;

import com.chamulas.commons.enums.TipoDocumento;
import com.chamulas.commons.enums.Nacionalidad;

public record HuespedResponse(
		Long id,
		String nombre,
		String apellido,
		String email,
		String telefono,
		TipoDocumento tipodocumento,
		Nacionalidad nacionalidad
		) {

}
