package com.ado.ui.repasar;

import java.util.ArrayList;
import java.util.List;

import com.ado.domain.Nivel;
import com.ado.domain.NivelEnum;
import com.ado.domain.Palabra;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SubNivel extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	private Label lblRango = new Label();
	private Button btnEstudiar = new Button();
	private Button btnPrueba = new Button();

	private List<Palabra> palabras = new ArrayList<Palabra>();
	private NivelEnum nivel;

	private int subNivel;
	
	public SubNivel(Nivel nivel, int subNivel, int numeroPalabras, boolean avance, ClickListener btnEstudiarClickListener, ClickListener btnPruebaClickListener) {
		this.btnEstudiar.addClickListener(btnEstudiarClickListener);
		this.btnPrueba.addClickListener(btnPruebaClickListener);
		this.nivel = nivel.getNivel();
		this.subNivel = subNivel;
		setHeightUndefined();
		setWidth("140px");
		lblRango.setValue((subNivel * numeroPalabras) + 1  + " - " + ((subNivel * numeroPalabras) + numeroPalabras));
		lblRango.setWidthUndefined();
		
		if(avance){
			lblRango.setStyleName(com.vaadin.ui.themes.ValoTheme.LABEL_SUCCESS);
		} else {
			lblRango.setHeight("40px");
		}
		
		HorizontalLayout botonera = new HorizontalLayout();
		btnEstudiar.setIcon(FontAwesome.BOOK);
		btnPrueba.setIcon(FontAwesome.GAMEPAD);
		botonera.addComponent(btnEstudiar);
		botonera.addComponent(btnPrueba);
		addComponent(lblRango);
		addComponent(botonera);
		setComponentAlignment(lblRango, Alignment.TOP_CENTER);
		setComponentAlignment(botonera, Alignment.TOP_CENTER);
		
		for(int i = subNivel * numeroPalabras; i < (subNivel * numeroPalabras) + numeroPalabras; i++){
			palabras.add(nivel.getPalabras().get(i));
		}
		
	}

	public int getSubNivel() {
		return subNivel;
	}

	public NivelEnum getNivel() {
		return nivel;
	}
	
	public List<Palabra> getPalabras() {
		return palabras;
	}
	
}
