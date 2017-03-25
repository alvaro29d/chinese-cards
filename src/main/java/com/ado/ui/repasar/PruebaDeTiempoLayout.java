package com.ado.ui.repasar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ado.domain.Palabra;
import com.ado.ui.estudiar.EstudiarLayout;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Component
@Scope("prototype")
public class PruebaDeTiempoLayout extends Window {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(PruebaDeTiempoLayout.class);
	
	enum MODO {NORMAL,MUERTE_SUBITA}
	
	@Autowired
	private EstudiarLayout estudiarLayout;
	
	@Autowired
	private BarraTiempo barraTiempo;
	
	private VerticalLayout mainLayout = new VerticalLayout();
	private Label lblCaracter = new Label();
	
	private VerticalLayout lyOpciones = new VerticalLayout();
	private MultilineButton[] opciones = new MultilineButton[8];
	private List<Palabra> palabrasSesion = new ArrayList<Palabra>();
	private Set<Palabra> errores = new LinkedHashSet<Palabra>();
	boolean attach;

	
	
	private int opcionCorrecta;

	private MODO modo;

	
	public PruebaDeTiempoLayout() {
		
	}
	
	public void setDatos(List<Palabra> palabras) {
		palabrasSesion.clear();
		palabrasSesion.addAll(palabras);
		errores.clear();
//		for(int i = inicio; i <= palabrasPorNivel ; i++) {
//			palabrasSesion.add(palabras.get(i));
//		}
		this.modo = MODO.NORMAL;
		cargarEjercicio();
	}
	
	private void cargarEjercicio() {
		LOGGER.debug("cargarEjercicio");
		Set<Palabra> listaEjercicio = new HashSet<Palabra>();
		while(listaEjercicio.size() < 8) {
			listaEjercicio.add(palabrasSesion.get(RandomUtils.nextInt(0, palabrasSesion.size())));
		}
		this.opcionCorrecta = RandomUtils.nextInt(0,8);
		Iterator<Palabra> iterator = listaEjercicio.iterator();
		for (int i = 0; i < 8; i++) {
			Palabra next = iterator.next();
			opciones[i].setEnabled(true);
			opciones[i].setPalabra(next);
			if(i == opcionCorrecta){
				lblCaracter.setValue(next.getPalabra());
			}
		}
		if(modo == MODO.NORMAL) {
			barraTiempo.refreshTime();
		} else {
			barraTiempo.addTime(0.4f);
		}
		
	}
	
	@Override
	public void attach() {
		super.attach();
		if(!attach) {
			build();
			setListeners();
		}
	}

		
	private void build() {
		setContent(mainLayout);
		setClosable(true);
		setWidth("800px");
		setModal(true);
		lblCaracter.setWidth("100px");
		lblCaracter.setStyleName(ValoTheme.LABEL_H1);
		HorizontalLayout hlSpacer = new HorizontalLayout();
		hlSpacer.setHeight("10px");
		
		mainLayout.removeAllComponents();
		mainLayout.addComponent(lblCaracter);
		mainLayout.addComponent(initBarraTiempo());
		mainLayout.addComponent(initOpciones());
		mainLayout.addComponent(hlSpacer);
		mainLayout.setComponentAlignment(lblCaracter, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(initBarraTiempo(), Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(lyOpciones, Alignment.MIDDLE_CENTER);
	}

	private BarraTiempo initBarraTiempo() {
		return barraTiempo;
	}


//	private com.vaadin.ui.Component initBarraTiempo() {
//		barraTiempo.setMin(0.0);
//		barraTiempo.setMax(3600.0);
//		barraTiempo.setValue(3600.0);
//		barraTiempo.setEnabled(false);
//		barraTiempo.setWidth("600px");
//		barraTiempo.setStyleName(ValoTheme.SLIDER_NO_INDICATOR);
//		return barraTiempo;
//	}
	
	private com.vaadin.ui.Component initOpciones() {
		lyOpciones.setSizeUndefined();
		lyOpciones.removeAllComponents();
		lyOpciones.setSpacing(true);
		for(int i = 0 ; i < opciones.length ; i++) {
			opciones[i] = new MultilineButton();
		}
		HorizontalLayout hl1 = new HorizontalLayout();
		hl1.setWidth("800px");
		hl1.setSpacing(true);
		hl1.addComponents(opciones[0],opciones[1],opciones[2]);
		hl1.setComponentAlignment(opciones[0], Alignment.MIDDLE_CENTER);
		hl1.setComponentAlignment(opciones[1], Alignment.MIDDLE_CENTER);
		hl1.setComponentAlignment(opciones[2], Alignment.MIDDLE_CENTER);
		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.setWidth("800px");
		hl2.setSpacing(true);
		hl2.addComponents(opciones[3],opciones[4]);
		hl2.setComponentAlignment(opciones[4], Alignment.MIDDLE_CENTER);
		hl2.setComponentAlignment(opciones[3], Alignment.MIDDLE_CENTER);
		HorizontalLayout hl3 = new HorizontalLayout();
		hl3.setWidth("800px");
		hl3.setSpacing(true);
		hl3.addComponents(opciones[5],opciones[6],opciones[7]);
		lyOpciones.addComponents(hl1,hl2,hl3);
		hl3.setComponentAlignment(opciones[5], Alignment.MIDDLE_CENTER);
		hl3.setComponentAlignment(opciones[6], Alignment.MIDDLE_CENTER);
		hl3.setComponentAlignment(opciones[7], Alignment.MIDDLE_CENTER);
		return lyOpciones;
	}
	
	private void setListeners() {
		this.attach = true;
		for(MultilineButton opcion : opciones) {
			opcion.addLayoutClickListener(btnOpcionAddLayoutClickListener);
		}
	}

	@SuppressWarnings("serial")
	private LayoutClickListener btnOpcionAddLayoutClickListener = new LayoutClickListener() {
		@Override
		public void layoutClick(LayoutClickEvent event) {
			LOGGER.debug("Seleccion tarjeta");
			if(opciones[opcionCorrecta] == event.getSource()) {
				ejercicioResuelto();
			} else {
				for(int i = 0; i < 8; i++) {
					if(!opciones[i].isEnabled()) {
						errores.add(opciones[opcionCorrecta].getPalabra());
					}
				}
				errores.add(((MultilineButton)event.getSource()).getPalabra());
				
				((MultilineButton)event.getSource()).setEnabled(false);
				deshabilitarOtro();
			}
		}
		
		private void deshabilitarOtro() {
			int deshabilitar = RandomUtils.nextInt(0,8);
			for(int i = deshabilitar; i< deshabilitar + 8; i++) {
				if(opciones[i%8].isEnabled() && opcionCorrecta !=i%8){
					opciones[i%8].setEnabled(false);
					i = deshabilitar + 8;
				}
			}
		}
		
		private void ejercicioResuelto() {
			cargarEjercicio();
		}
	};
	
	public void gameOver() {
		close();
		UI.getCurrent().addWindow(estudiarLayout);
		estudiarLayout.setDatos(new ArrayList<Palabra>(errores));
	}
	

}
