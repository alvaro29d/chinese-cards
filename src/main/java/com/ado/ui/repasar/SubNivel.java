package com.ado.ui.repasar;

import java.util.ArrayList;
import java.util.List;

import com.ado.domain.Nivel;
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

	private Nivel nivel;
	private List<Palabra> palabras = new ArrayList<Palabra>();

	private int inicio;
	
	public SubNivel(Nivel nivel, int inicio, int numeroPalabras, ClickListener btnEstudiarClickListener, ClickListener btnPruebaClickListener) {
		this.btnEstudiar.addClickListener(btnEstudiarClickListener);
		this.btnPrueba.addClickListener(btnPruebaClickListener);
		this.nivel = nivel;
		this.inicio = inicio;
		setHeightUndefined();
		setWidth("140px");
		lblRango.setValue((inicio * numeroPalabras) + 1  + " - " + ((inicio * numeroPalabras) + numeroPalabras));
		lblRango.setWidthUndefined();
		HorizontalLayout botonera = new HorizontalLayout();
		btnEstudiar.setIcon(FontAwesome.BOOK);
		btnPrueba.setIcon(FontAwesome.GAMEPAD);
		botonera.addComponent(btnEstudiar);
		botonera.addComponent(btnPrueba);
		addComponent(lblRango);
		addComponent(botonera);
		setComponentAlignment(lblRango, Alignment.TOP_CENTER);
		setComponentAlignment(botonera, Alignment.TOP_CENTER);
		
		for(int i = inicio * numeroPalabras; i < (inicio * numeroPalabras) + numeroPalabras; i++){
			System.out.println("inicio: " + (inicio * numeroPalabras) + " hasta: " + ((inicio * numeroPalabras) + numeroPalabras -1) + " i: "+ i);
			palabras.add(nivel.getPalabras().get(i));
		}
		
	}

	public int getInicio() {
		return inicio;
	}

	public Nivel getNivel() {
		return nivel;
	}
	
	public List<Palabra> getPalabras() {
		return palabras;
	}
	
}
