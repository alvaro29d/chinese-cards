package com.ado.ui.repasar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
public class RepasarLayout extends Panel {
	private static final long serialVersionUID = 1L;

	@Autowired
	private NivelesLayout nivelesLayout;

	private VerticalLayout vl = new VerticalLayout();
	
	private boolean attach;
	
	public RepasarLayout() {
		setContent(vl);
	}
	
	@Override
	public void attach() {
		super.attach();
		buildLayout();
		if(!attach) {
			setListeners();
		}
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		
	}

	private void buildLayout() {
		vl.removeAllComponents();
		setCaption("Niveles HSK");
		setWidth("800px");
		vl.addComponent(cargarNiveles());
		
	}

	private com.vaadin.ui.Component cargarNiveles() {
		return nivelesLayout;
	}
	
}
