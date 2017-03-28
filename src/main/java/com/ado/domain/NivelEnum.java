package com.ado.domain;

public enum NivelEnum {
	HSK1(150),
	HSK2(150),
	HSK3(300),
	HSK4(600),
	HSK5(1300),
	HSK6(2500);
	
	int nroPalabras;
	
	private NivelEnum(int nroPalabras) {
		this.nroPalabras = nroPalabras;
	}

	public int getNroPalabras() {
		return nroPalabras;
	}
	
}
