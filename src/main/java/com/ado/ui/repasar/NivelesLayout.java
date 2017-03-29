package com.ado.ui.repasar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ado.domain.Nivel;
import com.ado.domain.Palabra;
import com.ado.service.NivelService;
import com.ado.ui.estudiar.EstudiarLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.ValoTheme;

@Component
@Scope("prototype")
public class NivelesLayout extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private static final int PALABRAS_POR_NIVEL = 30;
	
	@Autowired
	private NivelService service;
	
	@Autowired
	private PruebaDeTiempoLayout pruebaLayout;
	@Autowired
	private EstudiarLayout estudiarLayout;
	
	private List<Nivel> niveles;
	private String userId;
	
	private boolean attach;
	
	
	public NivelesLayout() {
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
		
		this.niveles = service.getNiveles();
		removeAllComponents();
		
		for (Nivel nivel : niveles) {
			Label lblNivel = new Label(nivel.getNombre());
			lblNivel.setSizeUndefined();
			lblNivel.setStyleName(ValoTheme.LABEL_H2);
			lblNivel.addStyleName(ValoTheme.LABEL_COLORED);
			lblNivel.addStyleName(ValoTheme.LABEL_BOLD);
			addComponent(lblNivel);
			addComponent(cargarSubniveles(nivel));
			
			HorizontalLayout spacer = new HorizontalLayout();
			spacer.setHeight("20px");
			addComponent(spacer);
			
			setComponentAlignment(lblNivel, Alignment.TOP_CENTER);
		}
		HorizontalLayout footer = new HorizontalLayout();
		footer.setHeight("80px");
		addComponent(footer);
	}

	private com.vaadin.ui.Component cargarSubniveles(Nivel nivel) {
		GridLayout gl = new GridLayout();
		gl.setSpacing(true);
		gl.setColumns(5);
		List<Boolean> avances = service.getAvanceByUserId(userId, nivel.getNivel());
		for(int subNivel = 0; subNivel < nivel.getPalabras().size()/PALABRAS_POR_NIVEL; subNivel++) {
			Boolean avance = avances.size() >= subNivel + 1 ? avances.get(subNivel) : false;
			gl.addComponent(new SubNivel(nivel, subNivel, PALABRAS_POR_NIVEL,avance, btnEstudiarClickListener, btnPruebaClickListener));
		}
		return gl;
	}
	
	@SuppressWarnings("serial")
	private ClickListener btnPruebaClickListener = new ClickListener() {
		@Override
		public void buttonClick(ClickEvent event) {
			UI.getCurrent().addWindow(pruebaLayout);
			SubNivel sn= (SubNivel) event.getButton().getParent().getParent();
			List<Palabra> palabrasSesion = new ArrayList<Palabra>();
			palabrasSesion.addAll(sn.getPalabras());
			pruebaLayout.setDatos(palabrasSesion, sn.getNivel(), sn.getSubNivel(), userId);
			pruebaLayout.addCloseListener(pruebaLayoutCloseListener);
		}
	};
	
	@SuppressWarnings("serial")
	private CloseListener pruebaLayoutCloseListener = new CloseListener() {
		@Override
		public void windowClose(CloseEvent e) {
			NivelesLayout.this.buildLayout();
		}
	};
	
	@SuppressWarnings("serial")
	private ClickListener btnEstudiarClickListener = new ClickListener() {
		@Override
		public void buttonClick(ClickEvent event) {
			UI.getCurrent().addWindow(estudiarLayout);
			SubNivel sn = (SubNivel) event.getButton().getParent().getParent();
			List<Palabra> palabrasSesion = new ArrayList<Palabra>();
			palabrasSesion.addAll(sn.getPalabras());
			estudiarLayout.setDatos(palabrasSesion);
		}
	};
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
