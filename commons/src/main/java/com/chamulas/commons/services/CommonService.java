package com.chamulas.commons.services;

import java.util.List;

public interface CommonService <RQ, RS> {

	List<RS> listar();
	 
	RS obtenerPorId (Long id);
	
	RS registrar(RQ request);
	
	RS actualizar(RQ request, Long id);
	
	void eliminar(Long id);
}
