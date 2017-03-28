package com.ado.ui.estudiar;

import com.ado.domain.Palabra;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class PalabraLayout extends HorizontalLayout {
	private static final long serialVersionUID = 1L;

	private Label lblCaracter = new Label();
	private Label lblPingin = new Label();
	private Label lblSignificado = new Label();
	
	public PalabraLayout(Palabra palabra) {
		HorizontalLayout hlSpacerTop = new HorizontalLayout();
		hlSpacerTop.setHeight("10px");
		HorizontalLayout hlSpacerBottom = new HorizontalLayout();
		hlSpacerBottom.setHeight("10px");
		
		addComponents(hlSpacerTop,lblCaracter,lblPingin,lblSignificado,hlSpacerBottom);
		
		setComponentAlignment(lblCaracter, Alignment.MIDDLE_LEFT);
		setComponentAlignment(lblPingin, Alignment.MIDDLE_LEFT);
		setComponentAlignment(lblSignificado, Alignment.MIDDLE_LEFT);
		
		lblCaracter.setWidth("150px");
		lblPingin.setWidth("200px");
		lblSignificado.setWidth("450px");
		
		setStyleName(ValoTheme.LAYOUT_CARD);
		lblCaracter.setStyleName(ValoTheme.LABEL_H1);
		
		lblCaracter.setValue(palabra.getCaracter());
		lblPingin.setValue(palabra.getPingin());
		lblSignificado.setValue(palabra.getSignificado());
		
	}
	
}
