package com.ado.domain;

public class Palabra implements Comparable<Palabra>{

	private String caracter;
	private String significado;
	private String pingin;
	
	public Palabra() {
	}

	public String getCaracter() {
		return caracter;
	}

	public void setCaracter(String caracter) {
		this.caracter = caracter;
	}

	public String getSignificado() {
		return significado;
	}

	public void setSignificado(String significado) {
		this.significado = significado;
	}

	public String getPingin() {
		return pingin;
	}

	public void setPingin(String pingin) {
		this.pingin = pingin;
	}

	@Override
	public String toString() {
		return "Palabra [palabra=" + caracter + ", significado=" + significado
				+ ", pingin=" + pingin + "]";
	}

	@Override
	public int compareTo(Palabra arg0) {
		return pingin.compareTo(arg0.getPingin());
	}
	
	
	
}
