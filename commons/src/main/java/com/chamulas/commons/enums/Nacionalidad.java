package com.chamulas.commons.enums;

import java.util.NoSuchElementException;

public enum Nacionalidad {

	    ARGENTINA("Argentina"),
	    MEXICANA("Mexicana"),
	    ESPAÑOLA("Espanola"),
	    CHILENA("Chilena"),
	    COLOMBIANA("Colombiana"),
	    PERUANA("Peruana"),
	    ESTADOUNIDENSE("Estadounidense"),
	    FRANCESA("Francesa"),
	    ITALIANA("Italiana"),
	    JAPONESA("Japonesa");

	    private final String descripcion;

	    Nacionalidad(String descripcion) {
	    	this.descripcion = descripcion;
	    	}

	    public String getNacionalidadToString() {
	        return descripcion;
	    }

	    public static Nacionalidad stringToNacionalidad(String descripcion){
	        for(Nacionalidad nacionalidad: values()){
	            if(nacionalidad.descripcion.equalsIgnoreCase(descripcion.trim()))
	                return nacionalidad;
	        }
	        throw new NoSuchElementException("No existe la nacionalidad con la descripción: "+descripcion);
	    }
}
