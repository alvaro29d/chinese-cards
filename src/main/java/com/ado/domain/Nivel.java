package com.ado.domain;

import java.util.ArrayList;
import java.util.List;

public class Nivel {

	private NivelEnum nivel;
	private String descripcion;
	private String idioma;
	private List<Palabra> palabras;
	
	public Nivel() {
	}

	public String getNombre() {
		return nivel.name();
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
	
	public NivelEnum getNivel() {
		return nivel;
	}

	public void setNivel(NivelEnum nivel) {
		this.nivel = nivel;
	}
	
}
