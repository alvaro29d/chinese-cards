package com.ado.ui.repasar;

import com.ado.domain.Palabra;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MultilineButton extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	private Label lblPingin = new Label("chan");
	private Label lblSignificado = new Label("naa");

	private Palabra palabra;
	
	public MultilineButton() {
		setSpacing(false);
		setStyleName(ValoTheme.LAYOUT_WELL);
		setWidth("200px");
		setHeight("150px");
		lblPingin.setSizeUndefined();
		lblPingin.setStyleName(ValoTheme.LABEL_H1);
		lblSignificado.setSizeUndefined();
		lblSignificado.setWidth("180px");
		lblSignificado.setHeight("74px");
		lblSignificado.setStyleName(ValoTheme.LABEL_SMALL);
		addComponent(lblPingin);
		addComponent(lblSignificado);
		setComponentAlignment(lblPingin, Alignment.TOP_CENTER);
		setComponentAlignment(lblSignificado, Alignment.TOP_CENTER);
	}
	
	public void setEnabled(boolean enabled) {
		if(enabled) {
			setStyleName(ValoTheme.LAYOUT_WELL);
		} else {
			setStyleName("");
		}
		lblPingin.setVisible(enabled);
		lblSignificado.setVisible(enabled);
	}

	public void setPalabra(Palabra palabra) {
		this.palabra = palabra;
		lblPingin.setValue(palabra.getPingin());
		lblSignificado.setValue(palabra.getSignificado());
	}
	
	public Palabra getPalabra() {
		return palabra;
	}
	
}
