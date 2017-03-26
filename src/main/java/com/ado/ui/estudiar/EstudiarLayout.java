package com.ado.ui.estudiar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ado.domain.Palabra;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Component
@Scope("prototype")
public class EstudiarLayout extends Window {
	private static final long serialVersionUID = 1L;

	private VerticalLayout mainLayout = new VerticalLayout();
	
	private List<Palabra> palabrasSesion = new ArrayList<Palabra>();
	
	public EstudiarLayout() {
	}
	
	public void setDatos(List<Palabra> palabras) {
		palabrasSesion.clear();
		palabrasSesion.addAll(palabras);
		Collections.sort(palabrasSesion);
		cargarLista();
	}
	
	private void cargarLista() {
		mainLayout.removeAllComponents();
		for(Palabra p : palabrasSesion) {
			mainLayout.addComponent(new PalabraLayout(p));
		}
	}

	@Override
	public void attach() {
		super.attach();
		build();
	}
	
	private void build() {
		setContent(mainLayout);
		setClosable(true);
		setResizable(false);
		setWidth("800px");
		setHeight("632px");
		setModal(true);
		mainLayout.setStyleName(ValoTheme.LAYOUT_WELL);
		mainLayout.removeAllComponents();
	}
	
}
