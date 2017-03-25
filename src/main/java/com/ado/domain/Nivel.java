package com.ado.domain;

import java.util.ArrayList;
import java.util.List;

public class Nivel {

	private String nombre;
	private String descripcion;
	private String idioma;
	private List<Palabra> palabras;
	
	public Nivel() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public List<Palabra> getPalabras() {
		if(palabras == null) {
			this.palabras = new ArrayList<Palabra>();
		}
		return palabras;
	}
	
	
	
}
